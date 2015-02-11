package com.hmkcode.android.gcm;

import android.content.Context;

import com.hmkcode.android.gcm.rest.Device;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class Installation {
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static void registerOnFirstLaunch(Context context) throws RegistrationException {
        new File(context.getFilesDir(), INSTALLATION).delete();
        if(!installationFileExists(context)) {
            try {
                HttpClient client = new HttpClient();
                Device d = new Device(id(context));
                client.postJson(d.toJSONString());
            } catch (IOException e) {
                new File(context.getFilesDir(), INSTALLATION).delete();
                throw new RegistrationException(e);
            }
        }
    }

    public static File getInstallationFile(Context context) {
        return new File(context.getFilesDir(), INSTALLATION);
    }

    public static boolean installationFileExists(Context context) {
        File installation = getInstallationFile(context);
        return installation.exists();
    }


    public synchronized static String id(Context context) {
        if (sID == null) {  
            File installation = getInstallationFile(context);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        try {
            f.readFully(bytes);
        } finally {
            f.close();
        }
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        try {
            String id = UUID.randomUUID().toString();
            out.write(id.getBytes());
        } finally {
            out.close();
        }
    }
}