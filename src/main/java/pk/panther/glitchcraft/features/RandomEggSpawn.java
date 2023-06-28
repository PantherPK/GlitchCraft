package pk.panther.glitchcraft.features;

import com.destroystokyo.paper.event.entity.ThrownEggHatchEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pk.panther.glitchcraft.Utils;
public class RandomEggSpawn implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEggSpawn(ThrownEggHatchEvent e) {
        if(!e.isHatching()) return;
        e.setHatchingType(EntityType.values()[Utils.getRandom().nextInt(EntityType.values().length)]);
    }
}
