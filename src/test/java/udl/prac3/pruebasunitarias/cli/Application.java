package udl.prac3.pruebasunitarias.cli;

import udl.prac3.pruebasunitarias.cli.action.*;
import udl.prac3.pruebasunitarias.medicalconsultation.ConsultationTerminal;
import udl.prac3.pruebasunitarias.services.*;
import udl.prac3.pruebasunitarias.data.*;

import java.util.Scanner;

public class Application {

    public static final Scanner STDIN = new Scanner(System.in);
    public static ConsultationTerminal terminal;
    public static ProductID currentProduct;

    static Runnable[] ACTIONS;

    public static void main(String[] args) {

        terminal = new ConsultationTerminal();
        terminal.setHealthNationalService(new HealthNationalServiceImpl());
        terminal.setDecisionMakingAI(new DecisionMakingAIImpl());

        ACTIONS = new Runnable[]{
                new StartConsultationAction(),         // 0
                new AddAssessmentAction(),             // 1
                new StartPrescriptionEditionAction(),  // 2
                new AddMedicineAction(),               // 3
                new ModifyDoseAction(),                // 4
                new FinishAndSignAction(),             // 5
                new SendPrescriptionAction()           // 6
        };

        while (true) {
            showMenu();
            Runnable action = pickAction();
            if (action == null) break;
            action.run();
        }

        STDIN.close();
        System.out.println("Application closed");
    }

    static void showMenu() {
        System.out.println("\n=== Medical Prescription System ===");
        System.out.println("[0] Start new consultation");
        System.out.println("[1] Add medical assessment");
        System.out.println("[2] Start prescription edition");
        System.out.println("[3] Add medicine");
        System.out.println("[4] Modify dose");
        System.out.println("[5] Finish & sign prescription");
        System.out.println("[6] Send prescription");
        System.out.println("[*] Exit");
    }

    static Runnable pickAction() {
        System.out.print("Select option: ");
        String input = STDIN.nextLine().trim();

        if (input.equals("*")) return null;

        try {
            int opt = Integer.parseInt(input);
            if (opt < 0 || opt >= ACTIONS.length) {
                System.out.println("Invalid option");
                return () -> {}; // no-op, vuelve al menú
            }
            return ACTIONS[opt];
        } catch (NumberFormatException e) {
            System.out.println("Invalid option");
            return () -> {}; // no-op
        }
    }
}
