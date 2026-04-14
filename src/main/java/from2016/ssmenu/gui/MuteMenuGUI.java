package from2016.ssmenu.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class MuteMenuGUI {

    public static void open(Player staff, Player target) {
        Inventory gui = Bukkit.createInventory(null, 54, "§cВыберите срок мута для " + target.getName());

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(target);
        headMeta.setDisplayName("§6§l" + target.getName());
        headMeta.setLore(Arrays.asList(
                "§7Выберите срок мута",
                "§7для этого игрока"
        ));
        head.setItemMeta(headMeta);
        gui.setItem(40, head);

        ItemStack mute15min = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta15 = mute15min.getItemMeta();
        meta15.setDisplayName("§6§l15 МИНУТ");
        meta15.setLore(Arrays.asList(
                "§7Срок: §615 минут",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        mute15min.setItemMeta(meta15);
        gui.setItem(28, mute15min);

        ItemStack mute20min = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta20 = mute20min.getItemMeta();
        meta20.setDisplayName("§6§l20 МИНУТ");
        meta20.setLore(Arrays.asList(
                "§7Срок: §620 минут",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        mute20min.setItemMeta(meta20);
        gui.setItem(29, mute20min);

        ItemStack mute30min = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta30 = mute30min.getItemMeta();
        meta30.setDisplayName("§6§l30 МИНУТ");
        meta30.setLore(Arrays.asList(
                "§7Срок: §630 минут",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        mute30min.setItemMeta(meta30);
        gui.setItem(30, mute30min);

        ItemStack mute1hour = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta1h = mute1hour.getItemMeta();
        meta1h.setDisplayName("§6§l1 ЧАС");
        meta1h.setLore(Arrays.asList(
                "§7Срок: §61 час",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        mute1hour.setItemMeta(meta1h);
        gui.setItem(31, mute1hour);

        ItemStack mute2hours = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta2h = mute2hours.getItemMeta();
        meta2h.setDisplayName("§6§l2 ЧАСА");
        meta2h.setLore(Arrays.asList(
                "§7Срок: §62 часа",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        mute2hours.setItemMeta(meta2h);
        gui.setItem(32, mute2hours);

        ItemStack mute4hours = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta4h = mute4hours.getItemMeta();
        meta4h.setDisplayName("§6§l4 ЧАСА");
        meta4h.setLore(Arrays.asList(
                "§7Срок: §64 часа",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        mute4hours.setItemMeta(meta4h);
        gui.setItem(33, mute4hours);

        ItemStack mute6hours = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta6h = mute6hours.getItemMeta();
        meta6h.setDisplayName("§6§l6 ЧАСОВ");
        meta6h.setLore(Arrays.asList(
                "§7Срок: §66 часов",
                "",
                "§eНажмите, чтобы продолжить"
        ));
        mute6hours.setItemMeta(meta6h);
        gui.setItem(34, mute6hours);

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