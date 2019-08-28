package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Command {

    public static int send(String param) throws IOException {
        String[] params = param.split(" ");
        URL url = new URL(Utils.getURL() + "/handle?" + params[0].substring(1) +"="+params[1]);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        InputStream is = http.getInputStream();
        try {
            byte[] buf = requestBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);

            Gson gson = new GsonBuilder().create();
            User[] list = new User[1];
            if (params[1].equals("all")){
                list = gson.fromJson(strBuf, User[].class);
            } else{
                list[0] = gson.fromJson(strBuf, User.class);
            }

            if (list != null) {
                for (User u : list) {
                    System.out.println(u);
                }
            }
            return http.getResponseCode();
        } finally {
            is.close();
        }

    }

    private static byte[] requestBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }


}
