package pk.panther.glitchcraft.features;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LavaSnowman implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onSnowMan(EntityMoveEvent e) {
        if(e.isCancelled() || e.getEntity().getType() != EntityType.SNOWMAN || !e.hasChangedBlock() || e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() != Material.LAVA) return;
        e.getTo().getBlock().getRelative(BlockFace.DOWN).setType(Material.OBSIDIAN);
        e.getTo().getBlock().setType(Material.SNOW);
    }
}
