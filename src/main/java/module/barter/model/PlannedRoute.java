package module.barter.model;

public class PlannedRoute {

    String title;
    double exchanges;

    public PlannedRoute(String title, double exchanges) {
        this.title = title;
        this.exchanges = exchanges;
    }

    public String getTitle() {
        return title;
    }

    public double getExchanges() {
        return exchanges;
    }

    @Override
    public String toString() {
        return "ROUTE: " + title + ", EXCHANGES: " + exchanges;
    }
}
