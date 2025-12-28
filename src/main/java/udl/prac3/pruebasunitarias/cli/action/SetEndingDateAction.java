package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;

import java.util.Calendar;
import java.util.Date;

public class SetEndingDateAction implements Action {
    @Override
    public void run() throws Exception {
        String monthsStr = Application.readLine("Treatment duration (months): ");
        int months = Integer.parseInt(monthsStr);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        Date endDate = cal.getTime();

        Application.TERMINAL.enterTreatmentEndingDate(endDate);
        System.out.println(ConsoleColors.GREEN + "Ending date set." + ConsoleColors.RESET);
    }
}
