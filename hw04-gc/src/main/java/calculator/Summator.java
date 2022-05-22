package calculator;

import org.openjdk.jol.info.ClassLayout;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;


public class Summator {
    private long sum = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private int sumLastThreeValues = 0;
    private long someValue = 0;
    private final List<Data> listValues = new ArrayList<>();

    //!!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        int currentValue = data.getValue();
        listValues.add(data);
        if (listValues.size() % 512 == 0) {
            listValues.clear();
        }
        sum += currentValue;

        sumLastThreeValues = currentValue + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = currentValue;

        for (int idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (currentValue + 1) - sum);
            someValue = Math.abs(someValue) + listValues.size();
        }
    }

    public long getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public int getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public long getSomeValue() {
        return someValue;
    }
}
