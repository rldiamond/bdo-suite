package module.gardening.display;

import common.utilities.TextUtil;
import javafx.scene.control.TableCell;
import module.gardening.model.CropAnalysis;
import module.gardening.model.GardeningSettings;

public class SeedTableCell extends TableCell<CropAnalysis, CropAnalysis> {

    public SeedTableCell() {

    }

    @Override
    protected void updateItem(CropAnalysis item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText("");
        } else {

            double grids = GardeningSettings.getSettings().getPlayerFence().getGrids() * GardeningSettings.getSettings().getNumberOfFences();

            double seedsNeeded = grids / item.getCrop().getGrids();
            setText(TextUtil.formatWithCommas(seedsNeeded));
            if (seedsNeeded > item.getSeedAvailability()) {
                setStyle("-fx-text-fill: darkred");
            } else {
                setStyle("");
            }

        }

    }
}
