package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalTime;

public class ProcessorThrowExaption implements Processor {
    private final LocalTime localTime;

    public ProcessorThrowExaption(LocalTime localTime) {
        this.localTime = localTime;
    }

    @Override
    public Message process(Message message) {
            if (localTime.toSecondOfDay() % 2 == 0) {
                throw new RuntimeException("Even second");
            }
        return message;
    }
}
