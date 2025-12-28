package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.data.HealthCardID;

public class StartConsultationAction implements Runnable {

    @Override
    public void run() {
        try {
            System.out.print("HealthCardID (16 alphanumeric): ");
            String cipStr = Application.STDIN.nextLine().trim();
            HealthCardID cip = new HealthCardID(cipStr);

            System.out.print("Illness: ");
            String illness = Application.STDIN.nextLine().trim();

            Application.terminal.initRevision(cip, illness);
            System.out.println("Consultation initialized");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
