package udl.prac3.pruebasunitarias.cli.action;

import udl.prac3.pruebasunitarias.cli.Application;
import udl.prac3.pruebasunitarias.cli.utils.ConsoleColors;

public class StampSignatureAction implements Action {
    @Override
    public void run() throws Exception {
        Application.TERMINAL.stampeeSignature();
        System.out.println(ConsoleColors.GREEN + "Signature stamped." + ConsoleColors.RESET);
    }
}
