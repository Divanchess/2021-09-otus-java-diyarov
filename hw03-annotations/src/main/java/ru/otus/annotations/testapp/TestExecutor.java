package ru.otus.annotations.testapp;

import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.annotations.After;
import ru.otus.annotations.helper.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestExecutor {
    private String className;
    private int failedTestsCount;

    public TestExecutor(String className) {
        this.className = className;
    }

    public void run() throws ClassNotFoundException {
        Class<?> cls = Class.forName(className);

        List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
        List<Method> beforeMethods = methods.stream()
                .filter(m -> m.isAnnotationPresent(Before.class))
                .peek(m -> m.setAccessible(true))
                .collect(Collectors.toList());
        List<Method> afterMethods = methods.stream()
                .filter(m -> m.isAnnotationPresent(After.class))
                .peek(m -> m.setAccessible(true))
                .collect(Collectors.toList());
        List<Method> testMethods = methods.stream()
                .filter(m -> m.isAnnotationPresent(Test.class))
                .peek(m -> m.setAccessible(true))
                .collect(Collectors.toList());

        tests:
        for (Method T : testMethods) {
            boolean failed = false;
            Object o = ReflectionHelper.instantiate(cls);

            for (Method B : beforeMethods) {
                try {
                    ReflectionHelper.callMethod(o, B.getName());
                } catch (Exception e ) {
                    System.out.println(e.getCause().toString());
                    failedTestsCount++;
                    continue tests;
                }
            }

            try {
                ReflectionHelper.callMethod(o, T.getName());
            } catch (Exception e ) {
                System.out.println(e.getCause().toString());
                failed = true;
                failedTestsCount++;
            }

            for (Method A : afterMethods) {
                try {
                    ReflectionHelper.callMethod(o, A.getName());
                } catch (Exception e ) {
                    System.out.println(e.getCause().toString());
                    if (!failed) { failedTestsCount++; }
                }
            }
        }

        System.out.println("Всего тестов: " + testMethods.size());
        System.out.println("Пройдено неуспешно: " + failedTestsCount);
        System.out.println("Пройдено успешно: " + (testMethods.size() - failedTestsCount));
    }
}
