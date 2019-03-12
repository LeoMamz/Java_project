package reptile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
public class catchHref {
	public static void main(String[] args) throws IOException {
		//创建存储链接表的文件
		File file = new File("D:\\01250510/0618/0809/JuniorⅠ/java/work/HW4/数据/href.txt");
		Writer out = new FileWriter(file);
		//一下的目的是为了模仿人类浏览网站
		String Agent="";
		Random ra = new Random();
		int a = ra.nextInt(2);
		//随机化中断时间
		int timeP = ra.nextInt(2000)+3000;
		//将Agent的选取随机化
		if(a == 0) Agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";
		else Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134";
		String url="https://iask.sina.com.cn/c/79.html";
		Document doc = Jsoup.connect(url).header("user-agent",Agent).timeout(timeP).get();
		Elements links=doc.getElementsByTag("a");
		//通过观察html代码，找到链接的共同点
		for(Element link:links){
			String linkhref=link.attr("href");
			String linktext = link.text();
			if(linkhref.startsWith("/b/3SxK6Cf9IB.html")){
				linktext = "dna检测有用吗";
			}
			//基于链接的长度来筛选链接
			if(linkhref.startsWith("/b/") && linkhref.length() == 19){
				out.write(linktext+"\r\n");
				out.write("   https://iask.sina.com.cn"+linkhref+"\r\n");
			}else if(linkhref.startsWith("/b/") && (linkhref.length() >= 17 || linkhref.length() <= 19)){
//				out.write(linktext + ": https://iask.sina.com.cn"+linkhref+"\r\n");
				out.write(linktext+"\r\n");
				out.write("   https://iask.sina.com.cn"+linkhref+"\r\n");
			}else if(linkhref.startsWith("/key/") && linkhref.length() == 34){
//				out.write(linktext + ": https://iask.sina.com.cn"+linkhref+"\r\n");
				out.write(linktext+"\r\n");
				out.write("   https://iask.sina.com.cn"+linkhref+"\r\n");
			}
		}
		System.out.println("end!");
		out.close();
//        String title = doc.title();
//        String content = JsoupClean(doc.text());
//        System.out.println("title is: " + title + "\n");
//        System.out.println("content is: " + content);
    }
    public static String JsoupClean(String cont){
        String doc = Jsoup.clean(cont, Whitelist.relaxed());    
        //System.out.println(doc);
        return doc;
     }
	
}
