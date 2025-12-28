package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;

public class FinishPrescriptionAction implements Action {
    @Override
    public void run() throws Exception {
        Application.TERMINAL.finishMedicalPrescriptionEdition();
        System.out.println(ConsoleColors.GREEN + "Prescription edition finished." + ConsoleColors.RESET);
    }
}
