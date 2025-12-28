package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;
import udl.prac3.pruebasunitarias.data.HealthCardID;
import udl.prac3.pruebasunitarias.data.ProductID;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalPrescription;

import java.time.LocalDate;

public class DemoAction implements Action {

    @Override
    public void run() throws Exception {
        System.out.println(ConsoleColors.YELLOW + "\nDEMO: Full simulation" + ConsoleColors.RESET);

        // Demo values
        HealthCardID cip = new HealthCardID("ABCD1234EFGH5678");
        String illness = "MIGRAINE";
        ProductID prod = new ProductID("123456789001");

        // 1) Init revision
        step("Init revision", () -> {
            showState();
            Application.TERMINAL.initRevision(cip, illness);
            System.out.println("HealthCardID: " + cip);
            System.out.println("Illness: " + illness);
            showState();
        });

        // 2) Add assessment
        step("Add medical assessment", () -> {
            showState();
            String assessment = "Patient reports recurrent headaches.";
            Application.TERMINAL.enterMedicalAssessmentInHistory(assessment);
            System.out.println("Assessment added: " + assessment);
            showState();
        });

        // 3) Start prescription edition
        step("Start prescription edition", () -> {
            showState();
            Application.TERMINAL.initMedicalPrescriptionEdition();
            showState();
        });

        // 4) Add medicine with guidelines
        step("Add medicine with guidelines", () -> {
            showState();

            String[] g = { "BEFORELUNCH", "30", "2.0", "1.0", "DAY", "Take with water" };

            System.out.println("ProductID: " + prod);
            System.out.println("Guidelines:");
            System.out.println("  dayMoment   = " + g[0]);
            System.out.println("  duration    = " + g[1] + " days");
            System.out.println("  dose        = " + g[2]);
            System.out.println("  freq        = " + g[3]);
            System.out.println("  fqUnit      = " + g[4]);
            System.out.println("  instructions= " + g[5]);

            Application.TERMINAL.enterMedicineWithGuidelines(prod, g);

            Application.TERMINAL.modifyDoseInLine(prod, 3.0f);
            System.out.println("Dose modified to 3.0");

            showState();
        });

        // 5) Set ending date
        step("Set treatment ending date", () -> {
            showState();
            LocalDate end = LocalDate.now().plusMonths(1);
            Application.TERMINAL.enterTreatmentEndingDate(java.sql.Date.valueOf(end));
            System.out.println("Ending date set to: " + end);
            showState();
        });

        // 6) Finish prescription edition
        step("Finish prescription edition", () -> {
            showState();
            Application.TERMINAL.finishMedicalPrescriptionEdition();
            showState();
        });

        // 7) Stamp signature
        step("Stamp signature", () -> {
            showState();
            Application.TERMINAL.stampeeSignature();
            System.out.println("Signature stamped.");
            showState();
        });

        // 8) Send history and prescription
        step("Send history and prescription", () -> {
            showState();
            MedicalPrescription sent = Application.TERMINAL.sendHistoryAndPrescription();
            System.out.println(ConsoleColors.GREEN + "Prescription sent OK." + ConsoleColors.RESET);

            // Mostrar algo del resultado sin asumir métodos que quizá no existen
            if (sent != null) {
                System.out.println("Returned prescription object: " + sent.getClass().getSimpleName());
                System.out.println("Prescription (toString): " + sent);
            }

            showState();
        });

        // 9) Optional: AI
        stepOptional("AI suggestions", () -> {
            showState();
            Application.TERMINAL.callDecisionMakingAI();
            System.out.println("AI initialized.");

            String prompt = "Suggest improvements for migraine treatment";
            Application.TERMINAL.askAIForSuggest(prompt);
            System.out.println("Prompt: " + prompt);
            System.out.println("AI answer: " + Application.TERMINAL.getAiAnswer());

            Application.TERMINAL.extractGuidelinesFromSugg();
            var sugg = Application.TERMINAL.getSuggestions();
            System.out.println("Extracted suggestions: " + (sugg == null ? 0 : sugg.size()));
            if (sugg != null) {
                for (var s : sugg) System.out.println(" - " + s);
            }
            showState();
        });

        System.out.println(ConsoleColors.GREEN + "\n=== DEMO COMPLETED ===" + ConsoleColors.RESET);
    }

    // helpers

    private void showState() {
        System.out.println("State: " + ConsoleColors.CYAN + Application.TERMINAL.getState() + ConsoleColors.RESET);
    }

    private void step(String title, CheckedRunnable r) throws Exception {
        System.out.println(ConsoleColors.YELLOW + "\n" + title + ConsoleColors.RESET);
        try {
            r.run();
            System.out.println(ConsoleColors.GREEN + "✓ OK: " + title + ConsoleColors.RESET);
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "✗ FAIL: " + title + ConsoleColors.RESET);
            throw e;
        }
    }

    private void stepOptional(String title, CheckedRunnable r) {
        System.out.println(ConsoleColors.YELLOW + "\n"+title + ConsoleColors.RESET);
        try {
            r.run();
            System.out.println(ConsoleColors.GREEN + "✓ OK: " + title + ConsoleColors.RESET);
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank()) ? e.getClass().getSimpleName() : e.getMessage();
            System.out.println(ConsoleColors.RED + "Skipped: " + title + " (" + msg + ")" + ConsoleColors.RESET);
        }
    }

    private interface CheckedRunnable {
        void run() throws Exception;
    }
}
