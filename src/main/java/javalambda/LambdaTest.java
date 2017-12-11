package javalambda;

import com.google.common.collect.ImmutableList;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by shliangyan on 2017/11/17.
 */
public class LambdaTest {

    public void testThreadLambda() {
        // before java8
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before java8, too much code for too little to do");
            }
        })
                .start()
        ;
        System.out.println(1);
        new Thread(() -> System.out.println("In java8, Lambda expression rocks!!")).start();
        System.out.println(2);

    }

    public void testCollectionLambda() {
        //before java8
        System.out.println("Before java8");
        List<String> features = ImmutableList.of("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : features) {
            System.out.println(feature);
        }
        //after java8
        System.out.println("--------------");
        System.out.println("In java8");
        features.forEach(System.out::println);
    }

    public void testPredicate() {
        List<String> languages = ImmutableList.of("Java", "Scala", "C++", "Haskell", "Lisp");
        System.out.println("languages which starts with J:");

        filter(languages, (str) -> str.startsWith("J"));

        System.out.println("filter simple");
        filterSimple(languages, (str) -> str.startsWith("J"));

        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> lengthEqualsFour = (n) -> n.length() == 4;
        languages.stream().filter(startsWithJ.and(lengthEqualsFour))
                .forEach((n) ->
                        System.out.println("name, which starts with 'J'and length equals four is:" + n));

        languages.stream().filter(startsWithJ.or(lengthEqualsFour))
                .forEach((n) ->
                        System.out.println("name, which starts with 'J' or length equals four is:" + n));
    }

    public void testCollect() {
        List<String> languages = ImmutableList.of("Java", "Scala", "C++", "Haskell", "Lisp");
        List<String> filtered = languages.stream().filter(x -> x.length() > 4).collect(Collectors.toList());
        System.out.println(filtered);
    }

    public void testMap() {
        List<String> G7 = ImmutableList.of("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        String G7Contries = G7.stream().map(String::toUpperCase).collect(Collectors.joining(", "));
        System.out.println(G7Contries);
    }

    public void testDistinct() {
        List<Integer> numbers = ImmutableList.of(9, 10, 3, 4, 5, 7, 4, 3);
        List<Integer> distinctList = numbers.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctList);
    }

    public void testSummaryStatistics() {
        List<Integer> primes = ImmutableList.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics intSummaryStatistics = primes.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("primes max:" + intSummaryStatistics.getMax());
        System.out.println("primes min:" + intSummaryStatistics.getMin());
        System.out.println("primes sum:" + intSummaryStatistics.getSum());
        System.out.println("primes average:" + intSummaryStatistics.getAverage());

    }

    public void testMapAndReduce() {
        List<Integer> costBeforeTax = ImmutableList.of(100, 200, 300, 400, 500);
        //不使用lambda
        for (Integer cost : costBeforeTax) {
            double price = cost + .12 * cost;
            System.out.println(price);
        }

        //使用 lambda
        costBeforeTax.stream().map((cost) -> cost + .12 * cost).forEach(System.out::println);

        //使用 reduce
        Double bill = costBeforeTax.stream().map((cost) -> cost + .12 * cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total:" + bill);
    }



    private static void filter(List<String> names, Predicate<String> condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    private static void filterSimple(List<String> names, Predicate<String> condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> System.out.println(name + " "));
    }
}
