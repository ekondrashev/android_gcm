package org.notifyme.currency.model;

/**
 * Created by ekondrashev on 2/18/15.
 */
public enum TransportType {
    PUSH(13, new GCMTransport.Parser());
    long type;
    Transport.Parser parser;

    private TransportType(long type, Transport.Parser parser){
        this.type = type;
        this.parser = parser;
    }


    public static TransportType byType(long type){
       for(TransportType t: TransportType.values()) {
           if (t.type == type){
               return t;
           }
       }
        throw new IllegalArgumentException(String.format("Invalid notification type: %d",  type));
    }

    public Transport.Parser parser() {
        return parser;
    }
}

