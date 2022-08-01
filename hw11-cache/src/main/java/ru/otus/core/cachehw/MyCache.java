package ru.otus.core.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        callListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
        callListeners(key, null, "remove");
    }

    @Override
    public V get(K key) {
        callListeners(key, null, "get");
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

    private void callListeners(K key, V value, String action) {
        listeners.forEach(kvHwListener -> {
            try {
                kvHwListener.notify(key, value, "put");
            } catch (Exception e) {
                System.err.println(String.format("Exception during invoke listener {}", kvHwListener.getListenerName()));
            }
        });
    }
}
