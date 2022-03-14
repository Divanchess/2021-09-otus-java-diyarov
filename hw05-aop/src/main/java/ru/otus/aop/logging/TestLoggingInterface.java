package ru.otus.aop.logging;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TestLoggingInterface {
    void calculation(int param);

    void calculation(int param1, int param2);

    void calculation(int param1, int param2, String param3);

    void calculation(Long param1, Integer param2, BigDecimal param3);

    void calculation(String param1, String param2, String param3);

    void calculation(String param1, String param2);

    void calculation(LocalDate param1);
}
