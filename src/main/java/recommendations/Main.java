package recommendations;

import ui.CommandLineUI;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        CommandLineUI ui = new CommandLineUI(reader);
        ui.start();

    }

}
