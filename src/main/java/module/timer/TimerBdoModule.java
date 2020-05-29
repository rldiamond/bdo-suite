package module.timer;

import module.common.BdoModule;
import module.common.ModuleTool;
import module.timer.display.BossTimerToolView;

public class TimerBdoModule extends BdoModule {

    @Override
    protected void initialize() {

        ModuleTool bossTimerTool = new ModuleTool();
        bossTimerTool.setToolView(new BossTimerToolView());
        bossTimerTool.setTitle("Boss Timers");
        bossTimerTool.setDescription("Enable timers for various boss spawns.");
        bossTimerTool.setIconId("bossTool");
        getModuleToolbar().addTools(bossTimerTool);

    }
}
