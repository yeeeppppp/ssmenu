package from2016.ssmenu.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class MainMenuGUI {

    public static void open(Player staff, Player target) {
        Inventory gui = Bukkit.createInventory(null, 54, "§cВыберите действие для " + target.getName());

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(target);
        headMeta.setDisplayName("§6§l" + target.getName());
        headMeta.setLore(Arrays.asList(
                "§7Выберите действие",
                "§7для этого игрока"
        ));
        head.setItemMeta(headMeta);
        gui.setItem(22, head);

        ItemStack banItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta banMeta = banItem.getItemMeta();
        banMeta.setDisplayName("§c§lБАН");
        banMeta.setLore(Arrays.asList(
                "§7Забанить игрока",
                "",
                "§eНажмите, чтобы выбрать срок"
        ));
        banItem.setItemMeta(banMeta);
        gui.setItem(20, banItem);

        ItemStack muteItem = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta muteMeta = muteItem.getItemMeta();
        muteMeta.setDisplayName("§6§lМУТ");
        muteMeta.setLore(Arrays.asList(
                "§7Замутить игрока",
                "",
                "§eНажмите, чтобы выбрать срок"
        ));
        muteItem.setItemMeta(muteMeta);
        gui.setItem(24, muteItem);

        ItemStack warnItem = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta warnMeta = warnItem.getItemMeta();
        warnMeta.setDisplayName("§e§lВАРН");
        warnMeta.setLore(Arrays.asList(
                "§7Выдать предупреждение",
                "",
                "§eНажмите, чтобы выбрать срок"
        ));
        warnItem.setItemMeta(warnMeta);
        gui.setItem(31, warnItem);

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

        for (int row = 2; row <= 4; row++) {
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