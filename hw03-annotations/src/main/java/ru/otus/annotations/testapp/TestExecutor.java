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

    public TestExecutor(String className) {
        this.className = className;
    }

    public void run() throws ClassNotFoundException {
        printTestResults(executeTest(className));
    }

    public static TestResults executeTest(String className) throws ClassNotFoundException {
        Class<?> cls = Class.forName(className);
        TestResults testResults = new TestResults();

        List<Method> beforeMethods = getBeforeMethodsAndSetAccessible(cls);
        List<Method> testMethods = getTestMethodsAndSetAccessible(cls);
        List<Method> afterMethods = getAfterMethodsAndSetAccessible(cls);

        int failedTestsCounter = 0;
        for (Method T : testMethods) {
            boolean testFailed = false;
            Object o = ReflectionHelper.instantiate(cls);

            if (runMethods(beforeMethods, o)) {
                try {
                    ReflectionHelper.callMethod(o, T.getName());
                } catch (Exception e) {
                    System.out.println(e.getCause().toString());
                    testFailed = true;
                }
            } else {
                testFailed = true;
            }
            if (!runMethods(afterMethods, o)) testFailed = true;

            if (testFailed) failedTestsCounter++;
        }

        testResults.setTestsCount(testMethods.size());
        testResults.setFailedTestsCount(failedTestsCounter);
        testResults.setSucceedTestsCount(testMethods.size() - failedTestsCounter);
        return testResults;
    }

    public void printTestResults(TestResults testResults) {
        System.out.println("Всего тестов: " + testResults.getTestsCount() );
        System.out.println("Пройдено неуспешно: " + testResults.getFailedTestsCount());
        System.out.println("Пройдено успешно: " + testResults.getSucceedTestsCount());
    }

    public static boolean runMethods(List<Method> methods, Object o) {
        for (Method m : methods) {
            try {
                ReflectionHelper.callMethod(o, m.getName());
            } catch (Exception e) {
                System.out.println(e.getCause().toString());
                return false;
            }
        }
        return true;
    }

    public static List<Method> getBeforeMethodsAndSetAccessible(Class cls) {
        List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
        return methods.stream().filter(m -> m.isAnnotationPresent(Before.class)).peek(m -> m.setAccessible(true)).collect(Collectors.toList());
    }

    public static List<Method> getTestMethodsAndSetAccessible(Class cls) {
        List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
        return methods.stream().filter(m -> m.isAnnotationPresent(Test.class)).peek(m -> m.setAccessible(true)).collect(Collectors.toList());
    }

    public static List<Method> getAfterMethodsAndSetAccessible(Class cls) {
        List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
        return methods.stream().filter(m -> m.isAnnotationPresent(After.class)).peek(m -> m.setAccessible(true)).collect(Collectors.toList());
    }

}
