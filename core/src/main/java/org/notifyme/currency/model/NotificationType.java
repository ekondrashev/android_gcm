package org.notifyme.currency.model;

/**
 * Created by ekondrashev on 2/18/15.
 */
public enum NotificationType {
    PUSH(15, new AndroidToastNotification.Parser());
    long type;
    Notification.Parser parser;

    private NotificationType(long type, Notification.Parser parser){
        this.type = type;
        this.parser = parser;
    }


    public static NotificationType byType(long type){
       for(NotificationType t: NotificationType.values()) {
           if (t.type == type){
               return t;
           }
       }
        throw new IllegalArgumentException(String.format("Invalid notification type: %d",  type));
    }

    public Notification.Parser parser() {
        return parser;
    }
}

