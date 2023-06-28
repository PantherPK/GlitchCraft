package pk.panther.glitchcraft.features;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
public class MagicLeash implements Listener {
    private final Set<UUID> entities = new HashSet<>();
    @EventHandler(ignoreCancelled = true)
    public void onLeash(PlayerLeashEntityEvent e) {
        toggleMagicLeash(e.getEntity());
    }
    @EventHandler(ignoreCancelled = true)
    public void onUnleash(PlayerUnleashEntityEvent e) {
        toggleMagicLeash(e.getEntity());
    }
    private void toggleMagicLeash(Entity e) {
        if(entities.contains(e.getUniqueId())) {
            e.setGlowing(false);
            e.setInvulnerable(false);
            e.setGravity(true);
            Location l = e.getLocation().clone();
            l.setY(l.getWorld().getHighestBlockYAt(l));
            e.teleportAsync(l.add(0, 1, 0));
            if(e instanceof LivingEntity liv) liv.setHealth(liv.getMaxHealth());
            entities.remove(e.getUniqueId());
        } else {
            e.setGlowing(true);
            e.setInvulnerable(true);
            e.setGravity(false);
            e.teleportAsync(e.getLocation().add(0, 3, 0));
            entities.add(e.getUniqueId());
        }
    }
}
