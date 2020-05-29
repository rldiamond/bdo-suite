package module.barter.model;

public class PlannedRoute {

    private BarterGood turnInGood;
    private int turnInAmount;
    private BarterGood receivedGood;
    private int receivedAmount;
    private int exchanges;

    public PlannedRoute() {

    }

    public int getExchanges() {
        return exchanges;
    }

    public void setExchanges(int exchanges) {
        this.exchanges = exchanges;
    }

    public BarterGood getTurnInGood() {
        return turnInGood;
    }

    public void setTurnInGood(BarterGood turnInGood) {
        this.turnInGood = turnInGood;
    }

    public int getTurnInAmount() {
        return turnInAmount;
    }

    public void setTurnInAmount(int turnInAmount) {
        this.turnInAmount = turnInAmount;
    }

    public BarterGood getReceivedGood() {
        return receivedGood;
    }

    public void setReceivedGood(BarterGood receivedGood) {
        this.receivedGood = receivedGood;
    }

    public int getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(int receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public boolean hasTurnInGood() {
        return turnInGood==null;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("Turn in ").append(getTurnInAmount()).append(" ").append(getTurnInGood().getName());
            sb.append(" for ").append(getReceivedAmount()).append(" ").append(getReceivedGood().getName()).append(".");
        } catch (Exception ex) {
            sb.append("ERROR: ");
        }
        return sb.toString();
    }
}
