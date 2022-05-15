package ru.otus.processor.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import ru.otus.model.Message;

import java.time.LocalDateTime;

class ProcessorExceptionEvenSecondTest {
    protected Message message;

    @BeforeEach
    void setUp() {
        message = new Message.Builder(1L).field7("field7").build();
    }

    @Test
    void processExceptionEvenSecondTest() {
        var provider = mock(ProcessorExceptionEvenSecond.DateTimeProvider.class);
        when(provider.getCreatedAt()).thenReturn(LocalDateTime.of(2022, 05, 12, 9, 46, 56));
        var processor = new ProcessorExceptionEvenSecond(provider);
        assertThrows(UnsupportedOperationException.class, () -> processor.process(message));
    }

    @Test
    void processNoExceptionOddSecondTest() {
        var provider = mock(ProcessorExceptionEvenSecond.DateTimeProvider.class);
        when(provider.getCreatedAt()).thenReturn(LocalDateTime.of(2022, 05, 12, 9, 46, 57));
        var processor = new ProcessorExceptionEvenSecond(provider);
        assertDoesNotThrow(() -> processor.process(message));
    }
}
