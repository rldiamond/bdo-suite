package module.barter;

import module.barter.algorithms.NewBarterAlgorithm;
import module.barter.model.Barter;
import module.barter.model.BarterPlan;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BarterAlgorithmTest {


    @Test
    public void testAlgorithm_happyPath() throws Exception {

        NewBarterAlgorithm algorithm = new NewBarterAlgorithm(createHappyPathBarters());

        BarterPlan barterPlan = algorithm.run();

    }

    private List<Barter> createHappyPathBarters() {
        List<Barter> barters = new ArrayList<>();

        Barter barter1 = new Barter();
        barter1.setAcceptAmount(1);
        barter1.setAcceptGoodName("Luxury Patterned Fabric");
        barter1.setExchangeAmount(79);
        barter1.setExchangeGoodName("Crow Coin");
        barter1.setExchanges(1);
        barter1.setParley(36749);
        barters.add(barter1);

        Barter barter2 = new Barter();
        barter2.setAcceptAmount(1);
        barter2.setAcceptGoodName("Marine Knights' Spear");
        barter2.setExchangeAmount(1);
        barter2.setExchangeGoodName("Luxury Patterned Fabric");
        barter2.setExchanges(4);
        barter2.setParley(11991);
        barters.add(barter2);

        Barter barter3 = new Barter();
        barter3.setAcceptAmount(1);
        barter3.setAcceptGoodName("Weasel Leather Coat");
        barter3.setExchangeAmount(2);
        barter3.setExchangeGoodName("Marine Knights' Spear");
        barter3.setExchanges(6);
        barter3.setParley(11991);
        barters.add(barter3);

        Barter barter4 = new Barter();
        barter4.setAcceptAmount(1);
        barter4.setAcceptGoodName("Filtered Drinking Water");
        barter4.setExchangeAmount(3);
        barter4.setExchangeGoodName("Weasel Leather Coat");
        barter4.setExchanges(4);
        barter4.setParley(11991);
        barters.add(barter4);

        Barter barter5 = new Barter();
        barter5.setAcceptAmount(1);
        barter5.setAcceptGoodName("Rakeflower Seed Pouch");
        barter5.setExchangeAmount(1);
        barter5.setExchangeGoodName("Filtered Drinking Water");
        barter5.setExchanges(2);
        barter5.setParley(11991);
        barters.add(barter5);

        Barter barter6 = new Barter();
        barter6.setAcceptAmount(40);
        barter6.setAcceptGoodName("Wool");
        barter6.setExchangeAmount(1);
        barter6.setExchangeGoodName("Rakeflower Seed Pouch");
        barter6.setExchanges(6);
        barter6.setParley(11991);
        barters.add(barter6);

        return barters;


    }


}
