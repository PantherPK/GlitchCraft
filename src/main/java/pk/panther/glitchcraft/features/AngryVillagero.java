package pk.panther.glitchcraft.features;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
public class AngryVillagero implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onVillagerAttack(PrePlayerAttackEntityEvent e) {
        if(e.isCancelled() || e.getAttacked().getType() != EntityType.VILLAGER) return;
        e.getPlayer().setVelocity(new Vector(0, 1, 0).multiply(2.3));
    }
}
