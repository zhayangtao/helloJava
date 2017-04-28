package kilim;

import java.math.RoundingMode;

/**
 * Created by jovi on 2017/4/27.
 */
public class Calculator extends Task{

    private Mailbox<Calculation> mailbox;

    public Calculator(Mailbox<Calculation> mailbox) {
        this.mailbox = mailbox;
    }

    @Override
    public void execute() throws Pausable, Exception {
        while (true){
            Calculation calc = mailbox.get();
            if(calc.getAnswer() == null){
                calc.setAnswer(calc.getDividend().divide(calc.getDivisor(),8, RoundingMode.HALF_UP));
                System.out.println("Calculator determined answer");
                mailbox.putnb(calc);
            }
            Task.sleep(1000);
        }
    }
}
