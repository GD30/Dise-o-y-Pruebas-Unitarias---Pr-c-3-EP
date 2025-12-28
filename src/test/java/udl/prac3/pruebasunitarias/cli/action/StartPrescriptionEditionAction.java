package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.ProceduralException;

public class StartPrescriptionEditionAction implements Runnable {

    @Override
    public void run() {
        try {
            Application.terminal.initMedicalPrescriptionEdition();
            System.out.println("✓ Prescription edition started");
        } catch (ProceduralException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
