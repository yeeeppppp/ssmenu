package from2016.ssmenu.listeners;

import from2016.ssmenu.managers.CheckPlayerManager;
import from2016.ssmenu.managers.PrivateMessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PrivateMessageListener implements Listener {

    private final CheckPlayerManager checkPlayerManager;
    private final PrivateMessageManager privateMessageManager;

    public PrivateMessageListener(CheckPlayerManager checkPlayerManager, PrivateMessageManager privateMessageManager) {
        this.checkPlayerManager = checkPlayerManager;
        this.privateMessageManager = privateMessageManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        UUID partnerUUID = privateMessageManager.getConversationPartner(sender.getUniqueId());
        if (partnerUUID == null) {
            return;
        }

        event.setCancelled(true);

        Player receiver = sender.getServer().getPlayer(partnerUUID);
        if (receiver == null || !receiver.isOnline()) {
            sender.sendMessage("§cСобеседник вышел из сети!");
            privateMessageManager.endConversation(sender.getUniqueId());
            return;
        }

        String message = event.getMessage();

        sender.sendMessage("§7[Вы -> " + receiver.getName() + "§7] §f" + message);
        receiver.sendMessage("§7[" + sender.getName() + " -> Вам§7] §f" + message);
    }
}