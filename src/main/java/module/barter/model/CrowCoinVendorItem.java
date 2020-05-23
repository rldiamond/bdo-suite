package module.barter.model;


import module.marketapi.MarketDAO;
import module.marketapi.model.MarketResponse;

public class CrowCoinVendorItem {

    private double crowCoins;
    private String itemName;
    private double itemCost;

    public static CrowCoinVendorItem buildWithName(String name, double crowCoinCost) {
        MarketResponse response = MarketDAO.getInstance().searchByName(name);
        return new CrowCoinVendorItem(response.getName(), response.getPricePerOne(), crowCoinCost);
    }

    public static CrowCoinVendorItem buildWithId(long id, double crowCoinCost) {
        MarketResponse response = MarketDAO.getInstance().fetchData(id);
        return new CrowCoinVendorItem(response.getName(), response.getPricePerOne(), crowCoinCost);
    }

    private CrowCoinVendorItem(String itemName, double itemValue, double crowCoins) {
        this.itemName = itemName;
        this.crowCoins = crowCoins;
        this.itemCost = itemValue;
    }

    public double getCrowCoins() {
        return crowCoins;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemValue() {
        return itemCost;
    }
}
