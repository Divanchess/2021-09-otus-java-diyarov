package ru.otus.annotations.testapp;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.NotSerializableException;
import java.net.SocketException;

public class DataFileSenderTest {
    public DataFileSenderTest() {
    }

    @Before
    public void readDataFromFileTest() throws IOException {
        System.out.println("BeforeMethod1");
        if (Math.random() < 0.2){
            throw new FileNotFoundException();
        }
    }

    @Before
    public void setHeadersFromFileContentTest() throws IOException {
        System.out.println("BeforeMethod2");
        if (Math.random() < 0.2) {
            throw new InterruptedIOException();
        }
    }

    @After
    public void closeFileAndSocketTest() throws ClassNotFoundException {
        System.out.println("        AfterMethod");
        if (Math.random() < 0.2) {
            throw new ClassNotFoundException();
        }
    }

    @Test
    public void parseDataTest() throws NoSuchFieldException {
        System.out.println("    TestMethod1");
        if (Math.random() < 0.2) {
            throw new NoSuchFieldException();
        }
    }

    @Test
    public void checkDataConditionsTest() throws NotSerializableException {
        System.out.println("    TestMethod2");
        if (Math.random() < 0.2) {
            throw new NotSerializableException();
        }
    }

    @Test
    public void sendDataTest() throws SocketException {
        System.out.println("    TestMethod3");
        if (Math.random() < 0.2) {
            throw new SocketException();
        }
    }

}
