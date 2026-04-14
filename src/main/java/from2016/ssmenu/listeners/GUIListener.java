package from2016.ssmenu.listeners;

import from2016.ssmenu.gui.*;
import from2016.ssmenu.utils.PunishUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIListener implements Listener {

    private static final Map<UUID, TempData> tempData = new HashMap<>();

    private static final int[] MAIN_MENU_SLOTS = {20, 24, 31};
    private static final int[] BAN_SLOTS = {29, 31, 33};
    private static final int[] MUTE_SLOTS = {28, 29, 30, 31, 32, 33, 34};
    private static final int[] WARN_SLOTS = {29, 33};
    private static final int[] REASON_SLOTS = {28, 29, 30, 31, 32, 33, 34, 49};

    static class TempData {
        Player target;
        String duration;
        String punishType;
        TempData(Player target, String duration, String punishType) {
            this.target = target;
            this.duration = duration;
            this.punishType = punishType;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player staff = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        event.setCancelled(true);

        if (title.contains("§cВыберите действие для")) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            int slot = event.getRawSlot();

            boolean isAllowed = false;
            for (int s : MAIN_MENU_SLOTS) {
                if (slot == s) isAllowed = true;
            }
            if (!isAllowed) return;

            String displayName = clicked.getItemMeta().getDisplayName();

            Player target = null;
            if (event.getInventory().getItem(22) != null) {
                String targetName = event.getInventory().getItem(22).getItemMeta().getDisplayName();
                targetName = targetName.replaceAll("§[0-9a-fk-or]", "");
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.getName().equalsIgnoreCase(targetName)) {
                        target = online;
                        break;
                    }
                }
            }

            if (target == null) {
                staff.sendMessage("§cИгрок не найден или вышел из сети!");
                staff.closeInventory();
                return;
            }

            if (slot == 20 && displayName.contains("БАН")) {
                BanMenuGUI.open(staff, target);
            } else if (slot == 24 && displayName.contains("МУТ")) {
                MuteMenuGUI.open(staff, target);
            } else if (slot == 31 && displayName.contains("ВАРН")) {
                WarnMenuGUI.open(staff, target);
            }
        }

        else if (title.equals("§cВыберите срок бана")) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            int slot = event.getRawSlot();

            boolean isAllowed = false;
            for (int s : BAN_SLOTS) {
                if (slot == s) isAllowed = true;
            }
            if (!isAllowed) return;

            String duration = null;
            if (slot == 29) {
                duration = "7d";
            } else if (slot == 31) {
                duration = "14d";
            } else if (slot == 33) {
                duration = "30d";
            }

            Bukkit.getConsoleSender().sendMessage("§e[DEBUG] Бан - слот: " + slot + ", duration: " + duration);

            Player target = null;
            if (event.getInventory().getItem(40) != null) {
                String targetName = event.getInventory().getItem(40).getItemMeta().getDisplayName();
                targetName = targetName.replaceAll("§[0-9a-fk-or]", "");
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.getName().equalsIgnoreCase(targetName)) {
                        target = online;
                        break;
                    }
                }
            }

            if (target == null) {
                staff.sendMessage("§cИгрок не найден или вышел из сети!");
                staff.closeInventory();
                return;
            }

            tempData.put(staff.getUniqueId(), new TempData(target, duration, ReasonGUI.TYPE_BAN));
            staff.closeInventory();
            ReasonGUI.open(staff, target, duration, ReasonGUI.TYPE_BAN);
        }

        else if (title.contains("§cВыберите срок мута для")) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            int slot = event.getRawSlot();

            boolean isAllowed = false;
            for (int s : MUTE_SLOTS) {
                if (slot == s) isAllowed = true;
            }
            if (!isAllowed) return;

            String duration = null;
            if (slot == 28) duration = "15m";
            else if (slot == 29) duration = "20m";
            else if (slot == 30) duration = "30m";
            else if (slot == 31) duration = "1h";
            else if (slot == 32) duration = "2h";
            else if (slot == 33) duration = "4h";
            else if (slot == 34) duration = "6h";

            Player target = null;
            if (event.getInventory().getItem(40) != null) {
                String targetName = event.getInventory().getItem(40).getItemMeta().getDisplayName();
                targetName = targetName.replaceAll("§[0-9a-fk-or]", "");
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.getName().equalsIgnoreCase(targetName)) {
                        target = online;
                        break;
                    }
                }
            }

            if (target == null) {
                staff.sendMessage("§cИгрок не найден или вышел из сети!");
                staff.closeInventory();
                return;
            }

            tempData.put(staff.getUniqueId(), new TempData(target, duration, ReasonGUI.TYPE_MUTE));
            staff.closeInventory();
            ReasonGUI.open(staff, target, duration, ReasonGUI.TYPE_MUTE);
        }

        else if (title.contains("§cВыберите срок варна для")) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            int slot = event.getRawSlot();

            boolean isAllowed = false;
            for (int s : WARN_SLOTS) {
                if (slot == s) isAllowed = true;
            }
            if (!isAllowed) return;

            String duration = null;
            if (slot == 29) duration = "1d";
            else if (slot == 33) duration = "3d";

            Player target = null;
            if (event.getInventory().getItem(40) != null) {
                String targetName = event.getInventory().getItem(40).getItemMeta().getDisplayName();
                targetName = targetName.replaceAll("§[0-9a-fk-or]", "");
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.getName().equalsIgnoreCase(targetName)) {
                        target = online;
                        break;
                    }
                }
            }

            if (target == null) {
                staff.sendMessage("§cИгрок не найден или вышел из сети!");
                staff.closeInventory();
                return;
            }

            tempData.put(staff.getUniqueId(), new TempData(target, duration, ReasonGUI.TYPE_WARN));
            staff.closeInventory();
            ReasonGUI.open(staff, target, duration, ReasonGUI.TYPE_WARN);
        }

        else if (title.contains("Выберите причину")) {
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            int slot = event.getRawSlot();

            boolean isAllowed = false;
            for (int s : REASON_SLOTS) {
                if (slot == s) isAllowed = true;
            }
            if (!isAllowed) return;

            if (slot == 49) {
                tempData.remove(staff.getUniqueId());
                staff.closeInventory();
                staff.sendMessage("§cОперация отменена.");
                return;
            }

            String reason = clicked.getItemMeta().getDisplayName();

            ReasonGUI.TempPunishData data = ReasonGUI.getAndRemoveData(staff);
            if (data == null) {
                staff.sendMessage("§cПроизошла ошибка! Попробуйте снова.");
                staff.closeInventory();
                return;
            }

            Player target = data.target;
            String duration = data.duration;
            String type = data.type;

            if (target == null || !target.isOnline()) {
                staff.sendMessage("§cИгрок вышел из сети, наказание не выполнено!");
                staff.closeInventory();
                return;
            }

            staff.closeInventory();

            switch (type) {
                case ReasonGUI.TYPE_BAN:
                    PunishUtils.banPlayer(target, staff, reason, duration);
                    break;
                case ReasonGUI.TYPE_MUTE:
                    PunishUtils.mutePlayer(target, staff, reason, duration);
                    break;
                case ReasonGUI.TYPE_WARN:
                    PunishUtils.warnPlayer(target, staff, reason, duration);
                    break;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player staff = (Player) event.getPlayer();
        String title = event.getView().getTitle();

        if (title.contains("Выберите причину")) {
            Bukkit.getScheduler().runTaskLater(from2016.ssmenu.Ssmenu.getInstance(), () -> {
                if (!staff.getOpenInventory().getTitle().contains("Выберите причину")) {
                    tempData.remove(staff.getUniqueId());
                }
            }, 2L);
        }
    }
}