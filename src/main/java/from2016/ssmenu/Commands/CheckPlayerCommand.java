package from2016.ssmenu.Commands;

import from2016.ssmenu.Ssmenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckPlayerCommand implements CommandExecutor, TabCompleter {

    private static final List<String> SUB_COMMANDS = Arrays.asList("start", "stop", "extend", "complete", "freeze", "unfreeze", "reset");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду может использовать только игрок!");
            return true;
        }

        Player moderator = (Player) sender;

        if (args.length < 1) {
            sendHelp(moderator);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "start":
                if (args.length < 2) {
                    moderator.sendMessage("§cИспользование: /checkplayer start <ник>");
                    return true;
                }
                Player startTarget = Bukkit.getPlayer(args[1]);
                if (startTarget == null) {
                    moderator.sendMessage("§cИгрок " + args[1] + " не найден или не в сети!");
                    return true;
                }
                Ssmenu.getInstance().getCheckPlayerManager().startCheck(moderator, startTarget);
                break;

            case "stop":
                if (args.length < 2) {
                    moderator.sendMessage("§cИспользование: /checkplayer stop <ник>");
                    return true;
                }
                Player stopTarget = Bukkit.getPlayer(args[1]);
                if (stopTarget == null) {
                    moderator.sendMessage("§cИгрок " + args[1] + " не найден или не в сети!");
                    return true;
                }
                Ssmenu.getInstance().getCheckPlayerManager().stopCheck(stopTarget.getUniqueId(), "Принудительное завершение");
                break;

            case "extend":
                if (args.length < 3) {
                    moderator.sendMessage("§cИспользование: /checkplayer extend <ник> <минуты>");
                    return true;
                }
                Player extendTarget = Bukkit.getPlayer(args[1]);
                if (extendTarget == null) {
                    moderator.sendMessage("§cИгрок " + args[1] + " не найден или не в сети!");
                    return true;
                }
                int minutes;
                try {
                    minutes = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    moderator.sendMessage("§cМинуты должны быть числом!");
                    return true;
                }
                if (minutes <= 0) {
                    moderator.sendMessage("§cМинуты должны быть больше 0!");
                    return true;
                }
                Ssmenu.getInstance().getCheckPlayerManager().extendCheck(extendTarget.getUniqueId(), minutes);
                break;

            case "complete":
                if (args.length < 2) {
                    moderator.sendMessage("§cИспользование: /checkplayer complete <ник>");
                    return true;
                }
                Player completeTarget = Bukkit.getPlayer(args[1]);
                if (completeTarget == null) {
                    moderator.sendMessage("§cИгрок " + args[1] + " не найден или не в сети!");
                    return true;
                }
                Ssmenu.getInstance().getCheckPlayerManager().completeCheck(completeTarget.getUniqueId());
                break;

            case "freeze":
                if (args.length < 2) {
                    moderator.sendMessage("§cИспользование: /checkplayer freeze <ник>");
                    return true;
                }
                Player freezeTarget = Bukkit.getPlayer(args[1]);
                if (freezeTarget == null) {
                    moderator.sendMessage("§cИгрок " + args[1] + " не найден или не в сети!");
                    return true;
                }
                Ssmenu.getInstance().getCheckPlayerManager().freezeCheck(freezeTarget.getUniqueId());
                break;

            case "unfreeze":
                if (args.length < 2) {
                    moderator.sendMessage("§cИспользование: /checkplayer unfreeze <ник>");
                    return true;
                }
                Player unfreezeTarget = Bukkit.getPlayer(args[1]);
                if (unfreezeTarget == null) {
                    moderator.sendMessage("§cИгрок " + args[1] + " не найден или не в сети!");
                    return true;
                }
                Ssmenu.getInstance().getCheckPlayerManager().unfreezeCheck(unfreezeTarget.getUniqueId());
                break;

            case "reset":
                if (args.length < 2) {
                    moderator.sendMessage("§cИспользование: /checkplayer reset <ник>");
                    return true;
                }
                Player resetTarget = Bukkit.getPlayer(args[1]);
                if (resetTarget == null) {
                    moderator.sendMessage("§cИгрок " + args[1] + " не найден!");
                    return true;
                }
                resetTarget.setWalkSpeed(0.2f);
                resetTarget.setFlySpeed(0.1f);
                resetTarget.removePotionEffect(PotionEffectType.BLINDNESS);
                moderator.sendMessage("§aЭффекты с игрока " + resetTarget.getName() + " сняты!");
                break;

            default:
                sendHelp(moderator);
                break;
        }
        return true;
    }

    private void sendHelp(Player moderator) {
        moderator.sendMessage("§6§l=== /checkplayer HELP ===");
        moderator.sendMessage("§7/checkplayer start <ник> §8- §fНачать проверку");
        moderator.sendMessage("§7/checkplayer stop <ник> §8- §fПринудительно завершить проверку");
        moderator.sendMessage("§7/checkplayer extend <ник> <минуты> §8- §fПродлить проверку");
        moderator.sendMessage("§7/checkplayer complete <ник> §8- §fУспешно завершить проверку");
        moderator.sendMessage("§7/checkplayer freeze <ник> §8- §fОстановить таймер проверки");
        moderator.sendMessage("§7/checkplayer unfreeze <ник> §8- §fВозобновить таймер проверки");
        moderator.sendMessage("§7/checkplayer reset <ник> §8- §fСнять эффекты с игрока");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return SUB_COMMANDS.stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("stop") ||
                args[0].equalsIgnoreCase("extend") || args[0].equalsIgnoreCase("complete") ||
                args[0].equalsIgnoreCase("freeze") || args[0].equalsIgnoreCase("unfreeze") ||
                args[0].equalsIgnoreCase("reset"))) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("extend")) {
            List<String> suggestions = new ArrayList<>();
            suggestions.add("1");
            suggestions.add("2");
            suggestions.add("3");
            suggestions.add("5");
            suggestions.add("10");
            return suggestions.stream()
                    .filter(s -> s.startsWith(args[2]))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}