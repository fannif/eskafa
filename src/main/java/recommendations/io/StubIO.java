package recommendations.io;

import java.util.ArrayList;
import java.util.Scanner;

public class StubIO  implements IO{

    private ArrayList<String> inputs;
    private ArrayList<String> outputs;
    private int i;

    public StubIO(ArrayList<String> inputs) {
        this.inputs = inputs;
        this.outputs = new ArrayList<>();
    }

    @Override
    public void print(String toPrint) {
        //System.out.println(toPrint);
        this.outputs.add(toPrint);
    }

    @Override
    public String read() {
        if (i < this.inputs.size()) {
            String read = this.inputs.get(i++);
            //System.out.println(read);
            return read;
        }
        return "";
    }

    public ArrayList<String> getOutputs() {
        return outputs;
    }
}
