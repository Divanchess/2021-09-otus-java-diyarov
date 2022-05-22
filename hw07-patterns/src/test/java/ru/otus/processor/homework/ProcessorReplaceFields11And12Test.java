package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorReplaceFields11And12Test {
    @Test
    @DisplayName("Тест поменять местами поля 11 и 12 с определенными значениями")
    void processSuccessReplaceFields11And12Test() {
        Message message =  new Message.Builder(1L).field11("field11").field12("field12").build();
        var processor = new ProcessorReplaceFields11And12();
        var message_replaced = processor.process(message);
        assertEquals(message_replaced.getField12(), message.getField11());
        assertEquals(message_replaced.getField11(), message.getField12());
    }

    @Test
    @DisplayName("Тест поменять местами поля 11 и 12 неопределенные")
    void processSuccessReplaceNullFields11And12Test() {
        Message message = new Message.Builder(1L).build();
        var processor = new ProcessorReplaceFields11And12();
        var message_replaced = processor.process(message);
        assertEquals(message_replaced.getField12(), message.getField11());
        assertEquals(message_replaced.getField11(), message.getField12());
    }
}