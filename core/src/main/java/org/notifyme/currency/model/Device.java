package org.notifyme.currency.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

/**
 * Created by ekondrashev on 2/11/15.
 */
public class Device extends Subscriber implements JSONAware {

    private String installationId;

    public Device(String installationId) {
        this(null, installationId);
    }

    public Device(Long id, String installationId) {
        super(id);
        this.installationId = installationId;
    }

    public String installationId() {
        return installationId;
    }
    @Override
    public String toJSONString() {
        StringBuffer sb = new StringBuffer();

        sb.append("{");
        if (id != null) {
            sb.append("\"" + JSONObject.escape("id") + "\"");
            sb.append(":");
            sb.append("\"" + JSONObject.escape(String.valueOf(id)) + "\"");
        }

        sb.append("\"" + JSONObject.escape("installation_id") + "\"");
        sb.append(":");
        sb.append("\"" + JSONObject.escape(installationId) + "\"");

        sb.append("}");

        return sb.toString();

    }
     public static class Parser extends Base.Parser

    {
        @Override
        public Device fromJson(String json) throws ParseException {
            JSONParser p = new JSONParser()
                    ;
            JSONObject o = (JSONObject) p.parse(json);
            Long id = (Long) o.get("id");

            Map<String, Object> map = (Map<String, Object>) o;
            String installationId = (String) map.get("installation_id");
            return new Device(id, installationId);
        }
    }
}
