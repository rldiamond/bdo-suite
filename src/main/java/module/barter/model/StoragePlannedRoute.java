package module.barter.model;

public class StoragePlannedRoute extends PlannedRoute {

    private final StorageLocation storageLocation;

    public StoragePlannedRoute(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public StorageLocation getStorageLocation() {
        return storageLocation;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Go to ").append(storageLocation.getName()).append(" storage to get ").append(getReceivedAmount()).append(" ").append(getReceivedGood().getName()).append(".");
        return sb.toString();
    }

}
