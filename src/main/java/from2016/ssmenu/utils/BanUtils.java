package from2016.ssmenu.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BanUtils {

    public static void banPlayer(Player target, Player staff, String reason, long days) {
        String plainReason = reason.replaceAll("§[0-9a-fk-or]", "");
        String time = days + "d";
        String command = "tempbanip " + target.getName() + " " + time + " " + plainReason;

        Bukkit.getConsoleSender().sendMessage("§6[CheckMenu] §f" + staff.getName() + " §7применил бан к игроку §f" + target.getName());
        Bukkit.getConsoleSender().sendMessage("§6[CheckMenu] §7Срок: §f" + days + " дней §7| Причина: §f" + plainReason);
        Bukkit.getConsoleSender().sendMessage("§6[CheckMenu] §7Команда: §f" + command);

        staff.sendMessage("§6§l=== РЕЗУЛЬТАТ БАНА ===");
        staff.sendMessage("§7Игрок: §f" + target.getName());
        staff.sendMessage("§7Срок: §f" + days + " дней");
        staff.sendMessage("§7Причина: " + reason);
        staff.sendMessage("§7Статус: §eОжидание выполнения команды...");

        boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

        if (success) {
            staff.sendMessage("§7Статус: §a✓ Команда отправлена!");
            staff.sendMessage("§aИгрок " + target.getName() + " должен быть забанен.");
            Bukkit.getConsoleSender().sendMessage("§6[CheckMenu] §aКоманда успешно выполнена!");
        } else {
            staff.sendMessage("§7Статус: §c✗ Ошибка! Команда не выполнена.");
            staff.sendMessage("§cПроверьте, установлен ли плагин с командой /tempbanip!");
            Bukkit.getConsoleSender().sendMessage("§6[CheckMenu] §cОшибка: Команда не выполнена!");
        }
    }
}