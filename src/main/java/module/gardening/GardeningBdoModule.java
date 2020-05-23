package module.gardening;

import module.common.BdoModule;
import module.common.ModulePane;

/**
 * Module for gardening. Determines the most profitable crop to garden given seed/hypha availability.
 */
public class GardeningBdoModule extends BdoModule {

    /**
     * Constructor.
     */
    public GardeningBdoModule() {
        super("Gardening", "Determine the most profitable crops to garden.");
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {

    }

    /**
     * @inheritDoc
     */
    @Override
    public ModulePane getModulePane() {
        return null;
    }
}
