package from2016.ssmenu.managers;

import from2016.ssmenu.Ssmenu;
import from2016.ssmenu.tasks.CheckPlayerTimerTask;
import from2016.ssmenu.utils.PunishUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CheckPlayerManager {

    private static final Location CHECK_LOCATION = new Location(Bukkit.getWorld("world"), 306, 76, -97);
    private static final int DEFAULT_TIME_SECONDS = 5 * 60;

    private final Map<UUID, CheckPlayerSession> activeChecks = new HashMap<>();
    private final PrivateMessageManager privateMessageManager;

    public CheckPlayerManager(PrivateMessageManager privateMessageManager) {
        this.privateMessageManager = privateMessageManager;
    }

    public static class CheckPlayerSession {
        public final UUID targetUUID;
        public final UUID moderatorUUID;
        public int remainingSeconds;
        public final BossBar bossBar;
        public BukkitTask timerTask;
        public boolean active;
        public boolean frozen;
        public Location originalLocation;
        public PotionEffect originalBlindness;

        public CheckPlayerSession(UUID targetUUID, UUID moderatorUUID, int remainingSeconds, BossBar bossBar) {
            this.targetUUID = targetUUID;
            this.moderatorUUID = moderatorUUID;
            this.remainingSeconds = remainingSeconds;
            this.bossBar = bossBar;
            this.active = true;
            this.frozen = false;
        }
    }

    public boolean startCheck(Player moderator, Player target) {
        if (activeChecks.containsKey(target.getUniqueId())) {
            moderator.sendMessage("§cИгрок уже на проверке!");
            return false;
        }

        if (target.hasPotionEffect(PotionEffectType.BLINDNESS)) {
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            target.setWalkSpeed(0.2f);
            target.setFlySpeed(0.1f);
        }

        CheckPlayerSession session = new CheckPlayerSession(
                target.getUniqueId(),
                moderator.getUniqueId(),
                DEFAULT_TIME_SECONDS,
                Bukkit.createBossBar("§6§lВРЕМЯ ПРОВЕРКИ §7| §e5м 0с", BarColor.YELLOW, BarStyle.SEGMENTED_10)
        );

        session.originalLocation = target.getLocation();
        session.originalBlindness = target.getPotionEffect(PotionEffectType.BLINDNESS);

        target.teleport(CHECK_LOCATION);
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255, false, false));
        target.setWalkSpeed(0);
        target.setFlySpeed(0);

        session.bossBar.addPlayer(target);
        session.bossBar.setProgress(1.0);

        target.sendTitle("§c§lВЫ НА ПРОВЕРКЕ", "", 10, Integer.MAX_VALUE, 10);

        target.sendMessage("§6§l=== ВЫ НА ПРОВЕРКЕ ===");
        target.sendMessage("§7Пожалуйста, напишите в чат ваш Discord/Rudeskk/Anidesk.");
        target.sendMessage("§7Выход с проверки будет расценен как отказ.");
        target.sendMessage("§cНе затягивайте время!");

        moderator.sendMessage("§aПроверка игрока " + target.getName() + " начата!");
        moderator.sendMessage("§7Команды: §e/checkplayer stop " + target.getName());
        moderator.sendMessage("§7Команды: §e/checkplayer extend " + target.getName() + " <минуты>");
        moderator.sendMessage("§7Команды: §e/checkplayer complete " + target.getName());
        moderator.sendMessage("§7Команды: §e/checkplayer freeze " + target.getName());
        moderator.sendMessage("§7Команды: §e/checkplayer unfreeze " + target.getName());

        CheckPlayerTimerTask timerTask = new CheckPlayerTimerTask(this, target.getUniqueId());
        session.timerTask = Bukkit.getScheduler().runTaskTimer(Ssmenu.getInstance(), timerTask, 0L, 20L);

        activeChecks.put(target.getUniqueId(), session);
        privateMessageManager.startConversation(moderator.getUniqueId(), target.getUniqueId());

        return true;
    }

    public boolean stopCheck(UUID targetUUID, String reason) {
        CheckPlayerSession session = activeChecks.get(targetUUID);
        if (session == null) return false;

        Player target = Bukkit.getPlayer(targetUUID);
        Player moderator = Bukkit.getPlayer(session.moderatorUUID);

        session.active = false;
        if (session.timerTask != null) {
            session.timerTask.cancel();
        }
        session.bossBar.removeAll();

        if (target != null && target.isOnline()) {
            target.setWalkSpeed(0.2f);
            target.setFlySpeed(0.1f);
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            target.sendTitle("§c§lПРОВЕРКА ПРОВАЛЕНА", "§7" + reason, 10, 70, 20);
            target.sendMessage("§cВы провалили проверку! Причина: " + reason);
            PunishUtils.banPlayer(target, moderator, "§cОтказ от проверки: " + reason, "14d");
        }

        if (moderator != null && moderator.isOnline()) {
            moderator.sendMessage("§cПроверка игрока " + (target != null ? target.getName() : targetUUID.toString()) + " провалена!");
        }

        privateMessageManager.endConversation(targetUUID);
        activeChecks.remove(targetUUID);

        return true;
    }

    public boolean completeCheck(UUID targetUUID) {
        CheckPlayerSession session = activeChecks.get(targetUUID);
        if (session == null) return false;

        Player target = Bukkit.getPlayer(targetUUID);
        Player moderator = Bukkit.getPlayer(session.moderatorUUID);

        session.active = false;
        if (session.timerTask != null) {
            session.timerTask.cancel();
        }
        session.bossBar.removeAll();

        if (target != null && target.isOnline()) {
            target.teleport(session.originalLocation);
            target.setWalkSpeed(0.2f);
            target.setFlySpeed(0.1f);
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            target.sendTitle("§a§lПРОВЕРКА ПРОЙДЕНА", "§7Спасибо за сотрудничество", 10, 70, 20);
            target.sendMessage("§aПроверка успешно пройдена!");
        }

        if (moderator != null && moderator.isOnline()) {
            moderator.sendMessage("§aПроверка игрока " + (target != null ? target.getName() : targetUUID.toString()) + " успешно завершена!");
        }

        privateMessageManager.endConversation(targetUUID);
        activeChecks.remove(targetUUID);
        return true;
    }

    public boolean extendCheck(UUID targetUUID, int minutes) {
        CheckPlayerSession session = activeChecks.get(targetUUID);
        if (session == null) return false;

        int secondsToAdd = minutes * 60;
        session.remainingSeconds += secondsToAdd;

        Player target = Bukkit.getPlayer(targetUUID);
        Player moderator = Bukkit.getPlayer(session.moderatorUUID);

        double progress = Math.min(1.0, (double) session.remainingSeconds / DEFAULT_TIME_SECONDS);
        if (progress < 0) progress = 0;
        session.bossBar.setProgress(progress);

        int mins = session.remainingSeconds / 60;
        int secs = session.remainingSeconds % 60;
        session.bossBar.setTitle("§6§lВРЕМЯ ПРОВЕРКИ §7| §e" + mins + "м " + secs + "с");

        if (moderator != null && moderator.isOnline()) {
            moderator.sendMessage("§aТаймер проверки продлен на " + minutes + " минут! Осталось: " + mins + "м " + secs + "с");
        }

        if (target != null && target.isOnline()) {
            target.sendMessage("§aВремя проверки продлено на " + minutes + " минут!");
        }

        return true;
    }

    public boolean freezeCheck(UUID targetUUID) {
        CheckPlayerSession session = activeChecks.get(targetUUID);
        if (session == null) return false;

        if (session.frozen) {
            return false;
        }

        session.frozen = true;

        Player target = Bukkit.getPlayer(targetUUID);
        Player moderator = Bukkit.getPlayer(session.moderatorUUID);

        int mins = session.remainingSeconds / 60;
        int secs = session.remainingSeconds % 60;

        if (target != null && target.isOnline()) {
            target.sendTitle("§e§lВРЕМЯ ОСТАНОВЛЕНО", "", 10, Integer.MAX_VALUE, 10);
            target.sendMessage("§eВремя проверки остановлено модератором!");
            session.bossBar.setTitle("§6§lВРЕМЯ ПРОВЕРКИ §7(остановлено) §e" + mins + "м " + secs + "с");
        }

        if (moderator != null && moderator.isOnline()) {
            moderator.sendMessage("§aВремя проверки игрока " + (target != null ? target.getName() : targetUUID.toString()) + " остановлено!");
        }

        return true;
    }

    public boolean unfreezeCheck(UUID targetUUID) {
        CheckPlayerSession session = activeChecks.get(targetUUID);
        if (session == null) return false;

        if (!session.frozen) {
            return false;
        }

        session.frozen = false;

        Player target = Bukkit.getPlayer(targetUUID);
        Player moderator = Bukkit.getPlayer(session.moderatorUUID);

        int mins = session.remainingSeconds / 60;
        int secs = session.remainingSeconds % 60;

        if (target != null && target.isOnline()) {
            target.sendTitle("§c§lВЫ НА ПРОВЕРКЕ", "", 10, Integer.MAX_VALUE, 10);
            target.sendMessage("§aВремя проверки возобновлено!");
            session.bossBar.setTitle("§6§lВРЕМЯ ПРОВЕРКИ §7| §e" + mins + "м " + secs + "с");
        }

        if (moderator != null && moderator.isOnline()) {
            moderator.sendMessage("§aВремя проверки игрока " + (target != null ? target.getName() : targetUUID.toString()) + " возобновлено!");
        }

        return true;
    }

    public boolean isOnCheck(Player player) {
        return activeChecks.containsKey(player.getUniqueId());
    }

    public CheckPlayerSession getSession(UUID targetUUID) {
        return activeChecks.get(targetUUID);
    }

    public void updateTimer(UUID targetUUID, int remainingSeconds) {
        CheckPlayerSession session = activeChecks.get(targetUUID);
        if (session != null && session.active && !session.frozen) {
            session.remainingSeconds = remainingSeconds;
            double progress = (double) remainingSeconds / DEFAULT_TIME_SECONDS;
            if (progress < 0) progress = 0;
            session.bossBar.setProgress(progress);
        }
    }

    public void handleTimeOut(UUID targetUUID) {
        stopCheck(targetUUID, "Время проверки истекло");
    }

    public void clearAllChecks() {
        for (CheckPlayerSession session : activeChecks.values()) {
            if (session.timerTask != null) {
                session.timerTask.cancel();
            }
            session.bossBar.removeAll();

            Player target = Bukkit.getPlayer(session.targetUUID);
            if (target != null && target.isOnline()) {
                target.setWalkSpeed(0.2f);
                target.setFlySpeed(0.1f);
                target.removePotionEffect(PotionEffectType.BLINDNESS);
            }
        }
        activeChecks.clear();
    }
}