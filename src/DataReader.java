import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataReader {
    String pathName;

    public DataReader(String pathName) {
        this.pathName = pathName;
    }

    public List scanFileToList() {
        List<Day> days = new ArrayList<>();
        File file = new File(pathName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            days = br.lines().skip(1)
                    .filter(x -> !x.isEmpty())
                    .map(Day::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return days;
    }
}
