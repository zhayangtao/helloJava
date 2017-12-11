package jodatime;

import org.joda.time.DateTime;

/**
 * Created by shliangyan on 2017/11/15.
 */
public class JodaTimeTest {

    public void test1() {
        DateTime dateTime = new DateTime(2017, 11, 15, 0, 0, 0);
        String finalTime = dateTime.plusDays(4).toString("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(finalTime);

        //LocalDate localDate = new LocalDate(13, 30, 26);
        //System.out.println(localDate.toString("hh:mm:ss"));
        /*(Integer e) -> {
            double sqrt = Math.sqrt(e);
            double log = Math.log(e);

            return sqrt + log;
        }*/

        test2();

    }

    private void test2() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            System.out.println("className: " + element.getClassName());
            System.out.println("methodName: " + element.getMethodName());
            System.out.println("fileName: " + element.getFileName());
            System.out.println("lineNumber: " + element.getLineNumber());
        }
    }

    public static void main(String[] args) {
    }
}
