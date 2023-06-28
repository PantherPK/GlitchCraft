package pk.panther.glitchcraft.features;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import pk.panther.glitchcraft.Utils;
public class EggByMobKill implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onMobKill(EntityDeathEvent e) {
        if(e.getEntity().getKiller() == null) return;
        Player killer = e.getEntity().getKiller();
        if(!killer.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        e.getDrops().add(Utils.getSpawnEggByEntity(e.getEntityType()));
    }
}
