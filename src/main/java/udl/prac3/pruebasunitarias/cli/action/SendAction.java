package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;

public class SendAction implements Action {
    @Override
    public void run() throws Exception {
        Application.TERMINAL.sendHistoryAndPrescription();
        System.out.println(ConsoleColors.GREEN + "Sent successfully." + ConsoleColors.RESET);
    }
}
