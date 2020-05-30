import common.application.ModuleRegistration;
import common.jfx.FXUtil;
import display.main.RootDisplayPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Arrays;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Configurator.setRootLevel(Level.INFO);

        primaryStage.setTitle("BDO Suite");
        primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/theme/icon.png")));

        //load all this in the bg
        RootDisplayPane rootDisplayPane = RootDisplayPane.getInstance();
        Arrays.stream(ModuleRegistration.values()).forEach(rootDisplayPane::registerModule);
        Scene scene = new Scene(rootDisplayPane);
        FXUtil.setThemeOnScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
