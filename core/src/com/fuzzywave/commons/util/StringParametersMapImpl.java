package com.fuzzywave.commons.util;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringParametersMapImpl implements Parameters {

    private Map<String, Object> parameterMap;

    /**
     * Builds a new ParametersWrapper using a new instance of a HashMap<String, Object>
     */
    public StringParametersMapImpl() {
        this(new HashMap<String, Object>());
    }

    /**
     * Builds a new ParametersWrapper using the specified map of parameters.
     */
    public StringParametersMapImpl(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    /**
     * Returns the original map from where the properties are being extracted.
     */
    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    /**
     * Sets the map from where the properties will be extracted.
     *
     * @param parameterMap The new map with properties to be extracted.
     */
    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String id) {
        return (T) parameterMap.get(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String id, T defaultValue) {
        if (!parameterMap.containsKey(id)) {
            return defaultValue;
        }
        return (T) parameterMap.get(id);
    }

    @Override
    public Parameters put(String id, Object value) {
        parameterMap.put(id, value);
        return this;
    }

    @Override
    public Parameters putAll(Map<String, Object> values) {
        Set<String> keySet = values.keySet();
        for (String key : keySet) {
            put(key, values.get(key));
        }
        return this;
    }

    @Override
    public void clear() {
        parameterMap.clear();
    }

    @Override
    public Parameters remove(String id) {
        parameterMap.remove(id);
        return this;
    }

}
