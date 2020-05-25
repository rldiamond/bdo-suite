package module.gardening.display;

import common.utilities.TextUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import module.gardening.model.CropAnalysis;

import java.util.Arrays;
import java.util.List;

public class GardenProfitabilityTable extends TableView<CropAnalysis> {

    public GardenProfitabilityTable() {
        setPlaceholder(new Label("Loading data. Please wait."));

        //table configuration
        setEditable(false);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(buildColumns());
    }

    private List<TableColumn<CropAnalysis, ?>> buildColumns() {
        TableColumn<CropAnalysis, String> cropNameCol = new TableColumn<>("Crop");
        cropNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropName()));

//        TableColumn<CropAnalysis, String> seedsNeeded = new TableColumn<>("Seeds Needed");
//        seedsNeeded.setCellValueFactory(cellData -> {
//            // calculate the number of seeds needed..
//
//
//        });

        TableColumn<CropAnalysis, String> profitCol = new TableColumn<>("Profit");
        profitCol.setCellValueFactory(cellData -> new SimpleStringProperty(TextUtil.formatAsSilver(cellData.getValue().getValuePerHarvest())));

        return Arrays.asList(cropNameCol, profitCol);
    }

}
