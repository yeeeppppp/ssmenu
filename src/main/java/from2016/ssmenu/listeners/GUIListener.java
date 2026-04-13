package from2016.ssmenu.listeners;

import from2016.ssmenu.gui.ReasonGUI;
import from2016.ssmenu.utils.BanUtils;
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

    private static final Map<UUID, TempBanData> tempData = new HashMap<>();
    private static final int[] REASON_SLOTS = {20, 22, 24, 49};

    static class TempBanData {
        Player target;
        long days;
        TempBanData(Player target, long days) {
            this.target = target;
            this.days = days;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player staff = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        if (title.equals("§cВыбор срока бана")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            int slot = event.getRawSlot();

            if (slot != 21 && slot != 23 && slot != 31) return;

            String displayName = clicked.getItemMeta().getDisplayName();

            long days = 0;
            if (displayName.contains("7")) {
                days = 7;
            } else if (displayName.contains("14")) {
                days = 14;
            } else if (displayName.contains("30")) {
                days = 30;
            } else {
                return;
            }

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

            tempData.put(staff.getUniqueId(), new TempBanData(target, days));
            staff.closeInventory();
            ReasonGUI.open(staff, target, days);
        }

        else if (title.contains("§cВыберите причину бана")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            int slot = event.getRawSlot();

            boolean isAllowedSlot = false;
            for (int allowedSlot : REASON_SLOTS) {
                if (slot == allowedSlot) {
                    isAllowedSlot = true;
                    break;
                }
            }

            if (!isAllowedSlot) return;

            if (slot == 49) {
                tempData.remove(staff.getUniqueId());
                staff.closeInventory();
                staff.sendMessage("§cОперация отменена.");
                return;
            }

            String reason = clicked.getItemMeta().getDisplayName();

            TempBanData data = tempData.get(staff.getUniqueId());

            if (data == null) {
                staff.sendMessage("§cПроизошла ошибка! Попробуйте снова.");
                staff.closeInventory();
                return;
            }

            Player target = data.target;
            long days = data.days;

            if (target == null || !target.isOnline()) {
                staff.sendMessage("§cИгрок вышел из сети, бан не выполнен!");
                tempData.remove(staff.getUniqueId());
                staff.closeInventory();
                return;
            }

            tempData.remove(staff.getUniqueId());
            staff.closeInventory();
            BanUtils.banPlayer(target, staff, reason, days);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player staff = (Player) event.getPlayer();
        String title = event.getView().getTitle();

        if (title.contains("§cВыберите причину бана")) {
            Bukkit.getScheduler().runTaskLater(from2016.ssmenu.Ssmenu.getInstance(), () -> {
                if (!staff.getOpenInventory().getTitle().contains("§cВыберите причину бана")) {
                    tempData.remove(staff.getUniqueId());
                }
            }, 2L);
        }
    }
}