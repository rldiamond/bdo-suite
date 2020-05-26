package module.gardening.model;

public class GardeningSettings {

    private Fence playerFence;
    private int numberOfFences;

    public Fence getPlayerFence() {
        return playerFence;
    }

    public void setPlayerFence(Fence playerFence) {
        this.playerFence = playerFence;
    }

    public int getNumberOfFences() {
        return numberOfFences;
    }

    public void setNumberOfFences(int numberOfFences) {
        this.numberOfFences = numberOfFences;
    }
}
