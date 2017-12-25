package testguava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.apache.commons.collections.comparators.ComparatorChain;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author zhayangtao
 * @version 1.0
 * @since 2017/12/15
 */
public class TestThrowables {

    public void testThrow() {

    }

    public void showcase() {
        try {
            sqrt(1);
        } catch (IndexOutOfBoundsException | InvalidInputException e) {
            e.printStackTrace();
        }
    }

    private double sqrt(double input) throws InvalidInputException{
        if (input < 0) {
            throw new InvalidInputException();
        }
        return Math.sqrt(input);
    }
}

class InvalidInputException extends Exception {
    public static void main(String[] args) {
        System.out.println(Objects.hashCode("a"));
        System.out.println(Objects.hashCode("a"));
        System.out.println(Objects.hash("a", "b"));
        System.out.println(Objects.hash("a", "b", "c"));

        toStringTest();
    }

    private static void toStringTest() {
        System.out.println(MoreObjects.toStringHelper(new InvalidInputException()).add("x", 1).toString());
    }

}

class Person {
    public String name;
    public int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class MyComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        int result = o1.name.compareTo(o2.name);
        if (result != 0) {
            return result;
        }
        return Ints.compare(o1.age, o2.age);
    }
}

class Student implements Comparable<Student> {

    public String name;
    public int age;
    public int score;

    public Student(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    @Override
    public int compareTo(Student o) {

        return ComparisonChain.start()
                .compare(name, o.name)
                .compare(age, o.age)
                .compare(score, o.score, Ordering.natural().nullsLast())
                .result();
    }
}

class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return ComparisonChain.start()
                .compare(o1.name, o2.name)
                .compare(o1.age, o2.age)
                .compare(o1.score, o2.score)
                .result();
    }
}