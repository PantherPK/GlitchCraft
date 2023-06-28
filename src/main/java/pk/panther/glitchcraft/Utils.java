package pk.panther.glitchcraft;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    @Getter
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    public static List<Location> getRandomLocationsInSquare(Location center, int radius, int toHeight, int amountOfRandomLocations) {
        List<Location> possibleLocations = new ArrayList<>();
        List<Location> randomLocations = new ArrayList<>();
        for(int i = 0 ; i < toHeight ; i++) possibleLocations.addAll(getSquare(center.add(0, i, 0), radius));
        for(int i = 0 ; i < amountOfRandomLocations ; i++) {
            Location randomLocation = possibleLocations.get(random.nextInt(possibleLocations.size()));
            if(randomLocations.contains(randomLocation)) {
                amountOfRandomLocations++;
                continue;
            }
            randomLocations.add(randomLocation);
        }
        return randomLocations;
    }

    public static List<Block> getSurroudingBlocks(Player p) {
        Block b1 = p.getLocation().getBlock();
        Block b2 = p.getEyeLocation().getBlock();
        return new ArrayList<>(List.of(
                b1.getRelative(BlockFace.DOWN),
                b1.getRelative(BlockFace.NORTH),
                b1.getRelative(BlockFace.EAST),
                b1.getRelative(BlockFace.SOUTH),
                b1.getRelative(BlockFace.WEST),
                b2.getRelative(BlockFace.UP),
                b2.getRelative(BlockFace.NORTH),
                b2.getRelative(BlockFace.EAST),
                b2.getRelative(BlockFace.SOUTH),
                b2.getRelative(BlockFace.WEST)
        ));
    }

    public static List<Block> getSurroudingBlocks(Entity en) {
        Block b1 = en.getLocation().getBlock();
        Block b2 = en.getLocation().getBlock().getRelative(BlockFace.UP);
        return new ArrayList<>(List.of(
                b1.getRelative(BlockFace.DOWN),
                b1.getRelative(BlockFace.NORTH),
                b1.getRelative(BlockFace.EAST),
                b1.getRelative(BlockFace.SOUTH),
                b1.getRelative(BlockFace.WEST),
                b2.getRelative(BlockFace.UP),
                b2.getRelative(BlockFace.NORTH),
                b2.getRelative(BlockFace.EAST),
                b2.getRelative(BlockFace.SOUTH),
                b2.getRelative(BlockFace.WEST)
        ));
    }

    public static List<Location> getSphere(Location loc, int radius, int height, boolean hollow, boolean sphere, int plusY) {
        ArrayList<Location> circleblocks = new ArrayList<>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - radius; x <= cx + radius; ++x) {
            for (int z = cz - radius; z <= cz + radius; ++z) {
                for (int y = sphere ? cy - radius : cy; y < (sphere ? cy + radius : cy + height); ++y) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist >= (radius * radius) || hollow && dist < ((radius - 1) * (radius - 1))) continue;
                    Location l = new Location(loc.getWorld(), x, (y + plusY), z);
                    circleblocks.add(l);
                }
            }
        }
        return circleblocks;
    }

    public static List<Location> getSquare(Location center, int radius) {
        ArrayList<Location> locs = new ArrayList<>();
        int cX = center.getBlockX();
        int cZ = center.getBlockZ();
        int minX = Math.min(cX + radius, cX - radius);
        int maxX = Math.max(cX + radius, cX - radius);
        int minZ = Math.min(cZ + radius, cZ - radius);
        int maxZ = Math.max(cZ + radius, cZ - radius);
        for (int x = minX; x <= maxX; ++x) {
            for (int z = minZ; z <= maxZ; ++z) {
                locs.add(new Location(center.getWorld(), x, center.getBlockY(), z));
            }
        }
        locs.add(center);
        return locs;
    }

    public static ItemStack getSpawnEggByEntity(EntityType entityType) {
        return new ItemStack(Material.valueOf(entityType.name().toUpperCase() + "_SPAWN_EGG"), 1);
    }

    public static void removeItemInMainHand(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack main = inventory.getItemInMainHand();
        if (main.getAmount() > 1) {
            main.setAmount(main.getAmount() - 1);
        } else {
            inventory.setItemInMainHand(null);
        }
    }

    public static void removeItemInOffHand(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack main = inventory.getItemInOffHand();
        if (main.getAmount() > 1) {
            main.setAmount(main.getAmount() - 1);
        } else {
            inventory.setItemInOffHand(null);
        }
    }
}
