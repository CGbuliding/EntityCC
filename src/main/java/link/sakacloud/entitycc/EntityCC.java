package link.sakacloud.entitycc;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityCC extends JavaPlugin implements Listener {

    private int maxSilverfish;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        maxSilverfish = getConfig().getInt("max-silverfish");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSilverfishSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.SILVERFISH) {
            int currentSilverfishCount = getSilverfishCount();
            if (currentSilverfishCount >= maxSilverfish) {
                killOldestSilverfish();
            }
        }
    }

    private int getSilverfishCount() {
        int count = 0;
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() == EntityType.SILVERFISH) {
                    count++;
                }
            }
        }
        return count;
    }

    private void killOldestSilverfish() {
        Entity oldestSilverfish = null;
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() == EntityType.SILVERFISH) {
                    if (oldestSilverfish == null || oldestSilverfish.getTicksLived() < entity.getTicksLived()) {
                        oldestSilverfish = entity;
                    }
                }
            }
        }
        if (oldestSilverfish != null) {
            oldestSilverfish.remove();
        }
    }
}
