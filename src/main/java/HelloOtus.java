import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class HelloOtus {
    public static void main(String[] args) throws Exception {
        List<String> list = ImmutableList.of("Hello"," ","Otus");
        for (String l : list) {
            Preconditions.checkArgument(!l.isEmpty(),"empty string");
            System.out.println(l);
        }
        HashMultiset<Object> multiset = HashMultiset.create();
        multiset.add("first",2);
        multiset.add("second");
        multiset.add("third",4);
        multiset.forEach(System.out::println);
    }
}