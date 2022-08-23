package ru.bronuh.bhgram;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BhgramController extends TelegramLongPollingBot {
	PluginContext context;
	private String dataDir;
	private Logger log;
	public BhgramController(PluginContext context) {
		super();
		this.context = context;
		this.dataDir = new File(context.getPluginDir()).getPath();
		log = context.getLog();
	}

	@Override
	public String getBotUsername() {
		return context.getConfig().botName;
	}


	@Override
	public String getBotToken() {
		return context.getConfig().token;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			if (message.hasText()) {
				String text = message.getText();
				if(text.startsWith("/mc ")){
					log.info(message.getFrom().getUserName()+" (ChatId: "+message.getChatId()+"): "+text);
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.sendMessage(Component.text(update.getMessage().getFrom().getUserName()+": "+text.replace("/mc ",""), context.tgColor));
					}
				}
			}
		}
	}

	/**
	 * Отправляет сообщение в чат, указанный в конфиге
	 * TODO: добавить поддержку нескольких чатов
	 * @param message текст сообщения
	 */
	public void sendTgMessage(String message){
		SendMessage msg = new SendMessage();
		msg.setText(message);
		msg.setChatId(context.getConfig().targetChatId);
		msg.enableMarkdown(true);
		//msg.enableMarkdownV2(true);
		try {
			execute(msg);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
