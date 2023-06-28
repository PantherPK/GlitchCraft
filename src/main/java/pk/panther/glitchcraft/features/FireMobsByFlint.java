package pk.panther.glitchcraft.features;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
public class FireMobsByFlint implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onFlintAndSteel(PlayerInteractEntityEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().getType() != Material.FLINT_AND_STEEL) return;
        e.getRightClicked().setFireTicks(e.getRightClicked().getFireTicks() + (20 * 5));
    }
}
