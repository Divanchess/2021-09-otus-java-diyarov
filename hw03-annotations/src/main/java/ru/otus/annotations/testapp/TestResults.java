package ru.otus.annotations.testapp;

public class TestResults {
    private int testsCount;
    private int failedTestsCount;
    private int succeedTestsCount;

    public int getTestsCount() {
        return testsCount;
    }

    public void setTestsCount(int testsCount) {
        this.testsCount = testsCount;
    }

    public int getFailedTestsCount() {
        return failedTestsCount;
    }

    public void setFailedTestsCount(int failedTestsCount) {
        this.failedTestsCount = failedTestsCount;
    }

    public int getSucceedTestsCount() {
        return succeedTestsCount;
    }

    public void setSucceedTestsCount(int succeedTestsCount) {
        this.succeedTestsCount = succeedTestsCount;
    }
}
