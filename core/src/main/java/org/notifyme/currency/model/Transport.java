package org.notifyme.currency.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by ekondrashev on 2/19/15.
 */
public class Transport extends Base {
    protected Transport(Long id) {
        super(id);
    }

    public static class Parser {

        public <T extends Transport> T fromJson(String json) throws ParseException {
            JSONParser p = new JSONParser();
            JSONObject o = (JSONObject) p.parse(json);
            Long type = (Long) o.get("polymorphic_ctype");
            return TransportType.byType(type).parser().fromJson(json);
        }
    }
}
