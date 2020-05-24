package module.barter.model;

import java.util.ArrayList;
import java.util.List;

public class BarterPlan {

    private double profit;
    private List<PlannedRoute> routes = new ArrayList<>();

    public BarterPlan() {

    }

    public void addRoute(PlannedRoute route) {
        routes.add(route);
    }

    public void addProfit(double profit) {
        this.profit += profit;
    }

    public double getProfit() {
        return profit;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        for (PlannedRoute route : routes) {
            sb.append(route.getDescription()).append("\n");
        }
        return sb.toString();
    }


}
