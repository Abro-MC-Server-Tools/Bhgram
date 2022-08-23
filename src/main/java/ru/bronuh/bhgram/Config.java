package ru.bronuh.bhgram;

public class Config {
	// Токен для подключения к боту
	public String token = "";

	// Отправлять ли уведомления о подключении игроков в Telegram
	public boolean notifyConnection = true;

	// Отправлять ли все сообщения чата в Telegram
	public boolean streamChat = false;

	// Отправлять ли уведомления о полученных достижениях
	public boolean sendAdvancements = false;

	// Чат, в который будут отправляться уведомления
	public String targetChatId = "0";

	// Имя бота
	public String botName = "SAMPLE_BOT";
}
