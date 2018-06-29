import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

public class Day {
    private LocalDate date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjClose;
    private double volume;

    public Day(String csvString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] dayArray = csvString.split(",");
        IntStream.range(0, dayArray.length)
                .filter(i -> dayArray[i].equals("null") || dayArray[i].length() == 0)
                .forEach(i -> dayArray[i] = "-1");
        this.date = LocalDate.parse(dayArray[0], formatter);
        this.open = Double.parseDouble((dayArray[1]));
        this.high = Double.parseDouble((dayArray[2]));
        this.low = Double.parseDouble((dayArray[3]));
        this.close = Double.parseDouble((dayArray[4]));
        this.adjClose = Double.parseDouble((dayArray[5]));
        this.volume = Double.parseDouble((dayArray[6]));
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Date : ").append(this.date)
                .append(", Open :").append(this.open)
                .append(", High : ").append(this.high)
                .append(", Low :").append(this.low)
                .append(", Close : ").append(this.close)
                .append(", Adj Close :").append(this.adjClose)
                .append(", Volume : ").append(this.volume);
        return sb.toString();
    }
}
