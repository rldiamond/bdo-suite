package module.barter.task;

import common.task.BackgroundTask;
import module.marketapi.algorithms.CrowCoinValueAlgorithm;

public class CrowCoinScheduledTask extends BackgroundTask {



    @Override
    public void doTask() {
        CrowCoinValueAlgorithm algorithm = new CrowCoinValueAlgorithm();
        try {
            System.out.println(algorithm.run());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
