package it.unibo.smartgh.model;

import java.util.ArrayList;
import java.util.List;

public class ParameterImpl implements Parameter{
    private final String name;
    private final ParameterValue currentValue;
    private List<ParameterValue> history;

    public ParameterImpl(String name, ParameterValue currentValue, List<ParameterValue> history) {
        this.name = name;
        this.currentValue = currentValue;
        this.history = history;
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
        return this.history;
    }
}
