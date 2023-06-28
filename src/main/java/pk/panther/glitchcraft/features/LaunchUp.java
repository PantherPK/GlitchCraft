package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
import pk.panther.glitchcraft.Utils;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class LaunchUp implements Listener {
    private final Map<String, Long> lastClickTimes = new HashMap<>();
    @EventHandler(ignoreCancelled = true)
    public void onDoubleFirework(PlayerInteractEvent e) {
        PlayerInventory inv = e.getPlayer().getInventory();
        if(inv.getItemInMainHand().getType() != Material.FIREWORK_ROCKET || inv.getItemInOffHand().getType() != Material.FIREWORK_ROCKET) return;
        Vector launchVector = new Vector(0, 1, 0);
        long currentTime = System.currentTimeMillis();
        long lastClickTime = lastClickTimes.getOrDefault(e.getPlayer().getName(), 0L);
        if (currentTime - lastClickTime < 3000) return;
        e.getPlayer().setVelocity(launchVector.multiply(1.5));
        e.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, e.getPlayer().getLocation(), 2);
        Utils.removeItemInMainHand(e.getPlayer());
        Utils.removeItemInOffHand(e.getPlayer());
        lastClickTimes.put(e.getPlayer().getName(), currentTime);
    }
}
