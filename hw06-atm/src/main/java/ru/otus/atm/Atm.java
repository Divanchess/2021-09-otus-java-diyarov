package ru.otus.atm;

import ru.otus.atm.banknote.Banknote;
import ru.otus.atm.cassette.Cassette;

import java.util.*;

public class Atm {
    private Map<Integer, Cassette> safe = new TreeMap<>(Collections.reverseOrder());
    private int balance;

    public List<Banknote> deposit(List<Banknote> banknotes) {
        List<Banknote> depositBanknotes = new ArrayList<>();
        for(Banknote banknote : banknotes) {
            int key = banknote.getValue();
            Cassette cassette;
            if (!safe.containsKey(key)) {
                try {
                    cassette = Cassette.newInstance(banknote.getClass());
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                safe.put(key, cassette);
            }
            safe.get(key).addBanknote(banknote);
            balance += key;
            depositBanknotes.add(banknote);
        }
        return depositBanknotes;
    }

    public boolean withdraw(int withdrawAmount) {
        System.out.println("Withdraw amount: " + withdrawAmount);
        if (displayBalance() < withdrawAmount) {
            System.out.println("Not enough money in ATM. Please follow to another ATM nearby. Check here");
            return false;
        }

        int left = withdrawAmount;
        Map<Integer,Integer> withdrawResult = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Cassette> cassette : safe.entrySet()) {
            int banknoteValue = cassette.getKey();
            int neededBanknotes = left / banknoteValue;
            int presentBanknotes = cassette.getValue().getCountBanknotes();
            if ((neededBanknotes - presentBanknotes) > 0) {
                withdrawResult.put(banknoteValue, (neededBanknotes - presentBanknotes));
                left = left - presentBanknotes * banknoteValue;
            } else {
                withdrawResult.put(banknoteValue, neededBanknotes);
                left = left % banknoteValue;
            }
        }

        System.out.println("Here your money, welcome back again!");
        for (Map.Entry<Integer, Integer> entry : withdrawResult.entrySet()) {
            System.out.println("    banknoteValue:"+ entry.getKey() + ", count: " + entry.getValue());
        }
        return true;
    }

    public int displayBalance() {
        return this.balance;
    }

    public void displayBanknotesCountAndBalance() {
        System.out.println("In safe:");
        for (Map.Entry<Integer, Cassette> entry : safe.entrySet()) {
            System.out.println("    Banknotes denomination: " + entry.getKey() + ", count: " + entry.getValue().getCountBanknotes());
        }
        System.out.println("Balance: " + balance);
    }
}

