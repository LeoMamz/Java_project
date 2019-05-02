import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 根据请求类型和URI找到对应的控制器，将请求交给控制器处理
 *
 * @author Louis
 */
public class ServiceDispatcher {

    /**
     * 转发处理请求
     * @param request
     * @param response
     */
    public void dispatcher(Request request, Response response) {
        execController(request, response);
    }

    /**
     * 根据请求类型及URI等请求信息，找到并执行对应的控制器方法后返回
     * 此处直接返回一个控制器，模拟查找和执行控制器方法的过程
     * @param request
     * @param response
     * @return
     */
    private void execController(Request request, Response response) {
        if(request.postData != null || request.getcontentType().equals("php")){
//            response.writeText(("用户名： " + request.postData).getBytes(), "200", request.getcontentType());//直接模仿php返回
            response.writeText(postToPHP(request.postData, request.getUri(), request), "200", request.getcontentType());//运行php程序返回
//            for(int i = 0; i < request.getpostDataArray().size(); i++) System.out.println("postData: "+request.getpostDataArray().get(i));
            return;
        }
        byte[] text = getControllerResult(request, response);
        if(text == null) text = "".getBytes();
        else if(new String(text).equals("404")) {
            response.writeText("没有找到对应路径的文件！".getBytes(), "404", request.getcontentType());
            return;
        }
        // 输出控制器返回结果
        response.writeText(text, "200", request.getcontentType());
    }

    /**
     * 模拟查找和执行控制器方法并返回结果
     * @param request
     * @param response
     * @return
     */
    private byte[] getControllerResult(Request request, Response response) {
//        if(request.getcontentType().equals("php")) return request.postData;
        byte[] context = null;
        String uri = request.getUri();
        String [] uriArray = uri.split("\\/");
        String path = "."+uri;
        File requestFile = new File(path);
        Path path1 = Paths.get(path);
//        text = Files.readAllLines(path1);

        try{
            if(requestFile.exists()){
                context =  Files.readAllBytes(path1);
//            InputStream is = new FileInputStream(path);
//            byte[] context = new byte[1024];
//            int i = 0;
//            int index = 0;
//            while((i = is.read()) != -1){
//                context[index] = (byte) i;
//                index++;
//                if(index == 1024){
//                    text = text + new String(context);
//                    context = new byte[1024];
//                    index = 0;
//                }
//            }
//                System.out.println(new String(context));
//                text = new String(context);
//            is.close();
            }

        }catch (FileNotFoundException e){
            System.out.println("没有找到路径");
            context = "404".getBytes();
//            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return context;
    }

    private byte[] postToPHP(String data, String path, Request request){
        String commandB = "";
        for(int i = 0; i < request.getpostDataArray().size(); i++){
            String[] parameter = request.getpostDataArray().get(i).split("\\=");
            if(parameter.length == 2) commandB = commandB + "$_POST['" + parameter[0] + "'] = '" + parameter[1] + "';";
            if(parameter.length == 1) commandB = commandB + "$_POST['" + parameter[0] + "'] = null;";
        }
        String commandF = "./web" + path;
        byte[] buff =  new byte[1024];
        ProcessBuilder pb = new ProcessBuilder(
                "D:\\wamp64\\bin\\php\\php7.0.10\\php.exe", "-B", commandB, "-F", commandF);

        try{
            Process p = pb.start();
            OutputStream out = p.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(out);
            String outStr = "\r\n";
            writer.write(outStr);
            writer.flush();
            writer.close();
            InputStream in = p.getInputStream();
            int length = in.read(buff);
            byte[] result = Arrays.copyOfRange(buff, 0, length);
            return result;
        }catch (IOException e){
            System.out.println("PHP文件执行出错！");
            e.printStackTrace();
        }
        return null;
    }

}