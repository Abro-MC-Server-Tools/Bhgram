package ru.bronuh.bhgram.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.bronuh.bhgram.BhgramController;
import ru.bronuh.bhgram.PluginContext;

/**
 * Основа для других обработчиков команд. Несет в себе частично реализованные и дополненные функции обработчика
 */
public abstract class BaseCommand implements CommandExecutor {
	protected PluginContext context;
	protected BhgramController controller;

	public BaseCommand(PluginContext context, BhgramController controller) {
		this.context = context;
		this.controller = controller;
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
		String action = (argsCount > 0 ? args[0] : "").toLowerCase();
		String targetName = argsCount > 1 ? args[1] : "";



		return false;
	}

	/**
	 * Отправляет ответ зеленго цвета
	 * @param player поучатель ответа
	 * @param message текст сообщения
	 */
	protected void respondOk(CommandSender player, String message){
		player.sendMessage(Component.text(message,context.okColor));
	}

	/**
	 * Отправляет сообщение об ошибке красного цвета
	 * @param player получатель ответа
	 * @param message текст сообщения
	 */
	protected void respondError(CommandSender player, String message){
		player.sendMessage(Component.text("Ошибка: "+message,context.warnColor));
	}
}
