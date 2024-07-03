package link.sakacloud.entitycc;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
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

        private int silverfishCount = 0;


        @EventHandler
        public void onEntityDeath(EntityDeathEvent event) {

            if (event.getEntity() instanceof Silverfish) {
                silverfishCount--;
            }
        }

        @EventHandler
        public void onEntitySpawn(EntitySpawnEvent event) {
            if (event.getEntity() instanceof Silverfish) {
                silverfishCount++;

                if (silverfishCount > maxSilverfish) {
                    World world = event.getEntity().getWorld();
                    List<Silverfish> silverfishes = (List<Silverfish>) world.getEntitiesByClass(Silverfish.class);
                    silverfishes.sort(Comparator.comparingLong(Entity::getTicksLived));
                    silverfishes.getFirst().remove();
                    silverfishCount--;
                }
            }
        }
    }
}
