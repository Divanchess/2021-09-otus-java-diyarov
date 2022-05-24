package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;
    private String className;
    private String idFieldName;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        this.className = entityClassMetaData.getName();
        this.idFieldName = entityClassMetaData.getIdField().getName();
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", className);
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = ?", className, idFieldName);
    }

    @Override
    public String getInsertSql() {
        List<Field> fields = entityClassMetaData.getAllFields();
        String values = String.join(",", fields.stream().map(f -> f.getName()).collect(Collectors.toList()));
        return String.format("insert into %s (%s) values (?)", className, values);
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set ? = ? where ? = ?", className);
    }
}
