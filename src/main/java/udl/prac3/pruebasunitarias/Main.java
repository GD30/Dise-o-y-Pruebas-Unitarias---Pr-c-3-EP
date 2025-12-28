package udl.prac3.pruebasunitarias;

import udl.prac3.pruebasunitarias.medicalconsultation.ConsultationTerminal;
import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.services.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.services.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalPrescription;

import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            ConsultationTerminal terminal = new ConsultationTerminal();
            HealthNationalService hns = new HealthNationalServiceImpl();
            DecisionMakingAI ai = new DecisionMakingAIImpl();

            terminal.setHealthNationalService(hns);
            terminal.setDecisionMakingAI(ai);

            // Leer HealthCardID del usuario
            System.out.print("Introduce HealthCardID (16 caracteres): ");
            String cipInput = scanner.nextLine().trim();
            HealthCardID cip = new HealthCardID(cipInput);

            // Leer enfermedad
            System.out.print("Introduce el nombre de la enfermedad: ");
            String illness = scanner.nextLine().trim();

            terminal.initRevision(cip, illness);
            System.out.println("Historial médico descargado.");

            // Leer observación médica
            System.out.print("Introduce observaciones médicas: ");
            String observation = scanner.nextLine().trim();
            terminal.enterMedicalAssessmentInHistory(observation);

            terminal.initMedicalPrescriptionEdition();

            // Leer información del medicamento
            System.out.print("Introduce ProductID del medicamento (12 caracteres): ");
            String productInput = scanner.nextLine().trim();
            ProductID prod = new ProductID(productInput);

            System.out.println("Introduce guidelines:");

            System.out.print("  dayMoment (ej. BEFOREBREAKFAST): ");
            String dayMoment = scanner.nextLine().trim().toUpperCase();

            System.out.print("  freqNumber (dosis por día): ");
            String freqNumber = scanner.nextLine().trim();

            System.out.print("  freqUnitNumber: ");
            String freqUnitNumber = scanner.nextLine().trim();

            System.out.print("  freqTime: ");
            String freqTime = scanner.nextLine().trim();

            System.out.print("  FqUnit (HOUR/DAY/WEEK/MONTH): ");
            String fqUnit = scanner.nextLine().trim().toUpperCase();

            String[] guidelines = {dayMoment, freqNumber, freqUnitNumber, freqTime, fqUnit};

            terminal.enterMedicineWithGuidelines(prod, guidelines);

            // Modificar dosis opcional
            System.out.print("Introduce nueva dosis (float, ejemplo 20.0) o enter para mantener: ");
            String doseInput = scanner.nextLine().trim();
            if (!doseInput.isEmpty()) {
                float newDose = Float.parseFloat(doseInput);
                terminal.modifyDoseInLine(prod, newDose);
            }

            // Leer fecha de fin del tratamiento
            System.out.print("Introduce meses de duración del tratamiento: ");
            int months = Integer.parseInt(scanner.nextLine().trim());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, months);
            Date endDate = cal.getTime();
            terminal.enterTreatmentEndingDate(endDate);

            terminal.finishMedicalPrescriptionEdition();
            terminal.stampeeSignature();

            MedicalPrescription finalPrescription = terminal.sendHistoryAndPrescription();

            System.out.println("Prescripción enviada correctamente.");
            System.out.println("Estado final: " + terminal.getState());

        } catch (ConnectException |
                 HealthCardIDException |
                 AnyCurrentPrescriptionException |
                 ProceduralException |
                 ProductAlreadyInPrescriptionException |
                 IncorrectTakingGuidelinesException |
                 ProductNotInPrescriptionException |
                 IncorrectEndingDateException |
                 eSignatureException |
                 NotCompletedMedicalPrescription |
                 ProductIDException e) {

            System.err.println("Error durante la consulta médica:");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
