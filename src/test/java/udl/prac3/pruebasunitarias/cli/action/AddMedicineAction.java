package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.data.ProductID;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;

public class AddMedicineAction implements Runnable {

    @Override
    public void run() {
        try {
            System.out.print("ProductID (12 digits): ");
            String code = Application.STDIN.nextLine().trim();

            ProductID prod = new ProductID(code);

            System.out.print("Day moment: ");
            String dm = Application.STDIN.nextLine().trim().toUpperCase();

            System.out.print("Duration: ");
            String dur = Application.STDIN.nextLine().trim();

            System.out.print("Dose: ");
            String dose = Application.STDIN.nextLine().trim();

            System.out.print("Frequency: ");
            String freq = Application.STDIN.nextLine().trim();

            System.out.print("Freq unit: ");
            String unit = Application.STDIN.nextLine().trim().toUpperCase();

            String[] guidelines = {dm, dur, dose, freq, unit};

            Application.terminal.enterMedicineWithGuidelines(prod, guidelines);

            // 🔑 Guardamos el producto creado
            Application.currentProduct = prod;

            System.out.println("Medicine added");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
