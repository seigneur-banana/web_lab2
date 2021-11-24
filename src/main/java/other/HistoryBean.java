package other;

import java.util.ArrayList;

public class HistoryBean {
    private final ArrayList<Point> history;

    public HistoryBean() {
        history = new ArrayList<>();
    }

    public ArrayList<Point> getHistory() {
        return history;
    }
}