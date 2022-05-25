package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.*;

import java.beans.Introspector;
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
 * Сохратяет объект в базу, читает объект из базы
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
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    Constructor<T> constructor = entityClassMetaData.getConstructor();
                    List<Object> params = new ArrayList<>();
                    List<Field> fields = entityClassMetaData.getAllFields();
                    for (Field f : fields) {
                        params.add(rs.getObject(f.getName()));
                    }
                    try {
                        var object = constructor.newInstance(params);
                        return object;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        System.err.println(e);
                    }
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T object) {
        List<Object> columnValues = new ArrayList<>();
        String insertSql = entitySQLMetaData.getInsertSql();
        try {
            for(PropertyDescriptor propertyDescriptor :
                Introspector.getBeanInfo(object.getClass(), Object.class).getPropertyDescriptors()){
                System.out.println(propertyDescriptor.getReadMethod());
                columnValues.add(propertyDescriptor.getReadMethod().invoke(object));
            }
            System.out.println(columnValues);
            return dbExecutor.executeStatement(connection, insertSql,
                    columnValues);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }
}
