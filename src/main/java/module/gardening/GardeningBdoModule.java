package module.gardening;

import module.common.BdoModule;
import module.common.ModuleException;
import module.common.ModulePane;
import module.gardening.model.Crop;
import module.marketapi.MarketDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Module for gardening. Determines the most profitable crop to garden given seed/hyphae availability.
 */
public class GardeningBdoModule extends BdoModule {

    /**
     * Constructor.
     */
    public GardeningBdoModule() throws ModuleException {
        super("Gardening", "Determine the most profitable crops to garden.");
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        // The following are TEST data..
        Crop strawberry = new Crop();
        strawberry.setCrop("Special Strawberry Seed");
        strawberry.setSeed("Special Strawberry");

        Crop wheat = new Crop();
        wheat.setSeed("Special Wheat Seed");
        wheat.setCrop("Special Wheat");

        List<Crop> crops = new ArrayList<>();
        Collections.addAll(crops, strawberry, wheat);
        MarketDAO marketDAO = MarketDAO.getInstance();

        //TODO: Read JSON for Seeds and Crops

        // Do your code here

        for (int i = 0; i <= crops.size(); i++) {

            crops.get(i);

        }

        for (Crop crop : crops) {
            //loop through crops
            double value = marketDAO.getMarketValue(strawberry.getCrop());
            // TODO: make a for loop for crops and do the following and do a System.out.println("<PROFIT>")
            //TODO: For each crop, calculate the net profit
            // (# of fences) * (# of grids) = total number of grids
            // assume all seeds use 1 grid
            // (cost seed) * (total number of grids) = cost of the harvest
            // (cost crop) * ((total number of grids) * 30)) = Gross
            // gross -cost = profit before taxes
            // take out marketplace tax 35%
            // add back in player bonus (VP, FF) ~ 30.5%
            // net profit
        }

        crops.forEach(crop -> {

        });






    }

    /**
     * @inheritDoc
     */
    @Override
    public ModulePane getModulePane() {
        return null;
    }
}
