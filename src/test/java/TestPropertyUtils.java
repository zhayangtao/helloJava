import org.junit.Test;
import org.apache.commons.beanutils.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by shliangyan on 2017/4/26.
 */
public class TestPropertyUtils {
    @Test
    public void test() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Student student = new Student();
        student.setName("111");
        student.setAddress("SH");
        CopyStudent copyStudent = new CopyStudent();

        long start = System.currentTimeMillis();

        PropertyUtils.copyProperties(copyStudent, student);

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(copyStudent);

        start = System.currentTimeMillis();

        copyStudent.setName(student.getName());
        copyStudent.setAddress(student.getAddress());
        copyStudent.setSex(null);

        end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(copyStudent);
    }
}
