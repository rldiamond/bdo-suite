import common.application.ModuleRegistration;
import common.jfx.FXUtil;
import display.main.RootDisplayPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import module.marketapi.MarketApiBdoModule;
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

        RootDisplayPane rootDisplayPane = new RootDisplayPane();
        //Register all modules for now
        Arrays.stream(ModuleRegistration.values()).forEach(rootDisplayPane::registerModule);

        //Register this separately for now as theres no usage..
        MarketApiBdoModule module = new MarketApiBdoModule();

        Scene scene = new Scene(rootDisplayPane);
        FXUtil.setThemeOnScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
