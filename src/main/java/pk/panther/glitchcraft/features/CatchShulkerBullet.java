package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import pk.panther.glitchcraft.GlitchCraft;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CatchShulkerBullet implements Listener {
    private GlitchCraft glitchCraft;
    @EventHandler(ignoreCancelled = true)
    public void onClickShulkerBullet(PlayerInteractEntityEvent e) {
        if(e.getRightClicked().getType() != EntityType.SHULKER_BULLET) return;
        e.getRightClicked().remove();
        ItemStack it = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta itm = it.getItemMeta();
        itm.displayName(Component.text("ShulkerBullet"));
        itm.lore(List.of(Component.text("Shoots at nearby player")));
        itm.getPersistentDataContainer().set(new NamespacedKey(glitchCraft, "shulker_bullet_star"), PersistentDataType.STRING, "shulker_bullet_star");
        itm.addEnchant(Enchantment.DURABILITY, 1, true);
        itm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        it.setItemMeta(itm);
        e.getPlayer().getInventory().addItem(it);
    }
    @EventHandler(ignoreCancelled = true)
    public void onClick(PlayerInteractEvent e) {
        if(e.getItem() == null || e.getItem().getType() != Material.NETHER_STAR) return;
        Optional<NamespacedKey> namespacedKey = e.getItem().getItemMeta().getPersistentDataContainer().getKeys().stream().filter(key -> key.getKey().contains("shulker_bullet_star")).findAny();
        if(namespacedKey.isEmpty()) return;
        Location eyeLocation = e.getPlayer().getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();
        Optional<Player> player = e.getPlayer().getWorld().getNearbyPlayers(e.getPlayer().getLocation(), 20, 20).stream().filter(find -> !find.getName().equals(e.getPlayer().getName())).findAny();
        if(player.isEmpty()) {
            e.getPlayer().sendMessage(Component.text("Brak graczy w pobliżu do strzelenia pocisku shulkera"));
            return;
        }
        ShulkerBullet shulkerBullet = e.getPlayer().getWorld().spawn(eyeLocation.add(direction), ShulkerBullet.class);
        shulkerBullet.setGlowing(true);
        shulkerBullet.setTarget(player.get());
    }
}
