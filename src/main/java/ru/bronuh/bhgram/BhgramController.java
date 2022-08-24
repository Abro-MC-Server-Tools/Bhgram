package ru.bronuh.bhgram;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;


/**
 * TODO: Временный конфиг
 * TODO: Отключение сообщений со стороны Telegram
 * TODO: Автоматическая отправка всех сообщений из TG в MC
 * TODO: Индивидуальные настройки для пользователей
 */
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
				}else{
					if(context.getConfig().streamTelegram){
						log.info(message.getFrom().getUserName()+" (ChatId: "+message.getChatId()+"): "+text);
						for (Player player : Bukkit.getOnlinePlayers()) {
							player.sendMessage(Component.text(update.getMessage().getFrom().getUserName()+": "+text.replace("/mc ",""), context.tgColor));
						}
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


	/**
	 * Костыль для получения адреса сервера
	 * @return ip:port
	 */
	public String getServerName(){
		if(context.getConfig().useServerName){
			return context.getConfig().serverName;
		}
		URL whatismyip = null;
		String serverName = "<unknown>";
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			serverName = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serverName +":"+context.getPluginInstance().getServer().getPort();
	}
}
