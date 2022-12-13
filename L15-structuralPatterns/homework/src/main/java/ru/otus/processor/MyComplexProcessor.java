package ru.otus.processor;

import ru.otus.handler.Handler;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyComplexProcessor implements Handler {

    private final List<Listener> listeners = new ArrayList<>();
    private final List<Processor> processors;
    private final Consumer<Exception> errorHandler;

    public MyComplexProcessor(List<Processor> processors, Consumer<Exception> errorHandler) {
        this.processors = processors;
        this.errorHandler = errorHandler;
    }

    @Override
    public Message handle(Message msg) {
        notify(msg);
        Message newMsg = msg;
        for (Processor pros : processors) {
            try {
                newMsg = pros.process(newMsg);
            } catch (Exception ex) {
                errorHandler.accept(ex);
            }
        }
        return newMsg;
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
    private void notify(Message msg) {
        listeners.forEach(listener -> {
            try {
                listener.onUpdated(msg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
