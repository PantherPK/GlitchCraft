package pk.panther.glitchcraft.features;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pk.panther.glitchcraft.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DigMob implements Listener {
    private final Set<Material> pickaxes = new HashSet<>(List.of(Material.NETHERITE_PICKAXE, Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE));
    private final Map<Player, Integer> clickCounts = new HashMap<>();
    private final Map<Player, Long> lastClickTimes = new HashMap<>();
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR) return;
        if (item == null || !pickaxes.contains(item.getType())) return;
        long currentTime = System.currentTimeMillis();
        long lastClickTime = lastClickTimes.getOrDefault(player, 0L);
        if (currentTime - lastClickTime > 3000) clickCounts.put(player, 1); else {
            int clickCount = clickCounts.getOrDefault(player, 0) + 1;
            clickCounts.put(player, clickCount);
            if (!(clickCount >= 5)) return;
            Entity targetEntity = player.getTargetEntity(5);
            if (targetEntity == null) return;
            targetEntity.remove();
            player.getWorld().dropItem(player.getLocation(), Utils.getSpawnEggByEntity(targetEntity.getType()));
            clickCounts.put(player, 0);
        } lastClickTimes.put(player, currentTime);
    }
}
