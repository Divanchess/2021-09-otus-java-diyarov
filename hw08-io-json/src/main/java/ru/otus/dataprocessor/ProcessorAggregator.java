package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> result = new TreeMap<>();
        for (Measurement measurement : data) {
            String name = measurement.getName();
            Double value = measurement.getValue();
            if (result.containsKey(name)) {
                value = result.get(name) + value;
            }
            result.put(name, value);
        }
        return result;
    }
}
