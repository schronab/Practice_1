import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphPanel extends JPanel {

    private int padding = 25;
    private int twicePadding = padding * 2;
    //private int labelPadding = 25;
    private int xLabelPadding = 25;
    private int yLabelPadding = 50;
    private Color lineColor;
    private Color pointColor;
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(.5f);
    private int pointWidth = 0;
    private int numberYDivisions = 10;
    private List<Day> days;
    private Day firstDay;
    private Day lastDay;

    public GraphPanel(List<Day> days) {
        this.days = days.stream()
                .filter(x -> x.getOpen() != -1)
                .collect(Collectors.toList());

        //Green if uptrend. Red if downtrend
        this.firstDay = days.get(0);
        this.lastDay = days.get(days.size() - 1);
        if (firstDay.getOpen() > lastDay.getOpen()) {
            this.lineColor = Color.RED;
            this.pointColor = Color.RED;
        } else {
            this.lineColor = Color.GREEN;
            this.pointColor = Color.GREEN;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - twicePadding - xLabelPadding) / (days.size() - 1);
        double yScale = ((double) getHeight() - twicePadding - yLabelPadding) / (getAdditionalHeight() - getMinScore());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < days.size(); i++) {
            int x1 = (int) (i * xScale + padding + xLabelPadding);
            int y1 = (int) ((getAdditionalHeight() - days.get(i).getOpen()) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + xLabelPadding, padding,
                getWidth() - twicePadding - xLabelPadding,
                getHeight() - twicePadding - yLabelPadding
                //height + ()
        );
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        //skip first
        for (int i = 1; i < numberYDivisions + 1; i++) {
            int x0 = padding + xLabelPadding;
            int x1 = pointWidth + padding + xLabelPadding;
            int y = getHeight() - ((i * (getHeight() - twicePadding - yLabelPadding)) / numberYDivisions + padding + yLabelPadding);
            if (days.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + xLabelPadding + 1 + pointWidth, y, getWidth() - padding, y);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getAdditionalHeight() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y + (metrics.getHeight() / 2) - 3);
            }

            g2.drawLine(x0, y, x1, y);
        }

        // and for x axis
        for (int i = 0; i < days.size(); i++) {
            if (days.size() > 1) {
                int x = i * (getWidth() - twicePadding - xLabelPadding) / (days.size() - 1) + padding + xLabelPadding;
                int y0 = getHeight() - padding - yLabelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((days.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x, getHeight() - padding - xLabelPadding - 1 - pointWidth, x, padding);

                    g2.setColor(Color.BLACK);
                    String xLabel = days.get(i).getDate() + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    drawRotate(g2, x - 5, y0, 90, xLabel);
                }
                g2.drawLine(x, y0, x, y1);
            }
        }

        // create x and y axes
        g2.drawLine(padding + xLabelPadding, getHeight() - padding - yLabelPadding, padding + xLabelPadding, padding);
        g2.drawLine(padding + xLabelPadding, getHeight() - padding - yLabelPadding, getWidth() - padding, getHeight() - padding - yLabelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
            //draw fill
            g2.drawLine(x, y, x, getHeight() - padding - yLabelPadding - 1 - pointWidth);
        }
    }

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (Day day : days) {
            minScore = Math.min(minScore, day.getOpen());
        }
        return minScore;
    }

    private double getMaxScore() {
        return days.stream().mapToDouble(Day::getOpen).max().orElse(-1);
    }

    private double getAverage() {
        return days.stream().mapToDouble(Day::getOpen).average().orElse(-1);
    }

    private double getAdditionalHeight() {
        double maxScore = getMaxScore();
        return (maxScore / numberYDivisions) + maxScore;
    }

    public void setValues(List<Day> days) {
        this.days = days;
        invalidate();
        this.repaint();
    }

    public List<Day> getDays() {
        return days;
    }

    private void drawRotate(Graphics2D g2d, double x, double y, int angle, String text) {
        g2d.translate((float) x, (float) y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawString(text, 0, 0);
        g2d.rotate(-Math.toRadians(angle));
        g2d.translate(-(float) x, -(float) y);
    }
}