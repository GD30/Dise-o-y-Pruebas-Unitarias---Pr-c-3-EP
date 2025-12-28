package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;

public class AddAssessmentAction implements Runnable {

    @Override
    public void run() {
        try {
            System.out.print("Medical assessment: ");
            String assess = Application.STDIN.nextLine().trim();

            Application.terminal.enterMedicalAssessmentInHistory(assess);
            System.out.println("Assessment added");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
