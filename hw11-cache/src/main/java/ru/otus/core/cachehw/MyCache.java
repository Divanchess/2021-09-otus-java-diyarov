package ru.otus.core.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
    private Map<K, V> cache = new WeakHashMap<>();
    private List<HwListener<K, V>> listeners = new ArrayList();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        listeners.forEach(kvHwListener -> kvHwListener.notify());
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
        listeners.forEach(kvHwListener -> kvHwListener.notify());
    }

    @Override
    public V get(K key) {
        listeners.forEach(kvHwListener -> kvHwListener.notify());
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        this.listeners.remove(listener);
    }
}
