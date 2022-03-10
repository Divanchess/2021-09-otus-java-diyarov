package homework;

import java.util.*;


public class CustomerService {
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private Comparator comparator;
    public NavigableMap<Customer, String> map;

    public CustomerService() {
        this.comparator = new CustomerComparator();
        this.map = new TreeMap<>(comparator);
    }

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return map.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return map.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        map.put(new Customer(customer.getId(), customer.getName(), customer.getScores()) {
            @Override
            public void setName(String name) {}
            @Override
            public void setScores(long scores) {}
        }, data);
    }

    private class CustomerComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer c1, Customer c2) {
            return Long.compare(c1.getScores(),(c2.getScores()));
        }
    }

}
