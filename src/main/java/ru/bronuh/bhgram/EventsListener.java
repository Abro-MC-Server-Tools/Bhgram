package ru.bronuh.bhgram;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventsListener implements Listener {
	PluginContext context;
	BhgramController controller;

	public EventsListener(PluginContext context, BhgramController controller){
		this.context = context;
		this.controller = controller;
	}

	@EventHandler(ignoreCancelled = true ,priority = EventPriority.HIGHEST)
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		context.getLog().info(event.getPlayer().getName()+" зашел на сервер.");
		Player player = event.getPlayer();
		String name = player.getName();

		controller.sendTgMessage(name+" зашел на сервер");
		// TODO: Допили меня
	}
}
