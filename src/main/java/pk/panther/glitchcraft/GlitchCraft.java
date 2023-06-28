package pk.panther.glitchcraft;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pk.panther.glitchcraft.features.*;

import java.util.Arrays;
public class GlitchCraft extends JavaPlugin {
    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        this.registerListeners(new AngryVillagero(), new AnimalEvolve(), new BackTreeGrow(), new BadTnt(), new BlazeExtinguish(this), new CactusKiller(), new CatchMobsWithBucket(this), new CatchShulkerBullet(this), new DigMob(), new EggByMobKill(), new ExplodeTorch(this), new ExplodingArmorstand(this), new ExplodingFlint(this), new FireBoots(this), new FireMobsByFlint(), new FishyDay(), new GlowDustVision(), new InfiniteShear(this), new IronGolemCrafting(), new LaunchUp(), new LavaSnowman(), new MagicLeash(), new RandomEggSpawn(), new RandomSheepColor(), new ShearOres());
        this.getLogger().info("Loaded plugin in " + (System.currentTimeMillis() - start) + "ms");
    }
    public void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }
}