package module.barter.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BarterPlan {

    private double profit;
    private int parley;
    private List<PlannedRoute> routes = new ArrayList<>();

    public BarterPlan() {

    }

    public void addRoute(PlannedRoute route) {
        routes.add(route);
    }

    public void addProfit(double profit) {
        this.profit += profit;
    }

    public void addParley(int parley) {
        this.parley += parley;
    }

    public double getProfit() {
        return profit;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        for (PlannedRoute route : routes) {
            sb.append(route.getDescription()).append("\n");
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        sb.append("Parley: ").append(formatter.format(parley)).append("\n");
        sb.append("Profit: ").append(formatter.format(profit)).append("s");
        return sb.toString();
    }


}
