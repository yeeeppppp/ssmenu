package from2016.ssmenu.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class BanMenuGUI {

    public static void open(Player staff, Player target) {
        Inventory gui = Bukkit.createInventory(null, 54, "§cВыберите срок бана");

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(target);
        headMeta.setDisplayName("§6§l" + target.getName());
        headMeta.setLore(Arrays.asList(
                "§7Выберите срок бана",
                "§7для этого игрока"
        ));
        head.setItemMeta(headMeta);
        gui.setItem(40, head);

        ItemStack ban7Days = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta7 = ban7Days.getItemMeta();
        meta7.setDisplayName("§c§l7 ДНЕЙ");
        meta7.setLore(Arrays.asList(
                "§7Срок: §c7 дней",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        ban7Days.setItemMeta(meta7);
        gui.setItem(29, ban7Days);

        ItemStack ban14Days = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta14 = ban14Days.getItemMeta();
        meta14.setDisplayName("§c§l14 ДНЕЙ");
        meta14.setLore(Arrays.asList(
                "§7Срок: §c14 дней",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        ban14Days.setItemMeta(meta14);
        gui.setItem(31, ban14Days);

        ItemStack ban30Days = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta30 = ban30Days.getItemMeta();
        meta30.setDisplayName("§c§l30 ДНЕЙ");
        meta30.setLore(Arrays.asList(
                "§7Срок: §c30 дней",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        ban30Days.setItemMeta(meta30);
        gui.setItem(33, ban30Days);

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