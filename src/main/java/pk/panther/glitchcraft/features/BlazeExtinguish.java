package pk.panther.glitchcraft.features;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import pk.panther.glitchcraft.GlitchCraft;
import pk.panther.glitchcraft.Utils;

import java.util.List;
@AllArgsConstructor
public class BlazeExtinguish implements Listener {
    private GlitchCraft glitchCraft;
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR || p.getInventory().getItemInMainHand().getType() != Material.WATER_BUCKET) return;
        Entity entity = p.getTargetEntity(30, false);
        if(entity == null || entity.getType() != EntityType.BLAZE) return;
        Snowball bullet = p.launchProjectile(Snowball.class);
        bullet.setVelocity(p.getEyeLocation().getDirection().multiply(5.0));
        bullet.setMetadata("blaze_extinguish", new FixedMetadataValue(glitchCraft, p.getName()));
        Utils.removeItemInMainHand(p);
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent e) {
        if(!(e.getEntity() instanceof Snowball)) return;
        Snowball bullet = (Snowball) e.getEntity();
        Entity hitEntity = e.getHitEntity();
        if(hitEntity == null || hitEntity.getType() != EntityType.BLAZE) return;
        Blaze blaze = (Blaze) hitEntity;
        if(!bullet.hasMetadata("blaze_extinguish")) return;
        List<MetadataValue> value = bullet.getMetadata("blaze_extinguish");
        Player p = Bukkit.getPlayer(value.get(0).asString());
        if(p != null) {
            p.sendActionBar(Component.text("Ugaszono blaze na 10 sekund!"));
            return;
        }
        blaze.setAI(false);
        Bukkit.getScheduler().runTaskLater(glitchCraft, () -> {
            if(blaze.isDead()) return;
            blaze.setAI(true);
        }, 200L);
    }
}
