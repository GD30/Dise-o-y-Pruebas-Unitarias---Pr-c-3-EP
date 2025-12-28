package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;

public class SendPrescriptionAction implements Runnable {

    @Override
    public void run() {
        try {
            Application.terminal.sendHistoryAndPrescription();
            System.out.println("✓ Prescription sent");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
