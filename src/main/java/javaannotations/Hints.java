package javaannotations;

import java.lang.annotation.Repeatable;

/**
 * Created by zhayangtao on 2017/11/24.
 */
public @interface Hints {
    Hint[] value();
}

@Repeatable(Hints.class)
@interface Hint {
    String value();
}

@Hints({@Hint("hint1"), @Hint("hint2")})
class Person1 {

}

@Hint("hint1")
@Hint("hint2")
class Person2 {

}

class Test {
    public static void main(String[] args) {
        Hint hint = Person2.class.getAnnotation(Hint.class);
        System.out.println(hint);  // -> null

        Hints hints1 = Person1.class.getAnnotation(Hints.class);
        System.out.println(hints1.value().length);
    }
}


