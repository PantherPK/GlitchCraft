package pk.panther.glitchcraft.features;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pk.panther.glitchcraft.Utils;
public class GlowDustVision implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onGlowDustClick(PlayerInteractEvent e) {
        if(e.getItem() == null || e.getItem().getType() != Material.GLOWSTONE_DUST || e.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)) return;
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60, 5, true, false));
        Utils.removeItemInMainHand(e.getPlayer());
        e.getPlayer().sendActionBar(Component.text("Voila!"));
    }
}
