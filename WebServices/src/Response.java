import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    /**
     * 输出文本信息
     * @param text
     * @throws IOException
     */
    public void writeText(byte[] text, String status, String type) {
        FileInputStream fis = null;
        try {
            String line = "";
            line = "HTTP/1.1 " + status + " OK\n";
            output.write(line.getBytes());
            if(!type.equals("jpg")){
//                output.write(("Content-Encoding: gzip\n").getBytes());
            }
            if(type.equals("html")) type = "text/html";
            else if(type.equals("js")) type = "application/javascript";
            else if(type.equals("css")) type = "text/css";
            else if(type.equals("jpg")) type = "image/jpeg";
            else if(type.equals("php")) {
                type = "text/html";
            }

            output.write(("Accept-Ranges: bytes\n").getBytes());
            output.write(("Content-Type: "+type+"; charset=UTF-8\n\n").getBytes());
//            output.write(("X-Frame-Options: SAMEORIGIN\n\n").getBytes());
            if(text != null) output.write(text);
//            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}