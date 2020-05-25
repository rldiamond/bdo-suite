package module.gardening.display;

import common.jfx.components.ItemWithImageTableCell;
import common.utilities.TextUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import module.gardening.model.Crop;
import module.gardening.model.CropAnalysis;

import java.util.Arrays;
import java.util.List;

public class GardenProfitabilityTable extends TableView<CropAnalysis> {

    public GardenProfitabilityTable() {
        setPlaceholder(new Label("Loading data. Please wait. This can take a few minutes."));

        //table configuration
        setEditable(false);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(buildColumns());
    }

    private List<TableColumn<CropAnalysis, ?>> buildColumns() {
        TableColumn<CropAnalysis, Crop> cropCol = new TableColumn<>("Crop");
        cropCol.setCellFactory(c -> new ItemWithImageTableCell<>());
        cropCol.setCellValueFactory(new PropertyValueFactory<>("crop"));

        TableColumn<CropAnalysis, String> profitCol = new TableColumn<>("Profit");
        profitCol.setCellValueFactory(cellData -> new SimpleStringProperty(TextUtil.formatAsSilver(cellData.getValue().getValuePerHarvest())));

        return Arrays.asList(cropCol, profitCol);
    }

}
