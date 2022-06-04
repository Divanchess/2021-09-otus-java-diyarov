package ru.otus.core.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HwLoggerListener<K, V> implements HwListener<K, V>{
    private Logger log;

    public HwLoggerListener(Logger log) {
        this.log = log;
    }

    @Override
    public void notify(K key, V value, String action) {
        log.info("Cache action: " + action
                + (key != null ? "; key: " + key : "")
                + (value != null ? "; value: " + value : ""));
    }
}
