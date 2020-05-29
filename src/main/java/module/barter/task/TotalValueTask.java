package module.barter.task;

import common.jfx.FXUtil;
import common.task.BackgroundTask;
import common.utilities.FileUtil;
import common.utilities.TextUtil;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevel;
import module.barter.model.PlayerStorageLocations;
import module.barter.model.StorageLocation;
import module.barter.storage.StorageItem;
import module.marketapi.MarketDAO;

public class TotalValueTask extends BackgroundTask {

    private StringProperty stringProperty;
    private final TextField crowCoins;

    public TotalValueTask(StringProperty stringProperty, TextField crowCoins) {
        this.stringProperty = stringProperty;
        this.crowCoins = crowCoins;
    }

    @Override
    public void doTask() {
        PlayerStorageLocations storageLocations = FileUtil.loadModuleData(PlayerStorageLocations.class);

        double totalValue = 0;

        for (StorageLocation storageLocation : storageLocations.getStorageLocations()) {
            double value = 0;
            for (StorageItem storedItem : storageLocation.getStorage().getStoredItems()) {
                BarterGood good = BarterGood.getBarterGoodByName(storedItem.getName()).get();
                BarterLevel level = BarterLevel.getBarterLevelByType(good.getLevelType());
                value = value + (storedItem.getAmount() * level.getValue());
            }
            totalValue += value;
        }
        try {
            double numCoins = Double.parseDouble(crowCoins.getText());
            totalValue += (MarketDAO.getInstance().getCrowCoinValue() * numCoins);
        } catch (Exception ex) {
            //
        }

        final double lambda = totalValue;

        FXUtil.runOnFXThread(() -> stringProperty.setValue(TextUtil.formatAsSilver(lambda)));
    }
}
