package module.marketapi.model;

public class MarketResponse {

    private int chooseKey;
    private long count;
    private int grade;
    private int keyType;
    private int mainCategory;
    private int mainKey;
    private String name;
    private double pricePerOne;
    private int subCategory;
    private int subKey;
    private long totalTradeCount;

    public MarketResponse() {

    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getChooseKey() {
        return chooseKey;
    }

    public void setChooseKey(int chooseKey) {
        this.chooseKey = chooseKey;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }

    public int getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(int mainCategory) {
        this.mainCategory = mainCategory;
    }

    public int getMainKey() {
        return mainKey;
    }

    public void setMainKey(int mainKey) {
        this.mainKey = mainKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerOne() {
        return pricePerOne;
    }

    public void setPricePerOne(double pricePerOne) {
        this.pricePerOne = pricePerOne;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public int getSubKey() {
        return subKey;
    }

    public void setSubKey(int subKey) {
        this.subKey = subKey;
    }

    public long getTotalTradeCount() {
        return totalTradeCount;
    }

    public void setTotalTradeCount(long totalTradeCount) {
        this.totalTradeCount = totalTradeCount;
    }
}
