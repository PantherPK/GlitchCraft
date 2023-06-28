package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pk.panther.glitchcraft.Utils;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class FishyDay implements Listener {
    private final List<EntityType> fishes = new ArrayList<>(List.of(EntityType.TROPICAL_FISH, EntityType.PUFFERFISH, EntityType.SALMON, EntityType.DOLPHIN));
    @EventHandler(ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent e) {
        Player p = e.getPlayer();
        ItemStack fishingRod = p.getInventory().getItemInMainHand();
        if (!fishingRod.containsEnchantment(Enchantment.ARROW_INFINITE) || e.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        Location fishLocation = e.getHook().getLocation();
        int fishAmount = Utils.getRandom().nextInt(2, 5);
        if(e.getPlayer().getInventory().getItemInMainHand().getType() != Material.FISHING_ROD || !e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.ARROW_INFINITE)) return;
        for(int i = 0; i < fishAmount ; i++) {
            Entity fishEntity = fishLocation.getWorld().spawnEntity(fishLocation, fishes.get(Utils.getRandom().nextInt((fishes.size() - 1))));
            Vector direction = p.getLocation().subtract(fishLocation).toVector().normalize();
            fishEntity.setVelocity(direction.multiply(1.7));
        }
    }
}
