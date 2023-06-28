package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pk.panther.glitchcraft.GlitchCraft;
@AllArgsConstructor
public class ExplodeTorch implements Listener {
    private GlitchCraft glitchCraft;
    @EventHandler(ignoreCancelled = true)
    public void onTorchClick(PlayerInteractEvent e) {
        if(e.getClickedBlock() == null || e.getItem() == null || e.getItem().getType() != Material.FLINT_AND_STEEL) return;
        if(e.getClickedBlock().getType() != Material.REDSTONE_TORCH || e.getClickedBlock().getType() != Material.REDSTONE_WALL_TORCH) return;
        Bukkit.getScheduler().runTaskLater(glitchCraft, () -> {
            if(e.getClickedBlock().getLocation().getBlock().getType() == Material.REDSTONE_TORCH || e.getClickedBlock().getLocation().getBlock().getType() == Material.REDSTONE_WALL_TORCH ) e.getClickedBlock().getWorld().createExplosion(e.getClickedBlock().getLocation(), 3F, true);
        }, 20 * 3);
    }
}
