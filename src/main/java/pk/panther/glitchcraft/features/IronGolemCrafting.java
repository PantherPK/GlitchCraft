package pk.panther.glitchcraft.features;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class IronGolemCrafting implements Listener {
    private final Set<Player> players = new HashSet<>();
    @EventHandler(ignoreCancelled = true)
    public void onCraftGolem(PrepareItemCraftEvent e) { // [A, B, C, D, E, F, G, H, I]
        ItemStack[] ic = e.getInventory().getContents();
        Player p = (Player) e.getView().getPlayer();
        boolean golem = isGolem(ic);
        boolean golemReversed = isGolemReversed(ic);
        Block block = p.getTargetBlockExact(5);
        if(players.contains(p) || !build(golem, golemReversed) || block == null || block.getType() != Material.CRAFTING_TABLE) return;
        players.add(p);
        Entity en = p.getWorld().spawnEntity(block.getLocation(), EntityType.IRON_GOLEM, CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM);
        if(golemReversed) en.customName(Component.text("Dinnerbone"));
        e.getInventory().setContents(new ItemStack[]{new ItemStack(Material.AIR)});
        p.getInventory().addItem(new ItemStack(Material.CARVED_PUMPKIN, 1));
        p.getInventory().addItem(new ItemStack(Material.IRON_BLOCK, 4));
        p.getInventory().removeItem(new ItemStack(Material.IRON_BLOCK, 4), new ItemStack(Material.CARVED_PUMPKIN, 1));
        p.closeInventory();
        block.breakNaturally(true);
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
        p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_REPAIR, 1, 1);
        players.remove(p);
    }

    private boolean build(boolean isGolem, boolean isGolemReversed) {
        return isGolem || isGolemReversed;
    }


    private boolean isGolem(ItemStack[] ic) {
        return ic[0].getType() == Material.AIR
                && ic[1].getType() == Material.AIR
                && ic[2].getType() == Material.CARVED_PUMPKIN
                && ic[3].getType() == Material.AIR
                && ic[4].getType() == Material.IRON_BLOCK
                && ic[5].getType() == Material.IRON_BLOCK
                && ic[6].getType() == Material.IRON_BLOCK
                && ic[7].getType() == Material.AIR
                && ic[8].getType() == Material.IRON_BLOCK
                && ic[9].getType() == Material.AIR;
    }
    private boolean isGolemReversed(ItemStack[] ic) {
        return ic[0].getType() == Material.AIR
                && ic[1].getType() == Material.AIR
                && ic[2].getType() == Material.IRON_BLOCK
                && ic[3].getType() == Material.AIR
                && ic[4].getType() == Material.IRON_BLOCK
                && ic[5].getType() == Material.IRON_BLOCK
                && ic[6].getType() == Material.IRON_BLOCK
                && ic[7].getType() == Material.AIR
                && ic[8].getType() == Material.CARVED_PUMPKIN
                && ic[9].getType() == Material.AIR;
    }
}
