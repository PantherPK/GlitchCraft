package pk.panther.glitchcraft.features;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class BackTreeGrow implements Listener {

    private final Set<Material> treeBlocks = new HashSet<>(List.of(Material.ACACIA_LOG, Material.BIRCH_LOG, Material.MANGROVE_LOG, Material.CHERRY_LOG, Material.DARK_OAK_LOG, Material.OAK_LOG, Material.JUNGLE_LOG, Material.SPRUCE_LOG));
    private final Map<Player, Integer> clickCounts = new HashMap<>();
    private final Map<Player, Long> lastClickTimes = new HashMap<>();
    @EventHandler(ignoreCancelled = true)
    public void onTreeClick(PlayerInteractEvent e) {
        if(e.getClickedBlock() == null || !treeBlocks.contains(e.getClickedBlock().getType()) || e.getItem() == null || e.getItem().getType() != Material.BONE_MEAL) return;
        Player p = e.getPlayer();
        long currentTime = System.currentTimeMillis();
        long lastClickTime = lastClickTimes.getOrDefault(p, 0L);
        if (currentTime - lastClickTime > 10000) clickCounts.put(p, 1); else {
            int clickCount = clickCounts.getOrDefault(p, 0) + 1;
            clickCounts.put(p, clickCount);
            if (!(clickCount >= 5)) {
                e.getClickedBlock().getWorld().spawnParticle(Particle.COMPOSTER, e.getClickedBlock().getLocation(), 10);
                return;
            }
            switch (e.getClickedBlock().getType()) {
                case ACACIA_LOG -> e.getClickedBlock().setType(Material.ACACIA_SAPLING);
                case BIRCH_LOG -> e.getClickedBlock().setType(Material.BIRCH_SAPLING);
                case MANGROVE_LOG -> e.getClickedBlock().setType(Material.MANGROVE_PROPAGULE);
                case CHERRY_LOG -> e.getClickedBlock().setType(Material.CHERRY_SAPLING);
                case DARK_OAK_LOG -> e.getClickedBlock().setType(Material.DARK_OAK_SAPLING);
                case OAK_LOG -> e.getClickedBlock().setType(Material.OAK_SAPLING);
                case JUNGLE_LOG -> e.getClickedBlock().setType(Material.JUNGLE_SAPLING);
                case SPRUCE_LOG -> e.getClickedBlock().setType(Material.SPRUCE_SAPLING);
            }
            p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
            e.getClickedBlock().getWorld().spawnParticle(Particle.FLASH, e.getClickedBlock().getLocation(), 2);
            clickCounts.put(p, 0);
        } lastClickTimes.put(p, currentTime);
    }
}
