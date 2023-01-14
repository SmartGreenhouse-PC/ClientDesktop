package it.unibo.smartgh.model.parameter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The implementation of {@link Parameter} interface.
 */
public class ParameterImpl implements Parameter{
    private final String name;
    private final ParameterValue currentValue;
    private List<ParameterValue> history;

    /**
     * Instantiates a new Parameter.
     * @param name         the name of the parameter
     * @param currentValue the current value of the parameter
     */
    public ParameterImpl(String name, ParameterValue currentValue) {
        this.name = name;
        this.currentValue = currentValue;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ParameterValue getCurrentValue() {
        return this.currentValue;
    }

    @Override
    public List<ParameterValue> getHistory() {
        this.history.sort(Comparator.comparing(ParameterValue::getDate));
        return this.history;
    }

    @Override
    public void setHistory(List<ParameterValue> history){
        this.history = history;
    }

    @Override
    public Map<String, Double> getHistoryAsMap(){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Map<String, Double> map = this.getHistory()
                .stream()
                .collect(Collectors.toMap(p -> formatter.format(p.getDate()), ParameterValue::getValue, (p1, p2) -> p1));
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
