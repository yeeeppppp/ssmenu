package from2016.ssmenu.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class WarnMenuGUI {

    public static void open(Player staff, Player target) {
        Inventory gui = Bukkit.createInventory(null, 54, "§cВыберите срок варна для " + target.getName());

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(target);
        headMeta.setDisplayName("§6§l" + target.getName());
        headMeta.setLore(Arrays.asList(
                "§7Выберите срок варна",
                "§7для этого игрока"
        ));
        head.setItemMeta(headMeta);
        gui.setItem(40, head);

        ItemStack warn1Day = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta meta1 = warn1Day.getItemMeta();
        meta1.setDisplayName("§e§l1 ДЕНЬ");
        meta1.setLore(Arrays.asList(
                "§7Срок: §e1 день",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        warn1Day.setItemMeta(meta1);
        gui.setItem(29, warn1Day);

        ItemStack warn3Days = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta meta3 = warn3Days.getItemMeta();
        meta3.setDisplayName("§e§l3 ДНЯ");
        meta3.setLore(Arrays.asList(
                "§7Срок: §e3 дня",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        warn3Days.setItemMeta(meta3);
        gui.setItem(33, warn3Days);

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
            if (gui.getItem(i) == null) {
                if (i < 9) {
                    gui.setItem(i, blackGlass);
                } else {
                    gui.setItem(i, grayGlass);
                }
            }
        }

        for (int row = 2; row <= 5; row++) {
            int leftSlot = row * 9;
            int rightSlot = row * 9 + 8;
            if (gui.getItem(leftSlot) == null) {
                gui.setItem(leftSlot, grayGlass);
            }
            if (gui.getItem(rightSlot) == null) {
                gui.setItem(rightSlot, grayGlass);
            }
        }

        for (int i = 45; i < 54; i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, blackGlass);
            }
        }

        for (int i = 0; i < 54; i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, lightGrayGlass);
            }
        }

        staff.openInventory(gui);
    }
}