package ru.otus.aop.demo;

import ru.otus.aop.logging.TestLoggingInterface;
import ru.otus.aop.proxy.LoggingProxy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface logging = LoggingProxy.createMyClass();
        logging.calculation(6);
        logging.calculation(7,4);
        logging.calculation(2,9,"Test");
        logging.calculation(883L, 4, BigDecimal.TEN);
        logging.calculation("Param1", "Param2", "Param3");
        logging.calculation("Param1", "Param2");
        logging.calculation(LocalDate.now());
    }

}

