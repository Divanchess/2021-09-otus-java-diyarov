package ru.otus.atm.cassette;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.atm.Atm;
import ru.otus.atm.banknote.Banknote;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AtmTest {

    @Test
    void withdrawalTestSuccess() {
        int withdrawAmount = 13943;

        Atm atm = new Atm();
        List<Banknote.One> banknotesOne = new ArrayList<>(Collections.nCopies(15, new Banknote.One()));
        List<Banknote.Ten> banknotesTen = new ArrayList<>(Collections.nCopies(8, new Banknote.Ten()));
        List<Banknote.Fifty> banknotesFifty = new ArrayList<>(Collections.nCopies(7, new Banknote.Fifty()));
        List<Banknote.OneHundred> banknotesOneHundred = new ArrayList<>(Collections.nCopies(10, new Banknote.OneHundred()));
        List<Banknote.FiveHundred> banknotesFiveHundred= new ArrayList<>(Collections.nCopies(7, new Banknote.FiveHundred()));
        List<Banknote.OneThousand> banknotesOneThousand = new ArrayList<>(Collections.nCopies(4, new Banknote.OneThousand()));
        List<Banknote.FiveThousand> banknotesFiveThousand= new ArrayList<>(Collections.nCopies(1, new Banknote.FiveThousand()));

        List<Banknote> banknotes = Stream.of(banknotesOne, banknotesTen, banknotesFifty, banknotesOneHundred,
                        banknotesFiveHundred, banknotesOneThousand, banknotesFiveThousand)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        atm.deposit(banknotes);
        atm.displayBanknotesCountAndBalance();
        Assertions.assertTrue(atm.withdraw(withdrawAmount));
    }

    @Test
    void withdrawalTestFail() {
        int withdrawAmount = 43943;

        Atm atm = new Atm();
        List<Banknote.One> banknotesOne = new ArrayList<>(Collections.nCopies(15, new Banknote.One()));
        List<Banknote.Ten> banknotesTen = new ArrayList<>(Collections.nCopies(8, new Banknote.Ten()));
        List<Banknote.Fifty> banknotesFifty = new ArrayList<>(Collections.nCopies(7, new Banknote.Fifty()));
        List<Banknote.OneHundred> banknotesOneHundred = new ArrayList<>(Collections.nCopies(10, new Banknote.OneHundred()));
        List<Banknote.FiveHundred> banknotesFiveHundred= new ArrayList<>(Collections.nCopies(7, new Banknote.FiveHundred()));
        List<Banknote.OneThousand> banknotesOneThousand = new ArrayList<>(Collections.nCopies(4, new Banknote.OneThousand()));
        List<Banknote.FiveThousand> banknotesFiveThousand= new ArrayList<>(Collections.nCopies(1, new Banknote.FiveThousand()));

        List<Banknote> banknotes = Stream.of(banknotesOne, banknotesTen, banknotesFifty, banknotesOneHundred,
                        banknotesFiveHundred, banknotesOneThousand, banknotesFiveThousand)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        atm.deposit(banknotes);
        atm.displayBanknotesCountAndBalance();

        Assertions.assertFalse(atm.withdraw(withdrawAmount));
    }

    @Test
    void depositTest() {
        Atm atm = new Atm();
        List<Banknote.One> banknotesOne = new ArrayList<>(Collections.nCopies(2, new Banknote.One()));
        List<Banknote.Ten> banknotesTen = new ArrayList<>(Collections.nCopies(4, new Banknote.Ten()));

        List<Banknote> banknotes = new ArrayList<>();
        banknotes.addAll(banknotesOne);
        banknotes.addAll(banknotesTen);

        List<Banknote> depositBanknotesSuccess = atm.deposit(banknotes);

        Assertions.assertEquals(depositBanknotesSuccess.size(), banknotes.size());
        Assertions.assertTrue(depositBanknotesSuccess.containsAll(banknotes));
        Assertions.assertTrue(banknotes.containsAll(depositBanknotesSuccess));

        atm.displayBanknotesCountAndBalance();
    }
}
