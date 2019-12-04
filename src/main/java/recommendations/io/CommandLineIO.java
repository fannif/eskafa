
package recommendations.io;

import java.util.Scanner;

public class CommandLineIO implements IO {

    private Scanner scanner;
    
    public CommandLineIO(Scanner scanner) {
        this.scanner = scanner;
    }

    public void print(String toPrint) {
        System.out.println(toPrint);
    }

    public String read() {
        return scanner.nextLine();
    }



}
