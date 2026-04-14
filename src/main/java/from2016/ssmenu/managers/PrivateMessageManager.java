package from2016.ssmenu.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrivateMessageManager {

    private final Map<UUID, UUID> activeConversations = new HashMap<>();

    public void startConversation(UUID moderatorUUID, UUID targetUUID) {
        activeConversations.put(targetUUID, moderatorUUID);
        activeConversations.put(moderatorUUID, targetUUID);
    }

    public void endConversation(UUID playerUUID) {
        UUID partner = activeConversations.remove(playerUUID);
        if (partner != null) {
            activeConversations.remove(partner);
        }
    }

    public boolean isInConversation(UUID playerUUID) {
        return activeConversations.containsKey(playerUUID);
    }

    public UUID getConversationPartner(UUID playerUUID) {
        return activeConversations.get(playerUUID);
    }
}