package module.gardening.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.gardening.model.Crop;
import module.gardening.model.CropAnalysis;
import module.gardening.model.Fence;
import module.gardening.model.HarvestSettings;
import module.marketapi.MarketDAO;

import java.util.List;
import java.util.stream.Collectors;

public class GardenProfitabilityAlgorithm implements Algorithm<List<CropAnalysis>> {

    private static final MarketDAO MARKET_DAO = MarketDAO.getInstance();
    private final double marketTax;
    private final double playerBonus;
    private final Fence playerFence;
    private final int amountOfFences;

    public GardenProfitabilityAlgorithm(double marketTax, double playerBonus, Fence playerFence, int amountOfFences) {
        this.marketTax = marketTax;
        this.playerBonus = playerBonus;
        this.playerFence = playerFence;
        this.amountOfFences = amountOfFences;
    }


    @Override
    public List<CropAnalysis> run() throws AlgorithmException {
        final double gridsAvailable = playerFence.getGrids() * amountOfFences;

        return Crop.getCrops().stream().map(crop -> {
            CropAnalysis cropAnalysis = new CropAnalysis();
            cropAnalysis.setCrop(crop);
            cropAnalysis.setSeedAvailability((int) MarketDAO.getInstance().getMarketAvailability(crop.getSeedId()));

            //get the cost of the seed
            double individualSeedCost = MARKET_DAO.getMarketValue(crop.getSeedId());
            double totalSeedCost = (gridsAvailable / crop.getGrids()) * individualSeedCost;

            //get the value of the crop
            double individualCropValue = MARKET_DAO.getMarketValue(crop.getCropId());
            double grossValue = individualCropValue * HarvestSettings.cropsPerHarvest;

            double netValue = grossValue - (grossValue * marketTax);
            netValue = netValue + (netValue * playerBonus);
            cropAnalysis.setValuePerHarvest(netValue);

            return cropAnalysis;
        }).collect(Collectors.toList());
    }
}
