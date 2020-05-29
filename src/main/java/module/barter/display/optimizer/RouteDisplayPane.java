package module.barter.display.optimizer;

import common.jfx.components.ItemImage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import module.barter.model.PlannedRoute;

public class RouteDisplayPane extends StackPane {

    public RouteDisplayPane() {

    }

    public RouteDisplayPane(PlannedRoute route) {

        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);

        HBox wrapper = new HBox(5);
        wrapper.setAlignment(Pos.CENTER);

        Label text = new Label(route.getDescription());
        text.setStyle("-fx-font-size: 16px");
        setAlignment(Pos.CENTER);
        getChildren().setAll(container);

        //7528
        ItemImage leftImage = new ItemImage(route.getTurnInGood() != null ? route.getTurnInGood().getItemId() : 7528);
        wrapper.getChildren().addAll(leftImage, text, new ItemImage(route.getReceivedGood().getItemId()));
        container.getChildren().add(wrapper);

//
//        //if a storage route, add button to take item out of storage
//        if (route.getTurnInGood() == null) {
//
//            JFXButton removeFromStorageButton = new JFXButton("MODIFY STORAGE");
//            container.getChildren().add(removeFromStorageButton);
//            removeFromStorageButton.setOnMouseClicked(me -> {
//                if (me.getButton().equals(MouseButton.PRIMARY)) {
//                    ActionableAlertDialog alertDialog = new ActionableAlertDialog(AlertDialogType.CONFIRMATION);
//                    alertDialog.setActionButtonText("CONFIRM");
//                    alertDialog.setTitle("Are you sure?");
//                    alertDialog.setBody("This will modify your storage to complete this task.");
//                    alertDialog.setAction(() -> {
//                        StoragePlannedRoute storagePlannedRoute = (StoragePlannedRoute) route;
//
//                        storagePlannedRoute.getStorageLocation().getStorage().getItem(storagePlannedRoute.getReceivedGood().getName()).ifPresent(storageItem -> {
//                            storageItem.removeItems(storagePlannedRoute.getReceivedAmount());
//                            //todo: save..
//
//                        });
//                    });
//                }
//            });

//        }

    }

}
