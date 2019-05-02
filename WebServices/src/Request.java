import java.util.ArrayList;
import java.util.List;

public class Request {
    /**
     * 请求方式： GET\POST\DELETE..
     */
    private String type;
    /**
     * 请求URI
     */
    private String uri;
    private String contentType;
    public String postData = null;
//    private String[] postDataArray = new String[5];
    private List<String> postDataArray = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getcontentType() {
        return contentType;
    }

    public void setcontentType(String contentType) {
        this.contentType = contentType;
    }

    public List<String> getpostDataArray() {
        return postDataArray;
    }

    public void setpostDataArray(String data) {
        if(data != null) this.postDataArray.add(data);
    }
}
