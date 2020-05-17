import common.json.JsonFileReader;
import module.barter.BarterOptimizer;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;

import java.io.File;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("You must supply location of the barter JSON file.");
            System.exit(1);
            return;
        }
        System.out.println("Running barter optimization..");

        String url = args[0];
        List<BarterRoute> possibleRoutes;

        try {
            possibleRoutes = JsonFileReader.readListFromFile(new File(url));
        } catch (Exception ex) {
            System.out.println("Failed to read the JSON file.");
            ex.printStackTrace();
            System.exit(1);
            return;
        }

        if (possibleRoutes == null || possibleRoutes.isEmpty()) {
            System.out.println("Internal error occurred. No possible routes to examine.");
            System.exit(1);
            return;
        }

        BarterPlan barterPlan = BarterOptimizer.optimize(possibleRoutes);
        System.out.println(barterPlan);

    }

}
