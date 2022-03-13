package ru.otus.annotations.testapp;

import java.util.Arrays;
import java.util.List;

public class TestApplication {
    private final static List<String> tests = Arrays.asList(
            "ru.otus.annotations.testapp.DataFileSenderTest"
    );

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        for (String test : tests) {
            TestExecutor executor = new TestExecutor(test);
            executor.run();
        }
    }
}
