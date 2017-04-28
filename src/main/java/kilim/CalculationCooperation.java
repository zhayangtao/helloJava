package kilim;

/**
 * Created by shliangyan on 2017/4/27.
 */
public class CalculationCooperation {
    public static void main(String[] args) {
        Mailbox<Calculation> sharedMailbox = new Mailbox<>();

        Task deferred  = new DeferredDivision(sharedMailbox);
        Task calculator = new Calculator(sharedMailbox);

        deferred.start();
        calculator.start();
    }
}
