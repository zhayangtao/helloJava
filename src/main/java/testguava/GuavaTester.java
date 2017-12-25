package testguava;

import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.PrintUtils.print;


/**
 * @author zhayangtao
 * @version 1.0
 * @since 2017/12/15
 */
public class GuavaTester {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(2);
        numbers.add(3);
        numbers.add(6);
        numbers.add(6);
        numbers.add(4);

        Ordering<Integer> ordering = Ordering.natural();
        print("Input List");
        print(numbers);
        print("list if sorted:" + ordering.isOrdered(numbers));

        numbers.sort(ordering);
        print("Sorted List:");
        print(numbers);
        print("==========");
        print("list if sorted:" + ordering.isOrdered(numbers));
    }

}
