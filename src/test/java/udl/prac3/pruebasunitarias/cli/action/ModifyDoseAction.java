package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;

public class ModifyDoseAction implements Runnable {

    @Override
    public void run() {
        try {
            if (Application.currentProduct == null) {
                System.out.println("No medicine to modify");
                return;
            }

            System.out.print("New dose: ");
            float dose = Float.parseFloat(Application.STDIN.nextLine());

            Application.terminal.modifyDoseInLine(
                    Application.currentProduct,
                    dose
            );

            System.out.println("✓ Dose modified");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
