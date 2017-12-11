/**
 * @author zhayangtao
 * @version 1.0
 * @since 2017/12/8
 */
public class SwitchCaseTest {

    public static void main(String[] args) {
        String mode = "1";
        switch (mode) {
            case "Active":
                System.out.println("active");
                break;
            case "Passive":
                System.out.println("Passive");
                break;
            default:
                System.out.println("default");
        }
    }
}
