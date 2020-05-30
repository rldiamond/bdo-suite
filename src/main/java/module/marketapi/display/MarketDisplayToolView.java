package module.marketapi.display;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import common.jfx.FXUtil;
import common.jfx.LayoutBuilder;
import common.jfx.components.Card;
import common.logging.AppLogger;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import common.utilities.TextUtil;
import common.utilities.ToastUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import module.display.ToolView;

public class MarketDisplayToolView extends ToolView {

    private static final AppLogger logger = AppLogger.getLogger();

    public MarketDisplayToolView() {
        super("Market");
        //clear
        getChildren().clear();
        initializeIncomeCalculator();
    }

    private void initializeIncomeCalculator() {
        Card incomeCalculatorCard = new Card("Calculate Income");

        VBox inputContainer = new VBox(10);
        JFXTextField sellPriceField = LayoutBuilder.createTextField("Sale Price: ", "Enter the sale price of the item to calculate net income for.", inputContainer);
        JFXToggleButton valuePackToggle = LayoutBuilder.createToggleButton("Value Pack: ", "Turn on if you have an active Value Pack.", inputContainer);
        Label resultLabel = new Label();

        VBox footerContainer = new VBox(10);
        footerContainer.setAlignment(Pos.CENTER);
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        JFXButton calculateButton = new JFXButton("CALCULATE");
        buttonContainer.getChildren().setAll(calculateButton);
        footerContainer.getChildren().setAll(resultLabel, buttonContainer);

        incomeCalculatorCard.setDisplayedContent(inputContainer);
        incomeCalculatorCard.setFooterContent(footerContainer);

        this.getChildren().add(incomeCalculatorCard);

        calculateButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {

                BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
                    double salePrice = 0;
                    try {
                        salePrice = Double.parseDouble(sellPriceField.getText().trim());
                    } catch (NumberFormatException ex) {
                        logger.debug("Failed to parse entered sale price of: " + sellPriceField.getText());
                        ToastUtil.sendErrorToast("Invalid sale price entered.");
                        return;
                    }

                    double net = salePrice - (salePrice * 0.35);

                    if (valuePackToggle.isSelected())  {
                        net = net + (net * .305);
                    }

                    final double display = net;

                    FXUtil.runOnFXThread(() -> resultLabel.setText(TextUtil.formatAsSilver(display)));

                }));

            }
        });

    }
}
