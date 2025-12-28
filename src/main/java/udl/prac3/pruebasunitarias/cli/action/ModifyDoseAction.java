package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;
import udl.prac3.pruebasunitarias.data.ProductID;

public class ModifyDoseAction implements Action {
    @Override
    public void run() throws Exception {
        String productInput = Application.readLine("ProductID (12 chars): ");
        ProductID prod = new ProductID(productInput);

        String doseInput = Application.readLine("New dose (float, ex: 20.0): ");
        float newDose = Float.parseFloat(doseInput);

        Application.TERMINAL.modifyDoseInLine(prod, newDose);
        System.out.println(ConsoleColors.GREEN + "Dose modified." + ConsoleColors.RESET);
    }
}
