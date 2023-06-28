package pk.panther.glitchcraft.features;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import pk.panther.glitchcraft.Utils;

public class RandomSheepColor implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onShear(PlayerShearEntityEvent e) {
        if(e.getEntity().getType() != EntityType.SHEEP) return;
        randomizeSheepColor((Sheep) e.getEntity());
    }
    @EventHandler(ignoreCancelled = true)
    public void onSpawnSheep(EntitySpawnEvent e) {
        if(e.getEntityType() != EntityType.SHEEP) return;
        randomizeSheepColor((Sheep) e.getEntity());
    }
    private void randomizeSheepColor(Sheep sheep) {
        sheep.setColor(DyeColor.values()[Utils.getRandom().nextInt(DyeColor.values().length - 1)]);
    }
}
