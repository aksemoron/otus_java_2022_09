package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        return data.stream()
                .sorted(Comparator.comparing(Measurement::getValue))
                .collect(Collectors.groupingBy(Measurement::getName, Collectors.summingDouble(Measurement::getValue)))
                //Сортировка чтобы порядок проходил. Gson  парсит данные ровно в том порядке, в котором они пришли
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
