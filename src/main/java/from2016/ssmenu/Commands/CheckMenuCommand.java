package from2016.ssmenu.Commands;

import from2016.ssmenu.gui.MainMenuGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду может использовать только игрок!");
            return true;
        }

        Player staff = (Player) sender;

        if (args.length != 1) {
            staff.sendMessage("§cИспользование: /checkmenu <ник игрока>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            staff.sendMessage("§cИгрок " + args[0] + " не найден или не в сети!");
            return true;
        }

        MainMenuGUI.open(staff, target);

        return true;
    }
}