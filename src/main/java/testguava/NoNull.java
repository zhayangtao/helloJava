package testguava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Objects;

import static utils.PrintUtils.print;


/**
 * @author zhayangtao
 * @version 1.0
 * @since 2017/12/15
 */
public class NoNull {
    /**
     * Optional测试
     */
    public void testOptional() {
        Optional<Integer> possible = Optional.of(2);  // 快速失败
        Optional<Integer> absent = Optional.absent(); // 引用缺失的实例
        Optional<Integer> nullAble = Optional.fromNullable(null); // 若引用为null则表示缺失

        nullAble.isPresent();
        nullAble.get();
        nullAble.or(1);
        nullAble.orNull();
        nullAble.asSet();
    }

    /**
     *
     */
    public void testObjects() {
        Objects.equal("1", "1");
        Objects.equal(null, "a");
        Objects.hashCode(null);
        System.out.println(MoreObjects.toStringHelper(this).add("x", 1).toString());
        System.out.println(MoreObjects.toStringHelper("MyObject").add("x", 1).toString());
        print(1);
        print(2);

    }
}
