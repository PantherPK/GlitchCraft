package pk.panther.glitchcraft.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Optional;
public class BadTnt implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPrime(TNTPrimeEvent e) {
        if(e.isCancelled() || e.getPrimingEntity() == null || e.getCause() != TNTPrimeEvent.PrimeCause.PLAYER) return;
        Optional<Player> player = e.getPrimingEntity().getLocation().getNearbyPlayers(10).stream().findAny();
        if(player.isEmpty()) return;
        player.get().teleportAsync(e.getBlock().getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
}
