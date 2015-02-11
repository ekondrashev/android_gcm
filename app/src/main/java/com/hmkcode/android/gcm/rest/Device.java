package com.hmkcode.android.gcm.rest;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

/**
 * Created by ekondrashev on 2/11/15.
 */
public class Device  implements JSONAware {

    private String installationId;

    public Device(String installationId) {
        this.installationId = installationId;
    }

    @Override
    public String toJSONString() {
            StringBuffer sb = new StringBuffer();

            sb.append("{");

            sb.append("\""+ JSONObject.escape("installation_id") + "\"");
            sb.append(":");
            sb.append("\"" + JSONObject.escape(installationId) + "\"");

            sb.append("}");

            return sb.toString();

    }
}
