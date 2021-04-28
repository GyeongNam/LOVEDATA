package com.project.love_data.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Log4j2
@AllArgsConstructor
@Service
public class SmsService {
    private final static String apiUrl = "https://sslsms.cafe24.com/sms_sender.php";
    private final static String userAgent = "Mozilla/5.0";
    private final static String charset = "UTF-8";
    private final static boolean isTest = true;

    public void sendSMS(String data){

        try{
            URL obj = new URL(apiUrl);
            HttpsURLConnection con= (HttpsURLConnection) obj.openConnection();
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept-Charset", charset);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", userAgent);

            String postParams = "user_id="+base64Encode("silentsns97")
                    +"&secure="+base64Encode("1d1e0e0bdf80e01761871ec94d8f8d62")
                    +"&msg="+base64Encode("[Lovedata]\n인증번호는 ''입니다.")
                    +"&sphone1="+base64Encode("010")
                    +"&sphone2="+base64Encode("3014")
                    +"&sphone3="+base64Encode("1437")
                    +"&rphone="+base64Encode(data)
                    +"&mode="+base64Encode("1")
                    +"&smsType=S"
                    +"&rtime"+base64Encode(""); // SMS/LMS 여부


            //For POST only    - START
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(postParams.getBytes());
            os.flush();
            os.close();

            //For POST only - END
            int responseCode = con.getResponseCode();
            log.info("POST Response Code::"+responseCode);

            if(responseCode == HttpURLConnection.HTTP_OK){ // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer buf  = new StringBuffer();
                while((inputLine=in.readLine())!=null){
                    buf.append(inputLine);
                }
                in.close();
                log.info("SMS Content : "+buf.toString());
            }else{
                log.error("POST request not worked");
            }
        }catch(IOException ex){
            log.error("SMS IOException:"+ex.getMessage());
        }
    }

    public static String base64Encode(String str)  throws java.io.IOException {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        byte[] strByte = str.getBytes();
        String result = encoder.encode(strByte);
        return result ;

    }
}
