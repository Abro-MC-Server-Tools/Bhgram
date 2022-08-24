package ru.bronuh.bhgram;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventsListener implements Listener {
	PluginContext context;
	BhgramController controller;



	public EventsListener(PluginContext context, BhgramController controller){
		this.context = context;
		this.controller = controller;
	}


	@EventHandler(ignoreCancelled = true ,priority = EventPriority.LOWEST)
	public void playerJoinEvent(PlayerJoinEvent event) {
		context.getLog().info(event.getPlayer().getName()+" зашел на сервер.");
		Player player = event.getPlayer();
		String name = player.getName();

		controller.sendTgMessage("*"+name+"@"+controller.getServerName()+"* зашел на сервер");
		// TODO: Допили меня
	}


	@EventHandler(priority = EventPriority.LOWEST)
	public void playerAdvancementEvent(PlayerAdvancementDoneEvent event){
		// context.getLog().info(event.getPlayer().getName()+" зашел на сервер.");
		if(context.getConfig().sendAdvancements){
			Player player = event.getPlayer();
			String name = player.getName();

			controller.sendTgMessage("*"+name+"@"+controller.getServerName()+"* получил достижение *"+event.getEventName()+"*");
		}
	}



	@EventHandler(priority = EventPriority.LOWEST)
	public void chatEvent(AsyncChatEvent event){
		if(context.getConfig().streamChat){
			Player player = event.getPlayer();
			String name = player.getName();

			controller.sendTgMessage("*"+name+"@"+controller.getServerName()+"*: "+event.message().toString());
		}
	}
}
