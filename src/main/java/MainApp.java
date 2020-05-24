import common.application.ModuleRegistration;
import common.jfx.FXUtil;
import display.main.RootDisplayPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BDO Suite");

        RootDisplayPane rootDisplayPane = new RootDisplayPane();
        //Register all modules for now
        Arrays.stream(ModuleRegistration.values()).forEach(rootDisplayPane::registerModule);

        Scene scene = new Scene(rootDisplayPane);
        FXUtil.setThemeOnScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
