import java.io.InputStream;

/**
 * Request Parser
 * @author Louis
 */
public class RequestParser {
    private final static int BUFFER_SIZE = 1024;

    /**
     * 解析请求
     * @param inputStream
     * @return Request
     */
    public Request parse(InputStream inputStream) {
        Request request = new Request();
        // 读取请求信息
        String requestMessage = readRequestMessage(inputStream);
//        System.out.println(requestMessage);
        // 解析请求方式
        String type = parseType(requestMessage);
        request.setType(type);
        if(type.equals("POST")){
//            System.out.println("这个是POST请求！！！！！！！！！");
            request.postData = parsePostBody(requestMessage, request);
//            System.out.println(parsePostBody(requestMessage));
        }
        // 解析请求类型
        String uri = parseUri(requestMessage);
        request.setUri(uri);
        String contentType = "";
        String [] contentTypeArray = uri.split("\\.");
        if(uri.indexOf('.') != -1) contentType = contentTypeArray[contentTypeArray.length-1];
        request.setcontentType(contentType);
        return request;
    }

    /**
     * 读取请求信息
     * @param input
     * @return
     */
    private String readRequestMessage(InputStream input) {
        StringBuffer requestMessage = new StringBuffer();
        int readLength = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            readLength = input.read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            readLength = -1;
        }
        for(int i = 0; i < readLength; i++) {
            requestMessage.append((char) buffer[i]);
        }
        return requestMessage.toString();
    }

    /**
     * 解析请求方式
     * @param requestString
     * @return
     */
    private String parseType(String requestString) {
        int index = 0;
        index = requestString.indexOf(' ');
        if (index != -1) {
            return requestString.substring(0, index);
        }
        return null;
    }

    /**
     * 解析请求类型
     * @param requestString
     * @return
     */
    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);
        }
        return null;
    }

    private String parsePostBody(String requestString, Request request) {
        int index1, index2;
        String [] requestArray = requestString.split("\\\r\n\r\n");

//        for(int i = 0; i < requestArray.length; i++){
//            System.out.println("有没有得到poatdata");
//            System.out.println(requestArray.length);
//            System.out.println(requestArray[i]);
//        }

        if(requestArray.length >= 2){
            String [] parArray = requestArray[1].split("\\&");
            for(int i = 0; i < parArray.length; i++){
                request.setpostDataArray(parArray[i]);
            }
            index1 = requestArray[1].indexOf('=');
            if(index1 != -1){
                index2 = requestArray[1].indexOf('&');
                if(index2 > index1) return requestArray[1].substring(index1 + 1, index2);
            }
        }
        return null;
    }

}