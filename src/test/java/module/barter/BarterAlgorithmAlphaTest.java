package module.barter;

import common.json.JsonFileReader;
import common.json.JsonParseException;
import module.barter.algorithms.BarterAlgorithmAlpha;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;
import module.barter.model.BarterTier;
import module.barter.model.PlannedRoute;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarterAlgorithmAlphaTest {

    @Test
    public void run() throws Exception {

        BarterPlan plan = BarterOptimizer.optimize(getFromJson());

        System.out.println(plan);

    }

    private List<BarterRoute> getFromJson() throws JsonParseException {
        URL url = this.getClass().getResource("barter.json");
        return JsonFileReader.readListFromFile(new File(url.getPath()));
    }

}
