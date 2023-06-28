package pk.panther.glitchcraft.features;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import pk.panther.glitchcraft.Utils;

public class ShearOres implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onOreClick(PlayerInteractEvent e) {
        if(e.getClickedBlock() == null || !e.getClickedBlock().getType().name().contains("ORE") || e.getItem() == null || e.getItem().getType() != Material.SHEARS) return;
        e.getPlayer().getInventory().addItem(new ItemStack(e.getClickedBlock().getType(), e.getItem().containsEnchantment(Enchantment.LUCK) ? Utils.getRandom().nextInt(2, 4) : 1));
        if(e.getClickedBlock().getType().name().contains("DEEPSLATE")) e.getClickedBlock().setType(Material.DEEPSLATE); else e.getClickedBlock().setType(Material.STONE);
        Damageable damageable = (Damageable) e.getItem().getItemMeta();
        if(damageable.getDamage() > 1) damageable.setDamage(damageable.getDamage() - 1);
    }
}
