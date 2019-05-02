import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class tests {
    public static void main(String[] args){
        String a = "GET http://mamz.xyz/demo/production/login.html HTTP/1.1\n";
        String b = "/demo/production/login.html";
        int index1, index2;
        String [] c = b.split("\\.");
        index1 = a.indexOf(" ");
        index2 = a.indexOf(" ", index1+1);
        System.out.println(a.substring(index1+1, index2));
//        System.out.println(b.substring(b.indexOf(".")));
        for(int i = 0; i < c.length; i++){
            System.out.println(c[i]);
        }
        String uri = "a.b.c.b.g.v.html";
        String contentType = "";
        String [] contentTypeArray = uri.split("\\.");
        if(uri.indexOf('.') != -1) contentType = contentTypeArray[contentTypeArray.length-1];
        System.out.println(contentType);
        if("html" == contentType) System.out.println("sdfkljhjsdklfjsdlkjflkLKfkldsklf kdls");
        if("html".equals(contentType) ) System.out.println("sdfkljhjsdklfjsdlkjflkLKfkldsklf kdls");

        String request = "POST http://mamz.xyz/web/login.php HTTP/1.1\n" +
                "Host: mamz.xyz\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 41\n" +
                "Origin: http://mamz.xyz\n" +
                "X-Requested-With: XMLHttpRequest\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36\n" +
                "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\n" +
                "Accept: */*\n" +
                "Referer: http://mamz.xyz/web/index.html\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Accept-Language: zh-CN,zh;q=0.9\n" +
                "\n" +
                "loginName=asdf&password=13432&ip=&ipFrom=";
        String [] requestArray = request.split("\\\n\n");
        System.out.println("开始了！");
        for(int i = 0; i < requestArray.length; i++){
            System.out.println(requestArray[1]);
            System.out.println(requestArray.length);
        }
        if(requestArray.length >= 2){
            index1 = requestArray[1].indexOf('=');
            if(index1 != -1){
                index2 = requestArray[1].indexOf('&');
                if(index2 > index1) System.out.println(requestArray[1].substring(index1 + 1, index2));
            }
        }
        String x = "d";
        if(x == null) System.out.println("结束了！");
        else System.out.println("了！");

        String path = "./web/js/ext-all.js";
        path = "./web/js/a.txt";
        String text = "";
        try{
            InputStream is = new FileInputStream(path);
            byte[] context = new byte[10240];
            byte[] divide = new byte[5];
            String judge = "";
            int i = 0, index = 0, j = 0;
            while((i = is.read()) != -1){
                context[index] = (byte) i;
                divide[0] = (byte) i;
                judge = new String(divide);
//                System.out.println(new String(divide)+"dfsdfsdfds");
                index++;
                if(judge.equals(";") || judge.equals("{") || judge.equals("}")){
                    text = text + new String(context) + "/\n";
                    context = new byte[10240];
                    index = 0;
                }
                if(index == 10240){
                    text = text + new String(context);
                    context = new byte[10240];
                    index = 0;
                }
            }
            text = text + new String(context);
            System.out.println();
            System.out.print(text);
            is.close();
        }catch (FileNotFoundException e){
            System.out.println("没有找到路径");
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

