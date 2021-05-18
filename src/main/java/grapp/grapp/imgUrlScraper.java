package grapp.grapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class imgUrlScraper {
    private static String imageUrl = "https://i.imgur.com/"; //imgur url to display image
    private static String getImgUrl = "https://api.imgur.com/3/image/"; //imgur url api to find an image
    private static String postImgUrl = "https://api.imgur.com/3/upload"; //imgur url api to post an image
    private static String exceptionImgId = "BUxBHLB";
    private static String clientId = "492e7d659e0645a"; // TODO add to env variables
    private static String errorMsg ="Hubo un fallo";

    /**
     * Search in imgur an image by the img id
     * @param id id of the img to search in https://api.imgur.com/3/image/ 
     * @return string of the id if the img was found, id of error img if it was not found
     */
    public static String searchById(String id) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Client-ID " + clientId);
            JSONObject json = Unirest.get(getImgUrl + id).headers(headers).asJson().getBody().getObject();
            if (json.getBoolean("success"))
                return json.getJSONObject("data").getString("id");
        } catch (UnirestException e) {
            return errorMsg;
        }
        return errorMsg;
    }

    /**
     * Upload in imgur an imgage by the file given
     * @param img
     * @return id of the new img uploaded
     */

    public static String uploadImg(MultipartFile img) {
        try {
            double size = img.getSize() * 0.00000095367432;//Para que de en MB
            if(size >= 5){return errorMsg;}
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Client-ID " + clientId);
            Map<String, Object> fields = new HashMap<>();

            File imgF = convert(img);
            fields.put("image",imgF);
            
            fields.put("type", "file");

            JSONObject json = Unirest.post(postImgUrl).headers(headers).fields(fields).asJson().getBody().getObject();

            imgF.delete(); //delete the temp File

            if (json.getBoolean("success"))
                return json.getJSONObject("data").getString("id");
        } catch (Exception e) {
            return errorMsg;
        }
        return errorMsg;
    }

    /**
     * 
     * @param id id of the photo
     * @return formed URL to display the image
     */
    public static String getImageUrl(String id) {
        if(id.matches(errorMsg)) return imageUrl+exceptionImgId+".jpg";
        return imageUrl+id+".jpg";
    }

    /**
     * Convert org.springframework.web.multipart.MultipartFile given by html form to java.io.File requested by imgur api POST
     * @param file Multipart
     * @return
     * @throws IOException
     */
    private static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
