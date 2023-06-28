package pk.panther.glitchcraft.features;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import pk.panther.glitchcraft.Utils;

import java.util.HashMap;
import java.util.Map;

public class CactusKiller implements Listener {
    private final Map<LivingEntity, Long> lastTimes = new HashMap<>();
    private final Map<LivingEntity, Integer> damageCounts = new HashMap<>();
    @EventHandler(ignoreCancelled = true)
    public void onCactusDamage(EntityDamageByBlockEvent e) {
        if(e.getDamager() == null || e.getDamager().getType() != Material.CACTUS) return;
        if(e.getEntity() instanceof LivingEntity living) {
            long currentTime = System.currentTimeMillis();
            long lastClickTime = lastTimes.getOrDefault(living, 0L);
            if (currentTime - lastClickTime > 3000) damageCounts.put(living, 0); else {
                int damageCount = damageCounts.getOrDefault(living, 0) + 1;
                damageCounts.put(living, damageCount);
                if (damageCount <= 2) return;
                living.damage(5000.0);
                Utils.getRandomLocationsInSquare(living.getLocation(), 5, 4, 7).forEach(l -> living.getWorld().spawnParticle(Particle.SCULK_SOUL, l, 1));
                damageCounts.put(living, 0);
            }lastTimes.put(living, currentTime);
        }
    }
}
