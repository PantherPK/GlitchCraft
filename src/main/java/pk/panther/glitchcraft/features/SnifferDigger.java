package pk.panther.glitchcraft.features;

import io.papermc.paper.event.entity.EntityMoveEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sniffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pk.panther.glitchcraft.GlitchCraft;
import pk.panther.glitchcraft.Utils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SnifferDigger implements Listener {
    private @NonNull GlitchCraft glitchCraft;
    private final Map<Sniffer, Inventory> inventories = new HashMap<>();
    @EventHandler(ignoreCancelled = true)
    public void onSnifferMove(EntityMoveEvent e) {
        if(!e.hasChangedBlock() || e.getEntityType() != EntityType.SNIFFER) return;
        Sniffer en = (Sniffer) e.getEntity();
        Inventory enInv = inventories.getOrDefault(en, Bukkit.createInventory(null, InventoryType.CHEST, Component.text("Sniffer Equipment")));
        if(enInv.firstEmpty() == -1) {
            e.getEntity().getWorld().spawnParticle(Particle.GLOW, en.getLocation(), 1);
            return;
        }
        Location loc = en.getLocation();
        Vector direction = loc.getDirection().normalize();

        int toHeight = 4;

        Map<Material, Integer> digged = new HashMap<>();
        for(int h = 0 ; h <= toHeight ; h++) {
            Utils.getSquare(loc.clone().add(0, h, 0).add(direction.clone().multiply(2)), 3).stream().filter(l -> l.getBlock().getType() != Material.AIR).forEach(l -> {
                Block block = l.getBlock();
                int diggedAmount = digged.getOrDefault(block.getType(), 0) + 1;
                digged.put(block.getType(), diggedAmount);
                block.setType(Material.AIR);
                block.getWorld().spawnParticle(Particle.HEART, l, 1);
            });
        }
        digged.forEach((m, i) -> {
            if(enInv.firstEmpty() == -1) {
                e.getTo().getWorld().dropItem(en.getLocation(), new ItemStack(m, i));
                e.getEntity().getWorld().spawnParticle(Particle.GLOW, en.getLocation(), 1);
                return;
            }
            enInv.addItem(new ItemStack(m, i));
        });
        inventories.put(en, enInv);
    }


    @EventHandler(ignoreCancelled = true)
    public void onSnifferClick(PlayerInteractEntityEvent e) {
        if(e.getRightClicked().getType() != EntityType.SNIFFER) return;
        Sniffer en = (Sniffer) e.getRightClicked();
        if(e.getPlayer().isSneaking()) {
            Inventory enInv = inventories.getOrDefault(en, Bukkit.createInventory(null, InventoryType.CHEST, Component.text("Sniffer Equipment")));
            e.getPlayer().openInventory(enInv);
        } else {
            en.addPassenger(e.getPlayer());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(PlayerInteractEvent e) {
        if(!e.getPlayer().isInsideVehicle()) return;
        if(e.getPlayer().getVehicle() == null || e.getPlayer().getVehicle().getType() != EntityType.SNIFFER) return;
        Sniffer sniffer = (Sniffer) e.getPlayer().getVehicle();
        switch (e.getAction()) {
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK, RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                sniffer.getLocation().setYaw(e.getPlayer().getEyeLocation().getYaw());
                sniffer.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(1));
            }
        }
    }
}
