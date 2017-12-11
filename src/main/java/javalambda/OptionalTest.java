package javalambda;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by shliangyan on 2017/5/11.
 */
public class OptionalTest {

    public void testOptional() {
        Optional<Integer> possible = Optional.of(6);
        Optional<Integer> absentOpt = Optional.absent();
        Optional<Integer> nullableOpt = Optional.fromNullable(null);
        Optional<Integer> noNullableOpt = Optional.fromNullable(10);
        Optional<HashMap<String, String>> mapOpt = Optional.fromNullable(new HashMap<String, String>());
        System.out.println(mapOpt.get());
        if (possible.isPresent()) {
            System.out.println("possible isPresent:" + possible.isPresent());
            System.out.println("possible value:" + possible.get());
        }
        if (absentOpt.isPresent()) {
            System.out.println("absentOpt isPresent:" + absentOpt.isPresent());
            System.out.println("absentOpt value:" + absentOpt.get());
        }
        if (nullableOpt.isPresent()) {
            System.out.println("nullableOpt isPresent:" + nullableOpt.isPresent());
            System.out.println("nullableOpt value:" + nullableOpt.get());
        }
        if (noNullableOpt.isPresent()) {
            System.out.println("noNullableOpt isPresent:" + noNullableOpt.isPresent());
            System.out.println("noNullableOpt value:" + noNullableOpt.get());
        }
    }

    public void test2() throws Exception {
//        Optional<String> possibleNull = Optional.of(null);
        Optional<String> possibleNull2 = Optional.absent();
        possibleNull2.get();
//        possibleNull.get();
    }

    public void doIt() throws SQLException {
        try {
            doSomething();
        } catch (Throwable throwable) {
            Throwables.propagateIfInstanceOf(throwable,SQLException.class);
            Throwables.propagateIfPossible(throwable);
        }
    }

    public void doSomething() throws Throwable {}
    public void soSomethingElse() throws Exception {}


}
