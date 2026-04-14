package from2016.ssmenu.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishUtils {

    public static void banPlayer(Player target, Player staff, String reason, String duration) {
        String plainReason = reason.replaceAll("§[0-9a-fk-or]", "");
        String command = "tempban " + target.getName() + " " + duration + " " + plainReason;

        Bukkit.getConsoleSender().sendMessage("§6[CheckMenu] Команда: " + command);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void mutePlayer(Player target, Player staff, String reason, String duration) {
        String plainReason = reason.replaceAll("§[0-9a-fk-or]", "");
        String command = "tempmute " + target.getName() + " " + duration + " " + plainReason;

        Bukkit.getConsoleSender().sendMessage("§6[CheckMenu] Команда: " + command);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void warnPlayer(Player target, Player staff, String reason, String duration) {
        String plainReason = reason.replaceAll("§[0-9a-fk-or]", "");
        String command = "warn " + target.getName() + " " + duration + " " + plainReason;

        Bukkit.getConsoleSender().sendMessage("§6[CheckMenu] Команда: " + command);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}