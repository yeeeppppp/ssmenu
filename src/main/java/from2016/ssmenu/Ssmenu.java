package from2016.ssmenu;

import from2016.ssmenu.Commands.CheckMenuCommand;
import from2016.ssmenu.Commands.CheckPlayerCommand;
import from2016.ssmenu.listeners.CheckPlayerListener;
import from2016.ssmenu.listeners.GUIListener;
import from2016.ssmenu.listeners.PrivateMessageListener;
import from2016.ssmenu.managers.CheckPlayerManager;
import from2016.ssmenu.managers.PrivateMessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Ssmenu extends JavaPlugin {

    private static Ssmenu instance;
    private CheckPlayerManager checkPlayerManager;
    private PrivateMessageManager privateMessageManager;

    @Override
    public void onEnable() {
        instance = this;

        privateMessageManager = new PrivateMessageManager();
        checkPlayerManager = new CheckPlayerManager(privateMessageManager);

        getCommand("checkmenu").setExecutor(new CheckMenuCommand());
        getCommand("checkplayer").setExecutor(new CheckPlayerCommand());

        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new CheckPlayerListener(checkPlayerManager), this);
        getServer().getPluginManager().registerEvents(new PrivateMessageListener(checkPlayerManager, privateMessageManager), this);

        CheckPlayerCommand checkPlayerCommand = new CheckPlayerCommand();
        getCommand("checkplayer").setExecutor(checkPlayerCommand);
        getCommand("checkplayer").setTabCompleter(checkPlayerCommand);

        getLogger().info("§aSsmenu плагин включен!");
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (checkPlayerManager != null && checkPlayerManager.isOnCheck(player)) {
                player.setWalkSpeed(0.2f);
                player.setFlySpeed(0.1f);
                player.removePotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS);
                player.sendMessage("§cСервер остановлен, проверка прервана!");
            }
        }

        if (checkPlayerManager != null) {
            checkPlayerManager.clearAllChecks();
        }

        getLogger().info("§cSsmenu плагин выключен!");
        instance = null;
    }

    public static Ssmenu getInstance() {
        return instance;
    }

    public CheckPlayerManager getCheckPlayerManager() {
        return checkPlayerManager;
    }

    public PrivateMessageManager getPrivateMessageManager() {
        return privateMessageManager;
    }
}