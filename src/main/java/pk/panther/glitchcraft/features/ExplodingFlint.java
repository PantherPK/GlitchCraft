package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pk.panther.glitchcraft.GlitchCraft;

@AllArgsConstructor
public class ExplodingFlint implements Listener {
    private GlitchCraft glitchCraft;
    @EventHandler(ignoreCancelled = true)
    public void onFlintDrop(PlayerDropItemEvent e) {
        if(e.getItemDrop().getItemStack().getType() != Material.FLINT_AND_STEEL) return;
        Bukkit.getScheduler().runTaskLater(glitchCraft, () -> {
            if(!e.getItemDrop().isValid()) return;
            Location loc = e.getItemDrop().getLocation();
            Location underLoc = loc.clone();
            underLoc.setY(loc.getY() - 1);
            loc.getWorld().spawnParticle(Particle.FLASH, loc, 1);
            loc.getWorld().setType(underLoc, Material.NETHERRACK);
            loc.getWorld().setType(loc, Material.FIRE);
            e.getItemDrop().remove();
        }, 20 * 5);
    }
}
