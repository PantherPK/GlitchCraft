package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pk.panther.glitchcraft.GlitchCraft;
import pk.panther.glitchcraft.Utils;

import java.util.Locale;
import java.util.Optional;
@AllArgsConstructor
public class CatchMobsWithBucket implements Listener {
    private GlitchCraft glitchCraft;
    @EventHandler(ignoreCancelled = true)
    public void onBucketClick(PlayerInteractEntityEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().getType() != Material.BUCKET || !e.getPlayer().isSneaking()) return;
        Entity en = e.getRightClicked();
        e.getRightClicked().remove();
        ItemStack itemStack = new ItemStack(Material.LAVA_BUCKET, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(glitchCraft, "bucket_catcher_" + en.getType().name().toLowerCase(Locale.ENGLISH)), PersistentDataType.STRING, "bucket_catcher_" + en.getType().name().toLowerCase(Locale.ENGLISH));
        itemMeta.displayName(Component.text("Bucket with " + en.getType().name().toLowerCase()));
        itemStack.setItemMeta(itemMeta);
        Utils.removeItemInMainHand(e.getPlayer());
        e.getPlayer().getInventory().addItem(itemStack);
        e.setCancelled(true);
    }
    @EventHandler(ignoreCancelled = true)
    public void onClick(PlayerInteractEvent e) {
        if(e.getInteractionPoint() == null || !e.getAction().isRightClick() || e.getItem() == null || e.getItem().getType() != Material.LAVA_BUCKET) return;
        Optional<NamespacedKey> namespacedKey = e.getItem().getItemMeta().getPersistentDataContainer().getKeys().stream().filter(key -> key.getKey().startsWith("bucket_catcher_")).findAny();
        if(namespacedKey.isEmpty()) return;
        e.setUseItemInHand(Event.Result.DENY);
        if(e.getPlayer().isSneaking()) return;
        e.getInteractionPoint().getWorld().spawnEntity(e.getInteractionPoint(), EntityType.valueOf(namespacedKey.get().getKey().substring(15).toUpperCase()));
        Utils.removeItemInMainHand(e.getPlayer());
        e.getPlayer().getInventory().addItem(new ItemStack(Material.BUCKET, 1));
    }
}
