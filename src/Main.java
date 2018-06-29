import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static LocalDate startDate;
    private static LocalDate endDate;

    public static void main(String[] args) {
        //read
        String filePath = "resources/TRXC (1).csv";
        DataReader dataReader = new DataReader(filePath);
        List<Day> days = dataReader.scanFileToList();

        //temp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateStr = "2016-06-22";
        String endDateStr = "2018-01-07";
        //can use the below start and end to do a range. Currently not used-- need to refactor
        //startDate = LocalDate.parse(startDateStr, formatter);
        //endDate = LocalDate.parse(endDateStr, formatter);

        //filter
        List<Day> sortedDays = days.stream()
                .filter(x -> {
                    LocalDate date = x.getDate();
                    return !(((startDate != null) && date.isBefore(startDate)) ||
                            ((endDate != null) && date.isAfter(endDate)));
                })
                .sorted(Comparator.comparing((Day d) -> d.getDate()))
                .collect(Collectors.toList());

        //display graph
        GraphPanel graph = new GraphPanel(sortedDays);

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
