package module.barter.model;

/**
 * Object model for the route planned by the barter algorithms.
 */
public class PlannedRoute {

    private String title;
    private String description;
    private double exchanges;

    /**
     * Construct a PlannedRoute with the provided title and number of exchanged.
     *
     * @param title     The title of the PlannedRoute.
     * @param exchanges The number of exchanges to complete for this PlannedRoute.
     */
    public PlannedRoute(String title, double exchanges) {
        this.title = title;
        this.exchanges = exchanges;
    }

    /**
     * @return The title for the plan.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The number of exchanges to complete in this plan.
     */
    public double getExchanges() {
        return exchanges;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "ROUTE: " + title + ", EXCHANGES: " + exchanges;
    }
}
