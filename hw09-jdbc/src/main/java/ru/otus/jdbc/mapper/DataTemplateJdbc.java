package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs ->
                generateObjectsFromResultSet(rs).get(0));
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs ->
                generateObjectsFromResultSet(rs)).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        List<Object> columnValues = new ArrayList<>();
        String insertSql = entitySQLMetaData.getInsertSql();
        List<Field> fields = entityClassMetaData.getIdField() != null
                ? entityClassMetaData.getFieldsWithoutId()
                : entityClassMetaData.getAllFields();
        for (Field f : fields) {
            try {
                columnValues.add(new PropertyDescriptor(f.getName(), object.getClass()).getReadMethod().invoke(object));
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }
        return dbExecutor.executeStatement(connection, insertSql, columnValues);
    }

    @Override
    public void update(Connection connection, T object) {
        List<Object> columnValues = new ArrayList<>();
        String updateSql = entitySQLMetaData.getUpdateSql();
        List<Field> fields = entityClassMetaData.getIdField() != null
                ? entityClassMetaData.getFieldsWithoutId()
                : entityClassMetaData.getAllFields();
        Field idField = entityClassMetaData.getIdField() != null
                ? entityClassMetaData.getIdField()
                : fields.get(0);
        for (Field f : fields) {
            try {
                columnValues.add(new PropertyDescriptor(f.getName(), object.getClass()).getReadMethod().invoke(object));
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }
        try {
            columnValues.add(new PropertyDescriptor(idField.getName(), object.getClass()).getReadMethod().invoke(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
        dbExecutor.executeStatement(connection, updateSql, columnValues);
    }

    private List<T> generateObjectsFromResultSet(ResultSet rs) {
        List<T> objects = new ArrayList<T>();
        try {
            while (rs.next()) {
                Constructor<T> constructor = entityClassMetaData.getConstructor();
                List<Field> fields = entityClassMetaData.getAllFields();
                List<Object> newObjectParams = new ArrayList<>();
                for (Field f : fields) {
                    newObjectParams.add(rs.getObject(f.getName(), f.getType()));
                }
                try {
                    var object = constructor.newInstance(newObjectParams.toArray());
                    objects.add(object);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.err.println(e);
                }
            }
            return objects;
        } catch (SQLException e) {
            throw new DataTemplateException(e);
        }
    }
}
