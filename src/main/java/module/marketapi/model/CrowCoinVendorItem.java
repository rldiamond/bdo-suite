package module.marketapi.model;


import module.marketapi.MarketDAO;

public class CrowCoinVendorItem {

    private double crowCoins;
    private String itemName;
    private double itemCost;
    private long itemNumber;

    public static CrowCoinVendorItem buildWithName(String name, double crowCoinCost) {
        MarketResponse response = MarketDAO.getInstance().searchByName(name).orElseGet(MarketResponse::new);
        return new CrowCoinVendorItem(response.getName(), response.getPricePerOne(), crowCoinCost, response.getMainKey());
    }

    public static CrowCoinVendorItem buildWithId(long id, double crowCoinCost) {
        MarketResponse response = MarketDAO.getInstance().fetchData(id);
        return new CrowCoinVendorItem(response.getName(), response.getPricePerOne(), crowCoinCost, response.getMainKey());
    }

    private CrowCoinVendorItem(String itemName, double itemValue, double crowCoins, long itemNumber) {
        this.itemName = itemName;
        this.crowCoins = crowCoins;
        this.itemCost = itemValue;
        this.itemNumber = itemNumber;
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

    public long getItemNumber() {
        return itemNumber;
    }
}
