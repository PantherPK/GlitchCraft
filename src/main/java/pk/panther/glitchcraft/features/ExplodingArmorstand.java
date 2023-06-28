package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import pk.panther.glitchcraft.GlitchCraft;
@AllArgsConstructor
public class ExplodingArmorstand implements Listener {
    private GlitchCraft glitchCraft;
    @EventHandler(ignoreCancelled = true)
    public void on(PlayerArmorStandManipulateEvent e) {
        if(e.getPlayerItem().getType() != Material.CREEPER_HEAD) return;
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1, 1);
        Bukkit.getScheduler().runTaskLater(glitchCraft, () -> e.getPlayer().getWorld().createExplosion(e.getRightClicked().getLocation(), 4, false, true), 20 * 5);
    }
}
