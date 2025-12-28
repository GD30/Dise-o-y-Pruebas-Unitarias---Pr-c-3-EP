package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;

public class AddAssessmentAction implements Action {
    @Override
    public void run() throws Exception {
        String observation = Application.readLine("Medical assessment: ");
        Application.TERMINAL.enterMedicalAssessmentInHistory(observation);
        System.out.println(ConsoleColors.GREEN + "Assessment saved." + ConsoleColors.RESET);
    }
}
