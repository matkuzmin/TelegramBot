package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    private final NotificationTaskService notificationTaskService;
    private final NotificationTaskRepository notificationTaskRepository;
    public TelegramBotUpdatesListener(NotificationTaskService notificationTaskService, NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskService = notificationTaskService;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            String messageText = update.message().text();
            if (messageText.equals("/start")){
                long chatId = update.message().chat().id();
                SendMessage message = new SendMessage(chatId,messageText);
                SendResponse response = telegramBot.execute(new SendMessage(chatId,"Привет\nформат внесения напоминания в бота - 01.01.2022 20:00 Сделать домашнюю работу"));
                logger.info("Print message: {}", response);
            }
            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
            Matcher matcher = pattern.matcher(messageText);
            if (matcher.matches()) {
                long chatId = update.message().chat().id();
                String date = matcher.group(1);
                String item = matcher.group(3);
               notificationTaskService.saveBd(item,date,chatId);
                   logger.info("Save bd id= : {}", chatId);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
     List<NotificationTask> messagePrint = notificationTaskRepository.
findNotificationTaskByTimeMessage(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
     if (messagePrint.size()>0){
         for (int i = 0; i < messagePrint.size(); i++) {
           NotificationTask printText =  messagePrint.get(i);
             SendMessage message = new SendMessage(printText.getChatId(), printText.getTextMessage());
             SendResponse response = telegramBot.execute(message);
         }
         logger.info("print message count : {}", messagePrint.size());
     }
        }
    }


