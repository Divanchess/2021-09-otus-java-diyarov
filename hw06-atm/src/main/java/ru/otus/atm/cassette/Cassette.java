package ru.otus.atm.cassette;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Cassette<T> {
    private List<T> banknotes = new ArrayList<>();

    public Cassette(T t) {
    }

    public static <T> Cassette<T> newInstance(Class<T> classOfT) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T t = classOfT.getDeclaredConstructor().newInstance();
        return new Cassette<T>(t);
    }

    public void addBanknote(T banknote) {
        banknotes.add(banknote);
    }

    public void addBanknotes(List<T> banknotesIncome) {
        banknotes.addAll(banknotesIncome);
    }

    public int getCountBanknotes() {
        return banknotes.size();
    }
}