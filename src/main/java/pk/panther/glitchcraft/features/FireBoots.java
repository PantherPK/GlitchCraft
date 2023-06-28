package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pk.panther.glitchcraft.GlitchCraft;
import pk.panther.glitchcraft.Utils;

import java.util.List;

@AllArgsConstructor
public class FireBoots implements Listener {
    private GlitchCraft glitchCraft;
    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent e) {
        if(!e.hasChangedBlock() || e.getPlayer().isInWater() || e.getPlayer().isFlying()) return;
        if(e.getPlayer().getInventory().getBoots() == null || e.getPlayer().getInventory().getBoots().getType() != Material.CHAINMAIL_BOOTS) return;
        Location under = new Location(e.getTo().getWorld(), e.getTo().getX(), e.getTo().getY() - 1, e.getTo().getZ());
        List<Location> locations = Utils.getSphere(under, 3, under.getBlockY(), false, false, 0).stream().filter(l -> l.getBlock().getType() == Material.WATER).toList();
        locations.forEach(l -> {
            e.getTo().getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, l, 1);
            l.getBlock().setType(Material.OBSIDIAN);
        });
        Bukkit.getScheduler().runTaskLater(glitchCraft, () -> locations.forEach(l -> {
            e.getTo().getWorld().spawnParticle(Particle.FLAME, l, 1);
            l.getBlock().setType(Material.WATER);
        }), 100);
    }
}
