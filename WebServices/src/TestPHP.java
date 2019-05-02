
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by HONGLINCHEN on 2018/3/2 11:33
 * java调用PHP
 * @author HONGLINCHEN
 * @since JDK 1.8
 */
public class TestPHP {
    public static void main(String args[]) throws Exception {
        String[] x = "ip=".split("\\=");
        System.out.println(x.length);



        String name = "I am so handsome!!!!";
        String commandB = "$_POST['loginName'] = '"+ name + "';";
        String commandF = "D:\\01250510\\0618\\jsp\\workspace\\WebServices\\web\\login.php";
        ProcessBuilder pb = new ProcessBuilder(
                "D:\\wamp64\\bin\\php\\php7.0.10\\php.exe", "-B", commandB, "-F", "./web/login.php");
//        ProcessBuilder pba = new ProcessBuilder("","", "");
        Process p = pb.start();
        OutputStream out = p.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out);
        String outStr = "\r\n";
        writer.write(outStr);
        writer.flush();
        writer.close();
        InputStream in = p.getInputStream();
        byte[] buff =  new byte[1024];
        in.read(buff);
//        InputStreamReader reader = new InputStreamReader(in);
//        char[] buff = new char[1024];
//        reader.read(buff);
        System.out.println(new String(buff));//打印出!!!!!
    }
}
