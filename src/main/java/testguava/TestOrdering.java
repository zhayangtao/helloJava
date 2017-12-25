package testguava;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import javax.annotation.Nullable;
import java.util.Comparator;

/**
 * @author zhayangtao
 * @version 1.0
 * @since 2017/12/15
 */
public class TestOrdering {

    public void testOrdering() {
        Ordering natural = Ordering.natural(); // 自然排序，数字按大小，日期按先后
        Ordering usingToString = Ordering.usingToString();// 按对象字符串形式做字典排序
        Ordering from_Comparator = Ordering.from((Comparator<Long>) Long::compareTo);

        // 自定义排序
        Ordering<String> orderByLength = new Ordering<String>() {
            @Override
            public int compare(@Nullable String s, @Nullable String t1) {
                return Ints.compare(s.length(), t1.length());
            }
        };

        // 混合其他比较器
        Ordering reverse = orderByLength
                .reverse() // 反转排序器
                .nullsFirst() //
                .nullsLast() //
                .compound((o1, o2) -> 0); // 混合其他比较器
    }
}
