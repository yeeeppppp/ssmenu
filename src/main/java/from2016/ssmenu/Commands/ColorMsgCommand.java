package from2016.ssmenu.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ColorMsgCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (strings.length < 1) return false;

        String color = "";

        if (strings[0].equals("red")){
            color = "&4";
        } else if (strings[0].equals("blue")) {
            color = "&1";
        } else if (strings[0].equals("green")) {
            color = "&2";
        } else color = color;

        for (int i = 1; i < strings.length; i ++) {
            color += strings[i] + " ";
        }

        player.sendMessage(color);

        return false;
    }
}
