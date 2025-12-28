package udl.prac3.pruebasunitarias.cli;

import udl.prac3.pruebasunitarias.cli.action.*;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;
import udl.prac3.pruebasunitarias.medicalconsultation.ConsultationTerminal;
import udl.prac3.pruebasunitarias.services.DecisionMakingAI;
import udl.prac3.pruebasunitarias.services.DecisionMakingAIImpl;
import udl.prac3.pruebasunitarias.services.HealthNationalService;
import udl.prac3.pruebasunitarias.services.HealthNationalServiceImpl;

import java.util.Scanner;

public class Application {
    public static final Scanner STDIN = new Scanner(System.in);

    public static final ConsultationTerminal TERMINAL = new ConsultationTerminal();
    public static final HealthNationalService HNS = new HealthNationalServiceImpl();
    public static final DecisionMakingAI AI = new DecisionMakingAIImpl();

    static final Action[] AVAILABLE_ACTIONS = {
            new InitRevisionAction(),        // [0]
            new AddAssessmentAction(),       // [1]
            new StartPrescriptionAction(),   // [2]
            new AddMedicineAction(),         // [3]
            new ModifyDoseAction(),          // [4]
            new SetEndingDateAction(),       // [5]
            new FinishPrescriptionAction(),  // [6]
            new StampSignatureAction(),      // [7]
            new SendAction(),                // [8]
            new DemoAction()                 // [9]
    };

    static Action currentAction;

    public static void main(String[] args) {
        // Dependencias obligatorias
        TERMINAL.setHealthNationalService(HNS);
        TERMINAL.setDecisionMakingAI(AI);

        do {
            System.out.println("\n=== Medical Consultation CLI ===");
            System.out.println("State: " + TERMINAL.getState());

            currentAction = pickAction();
            if (currentAction != null) {
                try {
                    currentAction.run();
                } catch (Exception e) {
                    System.out.println(ConsoleColors.RED + "ERROR: " + prettyError(e) + ConsoleColors.RESET);
                }
            }
        } while (currentAction != null);

        System.out.println("Medical Consultation terminal terminated");
        STDIN.close();
    }

    static Action pickAction() {
        System.out.println("\nPick an action:\n" + ConsoleColors.YELLOW);
        System.out.println("[0] Init revision");
        System.out.println("[1] Add medical assessment");
        System.out.println("[2] Start prescription edition");
        System.out.println("[3] Add medicine with guidelines");
        System.out.println("[4] Modify dose in line");
        System.out.println("[5] Set treatment ending date");
        System.out.println("[6] Finish prescription edition");
        System.out.println("[7] Stamp signature");
        System.out.println("[8] Send history and prescription");
        System.out.println("[9] DEMO full simulation");
        System.out.println("[*] Exit" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.CYAN + "\nSelect your action: " + ConsoleColors.RESET);

        try {
            String input = STDIN.nextLine().trim();
            int c = Integer.parseInt(input);

            if (c < 0 || c >= AVAILABLE_ACTIONS.length) {
                System.out.println(ConsoleColors.RED + "ERROR: option out of range." + ConsoleColors.RESET);
                return null;
            }
            return AVAILABLE_ACTIONS[c];

        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return STDIN.nextLine().trim();
    }

    private static String prettyError(Exception e) {
        String msg = e.getMessage();
        if (msg != null && !msg.isBlank()) return msg;
        return e.getClass().getSimpleName();
    }
}
