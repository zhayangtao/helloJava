package javastream;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zhayangtao on 2017/11/24.
 */
public class StreamTest {

    public static void main(String[] args) {
        StreamTest testStream = new StreamTest();
//        testStream.testStream();

        Person person = new Person();
//        person.testStream();
        person.testParallelStream();
    }

    private void testStream() {
        List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");
        myList.stream().filter(s -> s.startsWith("c")).map(String::toUpperCase).sorted().forEach(System.out::println);
        myList.stream().findFirst().ifPresent(System.out::println);
        Stream.of("a1", "a2", "b1", "c2", "c1").findFirst().ifPresent(System.out::println);

        // IntStream
        IntStream.range(1, 4).forEach(System.out::println);
        Arrays.stream(new int[]{1, 2, 3}).map(operand -> 2 * operand + 1).average().ifPresent(System.out::println);

        // 将对象数据了转换为基本数据流
        Stream.of("a1", "a2", "b1", "c2", "c1").map(s -> s.substring(1))
                .mapToInt(Integer::parseInt).max().ifPresent(System.out::println);

        // 将基本数据流转换为对象数据流
        IntStream.range(1, 4).mapToObj(value -> "a" + value)
                .forEach(System.out::println);

        Stream.of(1.0, 2.0, 3.0).mapToInt(Double::intValue).mapToObj(value -> "a" + 1)
                .forEach(System.out::println);

        // 衔接操作特性：延迟
        Stream.of("a1", "a2", "b1", "c2", "c1").filter(s -> {
            System.out.println("filter: " + s);
            return true;
        });

        Stream.of("a1", "a2", "b1", "c2", "c1").filter(s -> {
            System.out.println("filter: " + s);
            return true;
        }).forEach(s -> System.out.println("forEach: " + s));

        Stream.of("d2", "a2", "b1", "b3", "c").map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).anyMatch(s -> {
            System.out.println("anyMatch: " + s);
            return s.startsWith("A");
        });

        System.out.println("---------------");
        Stream.of("d2", "a2", "b1", "b3", "c").map(s -> {
            System.out.println("Map: " + s);
            return s.toUpperCase();
        }).filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("A");
        }).forEach(s -> {
            System.out.println("forEach: " + s);
        });

        System.out.println("===============");
        Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("a");
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s -> System.out.println("forEach: " + s));

        System.out.println("==================");
        //sorted 操作是有状态的操作
        Stream.of("d2", "a2", "b1", "b3", "c").sorted((o1, o2) -> {
            System.out.printf("sort: %s; %s\n", o1, o2);
            return o1.compareTo(o2);
        }).filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("a");
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s -> {
            System.out.println("forEach: " + s);
        });

        System.out.println("重排调用链=============");
        Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("a");
        }).sorted((o1, o2) -> {
            System.out.printf("sort: %s; %s\n", o1, o2);
            return o1.compareTo(o2);
        }).map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        }).forEach(s -> {
            System.out.println("forEach: " + s);
        });

        // 复用数据流：java8的数据流不能复用，一旦调用任何终止操作，数据流就关闭
        Stream<String> stringStream = Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> s.startsWith("a"));
        stringStream.anyMatch(s -> true);
        // java.lang.IllegalStateException: stream has already been operated upon or closed
        // stringStream.noneMatch(s -> true);

        // 复用数据流需要为终止操作创建新的数据流调用链
        Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> s.startsWith("a"));
        streamSupplier.get().anyMatch(s -> true);
        streamSupplier.get().noneMatch(s -> true);

    }
}

class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    void testStream() {
        List<Person> personList = Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12));

        //collect是非常有用的终止操作，将流中的元素存放在不同类型的结果中
        List<Person> filtered = personList.stream().filter(person -> person.name.startsWith("p"))
                .collect(Collectors.toList());

        Map<Integer, List<Person>> integerListMap = personList.stream().collect(Collectors.groupingBy(o -> o.age));
        integerListMap.forEach((integer, people) -> System.out.format("age %s: %s\n", age, people));

        Double averageAge = personList.stream().collect(Collectors.averagingInt(value -> value.age));
        System.out.println(averageAge);

        String phrase = personList.stream().filter(person -> person.age >= 18)
                .map(person -> person.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age "));
        System.out.println(phrase);

        // 通过 Collector.of()创建一个新的收集器,收集器的四个组成部分：供应器、累加器、组合器和终止器。
        Collector<Person, StringJoiner, String> personStringJoinerStringCollector =
                Collector.of(() -> new StringJoiner(" | "),
                        (stringJoiner, person) -> stringJoiner.add(person.name.toUpperCase()),
                        StringJoiner::merge,
                        StringJoiner::toString);
        String names = personList.stream().collect(personStringJoinerStringCollector);
        System.out.println(names);

        // flatMap将流中的每个元素，转换为其它对象的流。
        List<Foo> foos = new ArrayList<>();
        IntStream.range(1, 4).forEach(value -> foos.add(new Foo("Foo" + value)));
        foos.forEach(foo -> IntStream.range(1, 4).forEach(value -> foo.bars.add(new Foo.Bar("Bar" + value + " <- " + foo.name))));
        foos.stream().flatMap(foo -> foo.bars.stream()).forEach(bar -> System.out.println(bar.name));

        // reduce 第一种用法，将流中的元素归约为流中的一个元素。
        personList.stream().reduce((person, person2) -> person.age > person2.age ? person : person2)
                .ifPresent(System.out::println);
        // 第二种用法，接受一个初始值，和一个累加器
        personList.stream().reduce(new Person("", 0), (person, person2) -> {
            person.age += person2.age;
            person.name += person2.name;
            return person;
        });
        // 第三种用法，接受三个参数，初始值、累加器、组合器
        Integer ageSum = personList.stream().reduce(0, (integer, person) -> integer += person.age, (integer, integer2) -> integer + integer2);
        System.out.println(ageSum);

        ForkJoinPool joinPool = ForkJoinPool.commonPool();
        System.out.println(joinPool.getParallelism());

    }

    /**
     * 测试并行流
     */
    void testParallelStream() {
        Arrays.asList("a1", "a2", "b1", "c2", "c1").parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s] \n", s, Thread.currentThread().getName());
                    return true;
                }).map(s -> {
            System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
            return s.toUpperCase();
        }).forEach(s -> {
            System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName());
        });
    }
}

class Foo {
    String name;
    List<Bar> bars = new ArrayList<>();

    public Foo(String s) {
        this.name = s;
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }
}