package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public void saveBd(String item, String time, long chatId){
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setTime(LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        notificationTask.setTextMessage(item);
        notificationTask.setChatId(chatId);
        notificationTaskRepository.save(notificationTask);
    }

}
