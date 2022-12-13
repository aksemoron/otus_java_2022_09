package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final List<Message> massageHistory = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        massageHistory.add(msg.toBuilder()
                .field13(new ObjectForMessage(List.copyOf(msg.getField13().getData())))
                .build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return massageHistory.stream()
                .filter(e-> e.getId() == id)
                .findFirst();
    }
}
