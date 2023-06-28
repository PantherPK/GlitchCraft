package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
public class AnimalEvolve implements Listener {
    @AllArgsConstructor
    enum ItemRepresent {
        CREEPER(EntityType.CREEPER, Material.GUNPOWDER),
        ZOMBIE(EntityType.ZOMBIE, Material.ROTTEN_FLESH),
        SKELETON(EntityType.SKELETON, Material.BONE),
        SPIDER(EntityType.SPIDER, Material.SPIDER_EYE),
        SLIME(EntityType.SLIME, Material.SLIME_BALL),
        WITCH(EntityType.WITCH, Material.POTION),
        ENDERMAN(EntityType.ENDERMAN, Material.ENDER_PEARL),
        BLAZE(EntityType.BLAZE, Material.ENCHANTED_BOOK),
        GUARDIAN(EntityType.GUARDIAN, Material.PRISMARINE_SHARD),
        ZOMBIE_PIGMAN(EntityType.PIGLIN, Material.GOLD_NUGGET),
        GHAST(EntityType.GHAST, Material.GHAST_TEAR),
        PARROT(EntityType.PARROT, Material.COOKIE);
        private final EntityType entityType;
        private final Material material;
        public static ItemRepresent getItemRepresentByMaterial(Material material) {
            return Arrays.stream(values()).filter(item -> item.material.equals(material)).findFirst().orElse(null);
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void onPigInteract(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Animals animal && animal.isLoveMode()) {
            ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand();
            ItemRepresent itemRepresent = ItemRepresent.getItemRepresentByMaterial(itemStack.getType());
            if(itemRepresent == null) return;
            animal.remove();
            animal.getWorld().spawnEntity(animal.getLocation(), itemRepresent.entityType);
        }
    }
}
