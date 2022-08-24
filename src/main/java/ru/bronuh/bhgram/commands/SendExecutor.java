package ru.bronuh.bhgram.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.bronuh.bhgram.BhgramController;
import ru.bronuh.bhgram.PluginContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Обработчик команды /tsend (/ts)
 */
public class SendExecutor extends BaseCommand {


	public SendExecutor(PluginContext context, BhgramController controller) {
		super(context,controller);
	}



	/**
	 * Метод обработчик команд. Если возвращается false, сервер отправит вызвавшему команду её описание из plugin.yml
	 * @param commandSender Source of the command
	 * @param command Command which was executed
	 * @param label Alias of the command which was used
	 * @param args Passed command arguments
	 * @return
	 */
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		CommandSender sender = commandSender;
		String senderName = sender.getName();
		int argsCount = args.length;
		StringBuilder builder = new StringBuilder();

		builder.append("*").append(senderName).append('@').append(controller.getServerName()).append(":* ");
		for (String arg : args) {
			builder.append(arg).append(" ");
		}

		String text = builder.toString();
		controller.sendTgMessage(text);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(Component.text(text, context.tgColor));
		}
		return true;
	}



}
