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
    private static int failedTestsCounter;

    public TestExecutor(String className) {
        this.className = className;
    }

    public void run() throws ClassNotFoundException {
        Class<?> cls = Class.forName(className);

        List<Method> beforeMethods = getBeforeMethodsAndSetAccessible(cls);
        List<Method> testMethods = getTestMethodsAndSetAccessible(cls);
        List<Method> afterMethods = getAfterMethodsAndSetAccessible(cls);

        for (Method T : testMethods) {
            boolean failCounterIncremented = false;
            Object o = ReflectionHelper.instantiate(cls);

            boolean runBeforeMethodsSuccessed = runBeforeMethods(beforeMethods, o);

            if (runBeforeMethodsSuccessed) {
                try {
                    ReflectionHelper.callMethod(o, T.getName());
                } catch (Exception e) {
                    System.out.println(e.getCause().toString());
                    failCounterIncremented = true;
                    failedTestsCounter++;
                }
            }

            runAfterMethods(afterMethods, o, !runBeforeMethodsSuccessed || failCounterIncremented);
        }

        System.out.println("Всего тестов: " + testMethods.size());
        System.out.println("Пройдено неуспешно: " + failedTestsCounter);
        System.out.println("Пройдено успешно: " + (testMethods.size() - failedTestsCounter));
    }

    public static boolean runBeforeMethods(List<Method> methods, Object o) {
        for (Method B : methods) {
            try {
                ReflectionHelper.callMethod(o, B.getName());
            } catch (Exception e ) {
                System.out.println(e.getCause().toString());
                failedTestsCounter++;
                return false;
            }
        }
        return true;
    }

    public static void runAfterMethods(List<Method> methods, Object o, boolean failCounterIncremented) {
        for (Method A : methods) {
            try {
                ReflectionHelper.callMethod(o, A.getName());
            } catch (Exception e ) {
                System.out.println(e.getCause().toString());
                if (!failCounterIncremented) { failedTestsCounter++; }
            }
        }
    }

    public static List<Method> getBeforeMethodsAndSetAccessible(Class cls) {
        List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
        return methods.stream()
                .filter(m -> m.isAnnotationPresent(Before.class))
                .peek(m -> m.setAccessible(true))
                .collect(Collectors.toList());
    }

    public static List<Method> getTestMethodsAndSetAccessible(Class cls) {
        List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
        return methods.stream()
                .filter(m -> m.isAnnotationPresent(Test.class))
                .peek(m -> m.setAccessible(true))
                .collect(Collectors.toList());
    }

    public static List<Method> getAfterMethodsAndSetAccessible(Class cls) {
        List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
        return methods.stream()
                .filter(m -> m.isAnnotationPresent(After.class))
                .peek(m -> m.setAccessible(true))
                .collect(Collectors.toList());
    }

}
