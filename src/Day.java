import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.IntStream;

public class Day {
    private LocalDate date;
    private Optional<Double> open;
    private Optional<Double> high;
    private Optional<Double> low;
    private Optional<Double> close;
    private Optional<Double> adjClose;
    private Optional<Double> volume;

    public Day(String csvString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] dayArray = csvString.split(",");
        IntStream.range(0, dayArray.length)
                .filter(i -> dayArray[i].equals("null") || dayArray[i].length() == 0)
                .forEach(i -> dayArray[i] = "-1");
        this.date = LocalDate.parse(dayArray[0], formatter);
        this.open = Optional.of(Double.parseDouble((dayArray[1])));
        this.high = Optional.of(Double.parseDouble((dayArray[2])));
        this.low = Optional.of(Double.parseDouble((dayArray[3])));
        this.close = Optional.of(Double.parseDouble((dayArray[4])));
        this.adjClose = Optional.of(Double.parseDouble((dayArray[5])));
        this.volume = Optional.of(Double.parseDouble((dayArray[6])));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Optional<Double> getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = Optional.of(open);
    }

    public Optional<Double> getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = Optional.of(high);
    }

    public Optional<Double> getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = Optional.of(low);
    }

    public Optional<Double> getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = Optional.of(close);
    }

    public Optional<Double> getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = Optional.of(adjClose);
    }

    public Optional<Double> getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = Optional.of(volume);
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
