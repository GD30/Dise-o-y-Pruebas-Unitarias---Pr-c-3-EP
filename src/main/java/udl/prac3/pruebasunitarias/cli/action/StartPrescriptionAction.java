package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;

public class StartPrescriptionAction implements Action {
    @Override
    public void run() throws Exception {
        Application.TERMINAL.initMedicalPrescriptionEdition();
        System.out.println(ConsoleColors.GREEN + "Prescription edition started." + ConsoleColors.RESET);
    }
}
