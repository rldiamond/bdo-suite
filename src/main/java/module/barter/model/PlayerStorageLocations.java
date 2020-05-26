package module.barter.model;

import common.application.ModuleRegistration;
import common.json.ModuleData;

import java.util.ArrayList;
import java.util.List;

public class PlayerStorageLocations implements ModuleData {

    private List<StorageLocation> storageLocations = new ArrayList<>();

    public void addStorageLocation(StorageLocation storageLocation) {
        storageLocations.add(storageLocation);
    }

    public List<StorageLocation> getStorageLocations() {
        return storageLocations;
    }

    public void setStorageLocations(List<StorageLocation> storageLocations) {
        this.storageLocations = storageLocations;
    }

    @Override
    public ModuleRegistration getModule() {
        return ModuleRegistration.BARTER;
    }

    @Override
    public String fileName() {
        return "storage";
    }
}
