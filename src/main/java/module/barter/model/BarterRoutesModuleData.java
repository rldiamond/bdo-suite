package module.barter.model;

import common.application.ModuleRegistration;
import common.json.ModuleData;

import java.util.List;

public class BarterRoutesModuleData implements ModuleData {

    private List<Barter> barters;

    public List<Barter> getBarters() {
        return barters;
    }

    public void setBarters(List<Barter> barters) {
        this.barters = barters;
    }

    @Override
    public ModuleRegistration getModule() {
        return ModuleRegistration.BARTER;
    }

    @Override
    public String fileName() {
        return "routes";
    }
}
