package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String textMessage;
    private LocalDateTime timeMessage;

    public LocalDateTime getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(LocalDateTime timeMessage) {
        this.timeMessage = timeMessage;
    }
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public LocalDateTime getTime() {
        return timeMessage;
    }

    public void setTime(LocalDateTime time) {
        this.timeMessage = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(textMessage, that.textMessage) && Objects.equals(timeMessage, that.timeMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, textMessage, timeMessage);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId='" + chatId + '\'' +
                ", textMessage='" + textMessage + '\'' +
                ", timeMessage=" + timeMessage +
                '}';
    }
}
