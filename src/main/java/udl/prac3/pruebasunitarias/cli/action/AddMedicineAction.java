package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;
import udl.prac3.pruebasunitarias.data.ProductID;
import udl.prac3.pruebasunitarias.medicalconsultation.FqUnit;
import udl.prac3.pruebasunitarias.medicalconsultation.dayMoment;

import java.util.Arrays;

public class AddMedicineAction implements Action {
    @Override
    public void run() throws Exception {
        String productInput = Application.readLine("ProductID (12 chars): ");
        ProductID prod = new ProductID(productInput);

        System.out.println("dayMoment options: " + Arrays.toString(dayMoment.values()));
        String dm = Application.readLine("dayMoment: ");
        String dmNorm = dm.trim().toUpperCase().replace("-", "").replace("_", "");
        // Si no existe, valueOf revienta y el mensaje de error salta al menú
        dayMoment.valueOf(dmNorm);

        String duration = Application.readLine("duration (days, ex: 30): ");
        String dose = Application.readLine("dose (ex: 2.0): ");
        String freq = Application.readLine("freq (ex: 1.0): ");

        System.out.println("FqUnit options: " + Arrays.toString(FqUnit.values()));
        String unit = Application.readLine("FqUnit: ");
        String unitNorm = unit.trim().toUpperCase().replace("-", "_");
        FqUnit.valueOf(unitNorm);

        String instr = Application.readLine("instructions (optional): ");

        String[] guidelines = instr.isBlank()
                ? new String[]{dmNorm, duration, dose, freq, unitNorm}
                : new String[]{dmNorm, duration, dose, freq, unitNorm, instr};

        Application.TERMINAL.enterMedicineWithGuidelines(prod, guidelines);
        System.out.println(ConsoleColors.GREEN + "Medicine added." + ConsoleColors.RESET);
    }
}
