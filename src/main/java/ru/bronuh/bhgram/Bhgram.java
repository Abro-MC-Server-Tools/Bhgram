package ru.bronuh.bhgram;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.bronuh.bhgram.commands.SendExecutor;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public final class Bhgram extends JavaPlugin {

	private final Logger log = getLogger();
	private final String pluginDir = getDataFolder().getPath();

	private final Bhgram instance = this;
	Config config = new Config();
	private final PluginContext context = new PluginContext(log, pluginDir, instance, config);
	private final BhgramController controller = new BhgramController(context);
	private final CommandExecutor sendExecutor = new SendExecutor(context,controller);
	private final EventsListener listener = new EventsListener(context,controller);
	private BotSession botSession;
	TelegramBotsApi botsApi;
	@Override
	public void onEnable() {
		// Plugin startup logic
		loadConfig();
		try {
			botsApi = new TelegramBotsApi(DefaultBotSession.class);

			botSession = botsApi.registerBot(controller);
		} catch (Exception e) {
			e.printStackTrace();
		}


		getServer().getPluginManager().registerEvents(listener,this);
		getServer().getPluginCommand("tsend").setExecutor(sendExecutor);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		// TODO: Остановка бота занимает реально дохрена времени. Надо посмотреть
		botSession.stop();
	}


	private void loadConfig(){
		// Пытается получить конфиг из файла. Если файла нет - конфиг пуст
		FileConfiguration fileConfig = getConfig();

		// Назначение стандартных значений в конфиг
		prepareDefaults(fileConfig);

		// В случае появления недостающих параметров в конфиге - они будут дописаны
		fileConfig.options().copyDefaults(true);
		saveConfig();

		// Перенос значений из загружнного файла в экземпляр конфига
		copyToConfig(fileConfig);
	}


	/**
	 * Переносит значения из fileConfig во внутренний конфиг
	 * @param fileConfig
	 */
	private void copyToConfig(FileConfiguration fileConfig) {
		Field[] declaredFields = config.getClass().getDeclaredFields();

		for (Field field : declaredFields) {
			try {
				field.set(config, fileConfig.get(field.getName()));
			}catch (Exception e){
				log.warning("Не удалось присвоить значение полю "+field.getName()+": "+e.getMessage());
			}
		}
	}


	/**
	 * Назначает стандартные значения в fileConfig, после чего записывает в файл недостающие параметры
	 * @param fileConfig
	 */
	private void prepareDefaults(FileConfiguration fileConfig) {
		Field[] declaredFields = config.getClass().getDeclaredFields();
		for(Field field : declaredFields){
			try {
				fileConfig.addDefault(field.getName(), field.get(config));
			}catch (Exception e){
				log.warning("Не удалось получить значение поля "+field.getName()+": "+e.getMessage());
			}
		}
	}
}
