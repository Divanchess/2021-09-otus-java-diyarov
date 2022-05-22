package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ProcessorExceptionEvenSecond implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ProcessorExceptionEvenSecond() {
        this.dateTimeProvider = new DateTimeProvider();
    }

    public ProcessorExceptionEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        var currentTime = dateTimeProvider.getCreatedAt();
        if (currentTime.getSecond() % 2 == 0) {
            throw new UnsupportedOperationException();
        }
        return message;
    }

    public static class DateTimeProvider {
        public LocalDateTime getCreatedAt() {
            return LocalDateTime.now();
        }
    }
}
