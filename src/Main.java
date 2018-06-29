import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String filePath = "resources/TRXC (1).csv";
        DataReader dataReader = new DataReader(filePath);
        List<Day> days = dataReader.scanFileToList();
        //System.out.println("size:" + days.size());
        //days.forEach(System.out::println);
        GraphPanel graph = new GraphPanel(days);
        setupFrame(graph);
    }

    private static void setupFrame(GraphPanel graph) {
        //setup frame
        JFrame frame = new JFrame("TRXC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(graph, BorderLayout.CENTER);

        //frame.pack();
        frame.setVisible(true);
    }
}
