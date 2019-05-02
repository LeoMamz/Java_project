import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

public class WebServer {
    private int port = 4793;
    private ServerSocket serverSocket;

    public WebServer(){
        init();
    }

    private void init(){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("mamz的服务器已经开启，等待客户端连接...");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        while (true){
            final Socket socket = serverSocket.accept(); //监听并接受客户端的请求
            new Thread() { //开启新的线程来处理客户端的请求
                public void run(){
                    service(socket);
                }
            }.start();
        }
    }
    private void service(Socket socket) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            // 读取请求信息内容
            Request request = new RequestParser().parse(inputStream);
            Response response = new Response(outputStream);
            service(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("接收到客户端连接, " + socket.getInetAddress() + ":" + socket.getPort());
    }

    /**
     * 处理客户请求, 把请求交给框架派遣服务，类似Spring的DispatcherServlet
     * @param request
     * @param response
     */
    private void service(Request request, Response response) {
        ServiceDispatcher serviceDispatcher = new ServiceDispatcher();
        serviceDispatcher.dispatcher(request, response);
    }

    public static void main(String[] args) {
        try {
            new WebServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

