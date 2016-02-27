package com.fuzzywave.kududzi.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ComponentRetriever {
    private static ComponentRetriever instance;

    private static synchronized ComponentRetriever getInstance() {
        if (instance == null) {
            instance = new ComponentRetriever();

            instance.init();
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Component> T get(Entity entity, Class<T> type) {
        return (T) getInstance().getMappers().get(type).get(entity);
    }

    @SuppressWarnings("unchecked")
    public static Collection<Component> getComponents(Entity entity) {
        Collection<Component> components = new ArrayList<Component>();
        for (ComponentMapper<? extends Component> mapper : getInstance().getMappers().values()) {
            if (mapper.get(entity) != null) components.add(mapper.get(entity));
        }

        return components;
    }

    private Map<Class, ComponentMapper<? extends Component>> mappers =
            new HashMap<Class, ComponentMapper<? extends Component>>();


    private void init() {
        // TODO Populate map of component mappers.
        mappers.put(TextureComponent.class, ComponentMapper.getFor(TextureComponent.class));
    }

    private Map<Class, ComponentMapper<? extends Component>> getMappers() {
        return mappers;
    }
}
