package from2016.ssmenu.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReasonGUI {

    private static final Map<Integer, String> REASONS = new HashMap<>();

    static {
        REASONS.put(20, "§cЧиты / Нечестная игра");
        REASONS.put(22, "§cРеклама");
        REASONS.put(24, "§cОбход блокировки");
    }

    private static final Map<UUID, Long> tempBanDays = new HashMap<>();

    public static void open(Player staff, Player target, long days) {
        tempBanDays.put(staff.getUniqueId(), days);

        Inventory gui = Bukkit.createInventory(null, 54, "§cВыберите причину бана (" + days + " дн.)");

        for (Map.Entry<Integer, String> entry : REASONS.entrySet()) {
            int slot = entry.getKey();
            String reason = entry.getValue();

            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(reason);
            meta.setLore(Arrays.asList(
                    "§7Срок бана: §c" + days + " дней",
                    "",
                    "§eНажмите, чтобы забанить"
            ));
            item.setItemMeta(meta);
            gui.setItem(slot, item);
        }

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("§6§l" + target.getName());
        headMeta.setLore(Arrays.asList(
                "§7Выберите причину бана",
                "§7для этого игрока"
        ));
        head.setItemMeta(headMeta);
        gui.setItem(31, head);

        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("§c§lОТМЕНА");
        cancelMeta.setLore(Arrays.asList("§7Нажмите, чтобы отменить бан"));
        cancel.setItemMeta(cancelMeta);
        gui.setItem(49, cancel);

        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackMeta = blackGlass.getItemMeta();
        blackMeta.setDisplayName(" ");
        blackGlass.setItemMeta(blackMeta);

        ItemStack grayGlass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta grayMeta = grayGlass.getItemMeta();
        grayMeta.setDisplayName(" ");
        grayGlass.setItemMeta(grayMeta);

        ItemStack lightGrayGlass = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta lightGrayMeta = lightGrayGlass.getItemMeta();
        lightGrayMeta.setDisplayName(" ");
        lightGrayGlass.setItemMeta(lightGrayMeta);

        for (int i = 0; i < 18; i++) {
            if (i < 9) {
                gui.setItem(i, blackGlass);
            } else {
                gui.setItem(i, grayGlass);
            }
        }

        for (int row = 2; row <= 4; row++) {
            gui.setItem(row * 9, grayGlass);
            gui.setItem(row * 9 + 8, grayGlass);
        }

        for (int i = 45; i < 54; i++) {
            gui.setItem(i, blackGlass);
        }

        for (int i = 0; i < 54; i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, lightGrayGlass);
            }
        }

        staff.openInventory(gui);
    }

    public static Long getAndRemoveBanDays(Player staff) {
        return tempBanDays.remove(staff.getUniqueId());
    }

    public static Map<Integer, String> getReasons() {
        return REASONS;
    }
}