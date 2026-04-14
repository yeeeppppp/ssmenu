package from2016.ssmenu.tasks;

import from2016.ssmenu.managers.CheckPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CheckPlayerTimerTask implements Runnable {

    private final CheckPlayerManager manager;
    private final UUID targetUUID;
    private int messageCounter = 0;

    public CheckPlayerTimerTask(CheckPlayerManager manager, UUID targetUUID) {
        this.manager = manager;
        this.targetUUID = targetUUID;
    }

    @Override
    public void run() {
        CheckPlayerManager.CheckPlayerSession session = manager.getSession(targetUUID);

        if (session == null || !session.active) {
            return;
        }

        if (session.frozen) {
            Player target = Bukkit.getPlayer(targetUUID);
            if (target != null && target.isOnline()) {
                int mins = session.remainingSeconds / 60;
                int secs = session.remainingSeconds % 60;
                session.bossBar.setTitle("§6§lВРЕМЯ ПРОВЕРКИ §7(остановлено) §e" + mins + "м " + secs + "с");
            }
            return;
        }

        if (session.remainingSeconds <= 0) {
            manager.handleTimeOut(targetUUID);
            return;
        }

        session.remainingSeconds--;

        double progress = Math.min(1.0, (double) session.remainingSeconds / (5 * 60));
        if (progress < 0) progress = 0;
        session.bossBar.setProgress(progress);

        Player target = Bukkit.getPlayer(targetUUID);
        if (target != null && target.isOnline()) {
            int minutes = session.remainingSeconds / 60;
            int seconds = session.remainingSeconds % 60;
            session.bossBar.setTitle("§6§lВРЕМЯ ПРОВЕРКИ §7| §e" + minutes + "м " + seconds + "с");

            messageCounter++;
            if (messageCounter >= 20) {
                messageCounter = 0;
                target.sendMessage("§6§l=== ПРАВИЛА ПРОВЕРКИ ===");
                target.sendMessage("§7- Напишите в чат ваш Discord/Rudesk/Anydesk");
                target.sendMessage("§7- Выход с проверки = бан на 14 дней");
                target.sendMessage("§7- Не пытайтесь обойти проверку");
                target.sendMessage("§c- Не затягивайте время!");
            }
        }
    }
}