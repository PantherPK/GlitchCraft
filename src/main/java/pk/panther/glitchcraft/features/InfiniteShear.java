package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import pk.panther.glitchcraft.GlitchCraft;
@AllArgsConstructor
public class InfiniteShear implements Listener {
    private GlitchCraft glitchCraft;
    @EventHandler(ignoreCancelled = true)
    public void onSheepShear(PlayerShearEntityEvent e) {
        if(e.getEntity().getType() != EntityType.SHEEP || !e.getItem().containsEnchantment(Enchantment.ARROW_INFINITE)) return;
        Bukkit.getScheduler().runTaskLater(glitchCraft, () -> ((Sheep) e.getEntity()).setSheared(false), 10);
    }
}
