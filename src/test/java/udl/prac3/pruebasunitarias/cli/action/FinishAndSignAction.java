package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;

import java.util.Calendar;
import java.util.Date;

public class FinishAndSignAction implements Runnable {

    @Override
    public void run() {
        try {
            Application.terminal.finishMedicalPrescriptionEdition();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 1);
            Date endDate = cal.getTime();

            Application.terminal.enterTreatmentEndingDate(endDate);
            Application.terminal.stampeeSignature();

            System.out.println("✓ Prescription finished and signed");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
