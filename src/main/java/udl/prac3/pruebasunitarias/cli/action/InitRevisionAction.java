package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;
import udl.prac3.pruebasunitarias.data.HealthCardID;

public class InitRevisionAction implements Action {
    @Override
    public void run() throws Exception {
        String cipInput = Application.readLine("HealthCardID (16 chars): ");
        String illness = Application.readLine("Illness: ");

        Application.TERMINAL.initRevision(new HealthCardID(cipInput), illness);
        System.out.println(ConsoleColors.GREEN + "Revision initialized, history downloaded." + ConsoleColors.RESET);
    }
}
