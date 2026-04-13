package from2016.ssmenu;

import from2016.ssmenu.Commands.CheckMenuCommand;
import from2016.ssmenu.listeners.GUIListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Ssmenu extends JavaPlugin {

    private static Ssmenu instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("checkmenu").setExecutor(new CheckMenuCommand());
        getServer().getPluginManager().registerEvents(new GUIListener(), this);

    }

    @Override
    public void onDisable() {

    }

    public static Ssmenu getInstance() {
        return instance;
    }
}