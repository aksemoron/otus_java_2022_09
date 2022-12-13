package ru.otus.listener.homework;


import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.ProcessorThrowExaption;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HistoryListenerTest {

    @Test
    void listenerTest() {
        //given
        var historyListener = new HistoryListener();

        var id = 100L;
        var data = "33";
        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add(data);
        field13.setData(field13Data);

        var message = new Message.Builder(id)
                .field10("field10")
                .field13(field13)
                .build();

        //when
        historyListener.onUpdated(message);
        message.getField13().setData(new ArrayList<>()); //меняем исходное сообщение
        field13Data.clear(); //меняем исходный список

        //then
        var messageFromHistory = historyListener.findMessageById(id);
        assertThat(messageFromHistory).isPresent();
        assertThat(messageFromHistory.get().getField13().getData()).containsExactly(data);
    }

    @Test
    void processorExceptionTest() {
        var processor = new ProcessorThrowExaption(LocalTime.of(1, 1, 2));
        assertThrows(RuntimeException.class, () -> processor.process(new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(new ObjectForMessage(List.of("Data1", "Data2", "Data3")))
                .build()));
    }

}