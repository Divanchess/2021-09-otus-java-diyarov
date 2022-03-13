package ru.otus.aop.logging;

import ru.otus.aop.annotations.Log;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestLogging implements TestLoggingInterface{
    @Log
    public void calculation(int param) {
        System.out.println("Method | running calculation(int)");
    }

    @Log
    public void calculation(int param1, int param2) {
        System.out.println("Method | running calculation(int, int)");
    }

    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Method | running calculation(int, int, String)");
    }

    @Log
    public void calculation(Long param1, Integer param2, BigDecimal param3) {
        System.out.println("Method | running calculation(Long, int, BigDecimal)");
    }

    @Log
    public void calculation(String param1, String param2, String param3) {
        System.out.println("Method | running calculation(String, String, String)");
    }


    public void calculation(String param1, String param2) {
        System.out.println("Method | running calculation(String, String)");
    }

    @Log
    public void calculation(LocalDate date) {
        System.out.println("Method | running calculation(Date)");
    }

}
