package link.sakacloud.entitycc;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class EntityCC extends JavaPlugin {

    private int maxSilverfish;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        maxSilverfish = getConfig().getInt("max-silverfish");

        getServer().getPluginManager().registerEvents(new SilverfishListener(), this);
    }

    private class SilverfishListener implements Listener {

        @EventHandler
        public void onEntitySpawn(EntitySpawnEvent event) {
            if (event.getEntity() instanceof Silverfish) {
                Collection<? extends Silverfish> silverfishes = event.getEntity().getWorld().getEntitiesByClass(Silverfish.class);


                    while (silverfishes.size() < maxSilverfish) {
                        silverfishes.stream()
                                .min(Comparator.comparingLong(Entity::getTicksLived))
                                .ifPresent(Entity::remove);
                    }

            }
        }
    }
}

