package ru.otus.jdbc.mapper;

import ru.otus.crm.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl implements EntityClassMetaData {
    private Class entityClass;

    public EntityClassMetaDataImpl(Class entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return entityClass.getDeclaredConstructor();
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public Field getIdField() {
        List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());
        return fields.stream().filter(f -> f.isAnnotationPresent(Id.class)).collect(Collectors.toList()).get(0);
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(entityClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());
        return fields.stream().filter(f -> !f.isAnnotationPresent(Id.class)).collect(Collectors.toList());
    }
}
