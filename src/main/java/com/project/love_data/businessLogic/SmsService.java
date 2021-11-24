package com.project.love_data.businessLogic;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Random;


@Log4j2
@AllArgsConstructor
@Service
public class SmsService {
    private final static String apiUrl = "https://sslsms.cafe24.com/sms_sender.php";
    private final static String userAgent = "Mozilla/5.0";
    private final static String charset = "UTF-8";
    private final static boolean isTest = true;

    public String RandomNum(){
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수
        for(int i=0;i<6;i++) {
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        return numStr;
    }

    public void sendSMS(String data,String num){

        try{
            URL obj = new URL(apiUrl);
            HttpsURLConnection con= (HttpsURLConnection) obj.openConnection();
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept-Charset", charset);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", userAgent);

            String postParams = "user_id="+base64Encode("silentsns97")
                    +"&secure="+base64Encode("1d1e0e0bdf80e01761871ec94d8f8d62")
                    +"&msg="+base64Encode("[Lovedata]\n인증번호는 '"+num+"'입니다.")
                    +"&sphone1="+base64Encode("010")
                    +"&sphone2="+base64Encode("3014")
                    +"&sphone3="+base64Encode("1437")
                    +"&rphone="+base64Encode(data)
                    +"&mode="+base64Encode("1")
                    +"&smsType=S";
//                    +"&rtime"+base64Encode(""); // SMS/LMS 여부


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

    public void managersendSMS(String data, String text){

        try{
            URL obj = new URL(apiUrl);
            HttpsURLConnection con= (HttpsURLConnection) obj.openConnection();
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept-Charset", charset);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", userAgent);

            String postParams = "user_id="+base64Encode("silentsns97")
                    +"&secure="+base64Encode("1d1e0e0bdf80e01761871ec94d8f8d62")
                    +"&msg="+base64Encode(text)
                    +"&sphone1="+base64Encode("010")
                    +"&sphone2="+base64Encode("3014")
                    +"&sphone3="+base64Encode("1437")
                    +"&rphone="+base64Encode(data)
                    +"&mode="+base64Encode("1")
                    +"&smsType=S";
//                    +"&rtime"+base64Encode(""); // SMS/LMS 여부


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
    // base64Encode
    public static String base64Encode(String str)  throws java.io.IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] strByte = str.getBytes("UTF-8");
        String result = encoder.encodeToString(strByte);
        return result ;
    }
}
