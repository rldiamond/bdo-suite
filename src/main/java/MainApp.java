import common.json.JsonFileReader;
import display.RootDisplayPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import module.barter.BarterBdoModule;
import module.barter.BarterOptimizer;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;

import java.io.File;
import java.util.List;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BDO Suite");

        RootDisplayPane rootDisplayPane = new RootDisplayPane();
        rootDisplayPane.loadModule(new BarterBdoModule());

        Scene scene = new Scene(rootDisplayPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
