package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.homework.ProcessorExceptionEvenSecond;
import ru.otus.processor.homework.ProcessorReplaceFields11And12;

import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        var processors = List.of(new ProcessorExceptionEvenSecond(),
                new LoggerProcessor(new ProcessorReplaceFields11And12()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        long id = 1456L;
        var field13 = new ObjectForMessage();
        field13.setData(List.of("field13_data", "another_data"));
        var message = new Message.Builder(id)
                .field1("field1")
                .field2("field2")
                .field5("field10")
                .field11("field12")
                .field13(field13)
                .build();

        complexProcessor.handle(message);
        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        System.out.println("last state of message id " + id + " : " + historyListener.findMessageById(id));
        System.out.println("last state of message id " + 820L + " : " + historyListener.findMessageById(820L));
        complexProcessor.removeListener(historyListener);
    }
}
