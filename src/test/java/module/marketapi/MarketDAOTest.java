package module.marketapi;

import module.marketapi.model.MarketResponse;
import org.junit.Test;

public class MarketDAOTest {

    @Test
    public void test() throws Exception {

        long id = 6828;
        MarketResponse response = MarketDAO.getInstance().fetchData(id);
        System.out.println(response);

    }
}
