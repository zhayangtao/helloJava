package functionInterface;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by zhayangtao on 2017/11/24.
 */
@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}

class Something{
    private String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }

    public static void main(String[] args) {
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);

        Something something = new Something();
        Converter<String, String> converter1 = something::startsWith;
        String converted1 = converter1.convert("Java");
        System.out.println(converted1);

        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Peter", "Parker");
        System.out.println(person.firstName);
        System.out.println(person.lastName);

        final int num = 1;
        Converter<Integer, String> stringConverter = from -> String.valueOf(from + num);
        stringConverter.convert(2);

        Comparator<Person> comparator = Comparator.comparing(p -> p.firstName);
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");
        System.out.println(comparator.compare(p1, p2));
        System.out.println(comparator.reversed().compare(p1, p2));

        Optional<String> optional = Optional.of("bam");
        optional.isPresent();
        optional.get();
        optional.orElse("fallback");
        optional.ifPresent(s -> System.out.println(s.charAt(0)));

        TestStream testStream = new TestStream();
        testStream.testParallelStreams();

        LocalTime late =  LocalTime.of(23, 59, 59);
        System.out.println(late);
    }
}

class Person {
    String firstName;
    String lastName;

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    Person() {
    }
}

@FunctionalInterface
interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}


class Lambda4 {
    static int outerStaticNum;
    private int outerNum;

    void testScopes() {
        Converter<Integer, String> stringConverter = from -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        Converter<Integer, String> stringConverter1 = from -> {
            outerNum = 24;
            return String.valueOf(from);
        };

        Predicate<String> predicate = s -> s.length() > 0;
        predicate.test("foo");
        predicate.negate().test("foo");

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        backToString.apply("123");

        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();

        Consumer<Person> greeter = person -> System.out.println("hello, " + person.firstName);
        greeter.accept(new Person("Luke", "Skywalker"));

        Comparator<Person> comparator = Comparator.comparing(p -> p.firstName);
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");
        System.out.println(comparator.compare(p1, p2));
        System.out.println(comparator.reversed().compare(p1, p2));



    }
}

class TestStream {
    void testStreams() {
        List<String> stringCollection = ImmutableList.of();
        stringCollection.add("ddd1");

        // Filter 是一个中间操作
        stringCollection.stream().filter(s -> s.startsWith("a"))
                .forEach(System.out::println);

        // Sorted
        stringCollection.stream().sorted().filter(s -> s.startsWith("a"))
                .forEach(System.out::println);

        // Map
        stringCollection.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

        boolean anyStartsWithA = stringCollection.stream().anyMatch(s -> s.startsWith("a"));
        System.out.println(anyStartsWithA);

        boolean allStartsWithA = stringCollection.stream().allMatch(s -> s.startsWith("a"));
        System.out.println(allStartsWithA);

        boolean noneStartsWithZ = stringCollection.stream().noneMatch(s -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);

        // Count
        long startsWithB = stringCollection.stream().filter(s -> s.startsWith("b")).count();

        // Reduce  一个终结操作，能通过某个方法，对元素进行消减
        Optional<String> reduced = stringCollection.stream().sorted().reduce((s1, s2) -> s1 + "#" + "s2");
        reduced.ifPresent(System.out::println);

    }

    /**
     * 测试并行stream
     */
    void testParallelStreams() {
        int max = 1000000;
        List<String> values = Lists.newArrayListWithCapacity(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // 顺序排序
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        System.out.println(String.format("took: %d ms", t1 - t0));

        // 并行排序
        long t2 = System.nanoTime();
        long count1 = values.parallelStream().sorted().count();
        System.out.println(count1);
        long t3 = System.nanoTime();
        System.out.println(String.format("took: %d ms", t3 - t2));

    }
}

class TestMap {
    void testMap() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((id, val) -> System.out.println(val));
    }
}

class TestTime {
    void testTime() {
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();

        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = today.minusDays(2);

        ZoneId zoneId1 = ZoneId.of("Europe/Berlin");
        ZoneId zoneId2 = ZoneId.of("Brazil/East");

        LocalTime now1 = LocalTime.now(zoneId1);
        LocalTime now2 = LocalTime.now(zoneId2);
        LocalTime late =  LocalTime.of(23, 59, 59);
        System.out.println(late);

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(Locale.GERMAN);

        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek);

        LocalDateTime localDateTime = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59,59);
        dayOfWeek = localDateTime.getDayOfWeek();
        Month month = localDateTime.getMonth();
        long mi = localDateTime.getLong(ChronoField.MINUTE_OF_DAY);
    }
}