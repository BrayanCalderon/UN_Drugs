package unmineraft.undrugs;

import commands.DrugsCommand;
import commands.UnDrugsCommands;
import events.DrugsEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import unmineraft.undrugs.consumable.Drugs;

import java.io.File;

public final class UNDrugs extends JavaPlugin {
    public String pathConfig;

    PluginDescriptionFile pdfile = getDescription();
    public String version = ChatColor.GREEN + pdfile.getVersion();
    public String name = ChatColor.YELLOW + "[" + ChatColor.GREEN + pdfile.getName() + ChatColor.YELLOW + "]";

    @Override
    public void onEnable() {
        String initPluginMessage = name + ChatColor.WHITE + " has been enabled in the version: " + version;
        Bukkit.getConsoleSender().sendMessage(initPluginMessage);

        this.saveDefaultConfig();

        Drugs.buildDrugs(this);

        commandRegister();
        eventsRegister();
        configRegister();
    }

    @Override
    public void onDisable() {
        String endPluginMessage = name + ChatColor.RED + " has been disabled";
        Bukkit.getConsoleSender().sendMessage(endPluginMessage);
    }

    public void commandRegister(){
        this.getCommand("drugs").setExecutor(new DrugsCommand(this));
        this.getCommand("undrugs").setExecutor(new UnDrugsCommands(this));
    }

    public void eventsRegister(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new DrugsEvent(this), this);
    }

    public void configRegister(){
        File config = new File(this.getDataFolder(), "config.yml");
        pathConfig = config.getPath();

        if(!config.exists()){
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
}
