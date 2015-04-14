package org.notifyme.currency.model;

/**
 * Created by ekondrashev on 2/19/15.
 */
public class GCMTransport extends Transport {
    private final String regId;
    protected GCMTransport(Long id, String regId) {
        super(id);
        this.regId = regId;
    }

    static class Parser extends Transport.Parser {
        public Transport fromJson(String json) {
            return null;
        }
    }
}
