package maxmc.company.writebook.writebook;

import maxmc.company.writebook.writebook.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * @author sanseyooyea
 */
public class WriteBook extends JavaPlugin {

    /**
     * WriteBook's Admin Permission
     */
    public static final String WRITEBOOK_ADMIN_PERMISSION = "WriteBook.admin";
    public static String prefix;
    public static String successMessage;

    @Override
    public void onEnable() {
        //When loading plugin
        Objects.requireNonNull(this.getCommand("write")).setExecutor(new Write());
        //register the command
        System.out.println(ansi().eraseScreen().render("@|red The Plugin WriteBook is Running|@"));
        getLogger().info(prefix + ">>The Plugin WriteBook is Running");
        System.out.println(ansi().eraseScreen().render("@|green Author : SanseYooyea|@"));
        getLogger().info("Author : SanseYooyea");
        saveDefaultConfig();
        reloadConfig();
        {
            prefix = getConfig().getString("info.prefix");
            successMessage = getConfig().getString("info.successMessage");
        }
    }

    @Override
    public void onDisable() {
        //When unloading plugin
        getLogger().info(prefix + ">>The Plugin WriteBook is stopping");
        getLogger().info("Author : SanseYooyea");
        saveConfig();
    }
}
