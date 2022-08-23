package ru.bronuh.bhgram;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PluginContext {
	public final TextColor
			warnColor = TextColor.color(255,100,100),
			okColor = TextColor.color(100,255,100),
			tgColor = TextColor.color(100,175,255);


	private Logger log;
	private String pluginDir;
	private JavaPlugin pluginInstance;

	private Config config;

	public PluginContext(Logger log, String pluginDir, JavaPlugin pluginInstance, Config config){
		this.log = log;
		this.pluginDir = pluginDir;
		this.pluginInstance = pluginInstance;
		this.config = config;
	}

	public String getPluginDir() {
		return pluginDir;
	}

	public Logger getLog() {
		return log;
	}

	public JavaPlugin getPluginInstance(){return pluginInstance;}

	public Config getConfig() {
		return config;
	}
}
