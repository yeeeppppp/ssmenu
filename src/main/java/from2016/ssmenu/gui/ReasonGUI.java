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

    public static final String TYPE_BAN = "ban";
    public static final String TYPE_MUTE = "mute";
    public static final String TYPE_WARN = "warn";

    private static final Map<Integer, String> BAN_REASONS = new HashMap<>();
    private static final Map<Integer, String> MUTE_REASONS = new HashMap<>();
    private static final Map<Integer, String> WARN_REASONS = new HashMap<>();

    static {
        BAN_REASONS.put(29, "§cЧиты");
        BAN_REASONS.put(31, "§cРеклама");
        BAN_REASONS.put(33, "§cОбход блокировки");

        MUTE_REASONS.put(28, "§6Оскорбление");
        MUTE_REASONS.put(29, "§6Оскорбление родни");
        MUTE_REASONS.put(30, "§6Оскорбление администрации");
        MUTE_REASONS.put(32, "§6Флуд");
        MUTE_REASONS.put(33, "§6Спам");
        MUTE_REASONS.put(34, "§6Упоминание родни");

        WARN_REASONS.put(28, "§eОскорбление");
        WARN_REASONS.put(29, "§eФлейм");
        WARN_REASONS.put(30, "§eСпам");
        WARN_REASONS.put(32, "§eФлуд");
        WARN_REASONS.put(33, "§eКапс");
        WARN_REASONS.put(34, "§eУпоминание родни");
    }

    private static final Map<UUID, TempPunishData> tempData = new HashMap<>();

    public static class TempPunishData {
        public Player target;
        public String duration;
        public String type;
        public TempPunishData(Player target, String duration, String type) {
            this.target = target;
            this.duration = duration;
            this.type = type;
        }
    }

    public static void open(Player staff, Player target, String duration, String type) {
        tempData.put(staff.getUniqueId(), new TempPunishData(target, duration, type));

        String title = "";
        Map<Integer, String> reasons = new HashMap<>();

        switch (type) {
            case TYPE_BAN:
                title = "§cВыберите причину бана (" + duration + ")";
                reasons = BAN_REASONS;
                break;
            case TYPE_MUTE:
                title = "§6Выберите причину мута (" + duration + ")";
                reasons = MUTE_REASONS;
                break;
            case TYPE_WARN:
                title = "§eВыберите причину варна (" + duration + ")";
                reasons = WARN_REASONS;
                break;
        }

        Inventory gui = Bukkit.createInventory(null, 54, title);

        for (Map.Entry<Integer, String> entry : reasons.entrySet()) {
            int slot = entry.getKey();
            String reason = entry.getValue();

            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(reason);
            meta.setLore(Arrays.asList(
                    "§7Срок: " + duration,
                    "",
                    "§eНажмите, чтобы применить"
            ));
            item.setItemMeta(meta);
            gui.setItem(slot, item);
        }

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("§6§l" + target.getName());
        headMeta.setLore(Arrays.asList(
                "§7Выберите причину",
                "§7для этого игрока"
        ));
        head.setItemMeta(headMeta);
        gui.setItem(40, head);

        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("§c§lОТМЕНА");
        cancelMeta.setLore(Arrays.asList("§7Нажмите, чтобы отменить"));
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

        for (int row = 2; row <= 5; row++) {
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

    public static TempPunishData getAndRemoveData(Player staff) {
        return tempData.remove(staff.getUniqueId());
    }
}