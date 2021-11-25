package homework;

import java.util.Comparator;
import java.util.NavigableMap;
import java.util.Map;
import java.util.TreeMap;


public class CustomerService {
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private CustomerComparator comparator;
    public NavigableMap<Customer, String> map;

    public CustomerService() {
        this.comparator = new CustomerComparator();
        this.map = new TreeMap<>(comparator);
    }

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
//        Map.Entry<Customer, String> smallest = map.firstEntry();
//        for (Map.Entry<Customer, String> entry: map.entrySet()) {
//            Map.Entry.comparingByKey();
//        }
        return map.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return map.ceilingEntry(customer);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    class CustomerComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer c1, Customer c2) {
            if (c1.getScores() == c2.getScores()) {
                return 0;
            }
            if (c1.getScores() == 0) {
                return -1; // c1 < c2
            }
            if (c2.getScores() == 0) {
                return 1; // c1 > c2
            }
            if (c1.getScores() > c2.getScores()) {
                return 1;
            }
            return -1;
        }
    }
}
