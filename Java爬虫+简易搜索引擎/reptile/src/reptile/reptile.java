package reptile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class reptile {
    public static void main(String[] args) throws IOException {
    	File file = new File("D:\\01250510/0618/0809/JuniorⅠ/java/work/HW4/数据/href.txt");
		FileInputStream fis = new FileInputStream(file);
		File fileQA = new File("D:\\01250510/0618/0809/JuniorⅠ/java/work/HW4/数据/QA1.txt");
		@SuppressWarnings("resource")
		Writer out = new FileWriter(fileQA);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        String line1 = null;
        int count = 1;
		while((line = br.readLine()) != null){
			line1 = br.readLine();
			String url=line1;
			out.write("Begin Tag**\r\n");
			out.write("1."+line+"\r\n");
			out.write("2."+line1+"\r\n");
			//将时间额服务器都随机化，以模仿人类浏览
        	String Agent="";
    		Random ra = new Random();
    		int a = ra.nextInt(2);
    		int timeP = ra.nextInt(2000)+3000;
    		if(a == 0) Agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";
    		else Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134";
    		
//    		String url="https://iask.sina.com.cn/b/OjZyeX3JCtR.html";
    		Document doc = Jsoup.connect(url).header("user-agent",Agent).timeout(timeP).get();
    		Elements divs=doc.getElementsByTag("div");
    		out.write("3.");
    		for(Element div:divs){
    			String text = "";
    			String titleclass=div.attr("class");
    			if(titleclass.startsWith("good_answer")||titleclass.startsWith("new-goods-answer")){//好评回答
    				//遇到对应的标签就知道是好评回答
    				text = text +"好评回答:\r\n";
    				Elements answers=div.getElementsByTag("div");
    				for(Element answer:answers){
    					String answer_text=answer.attr("class");
    					if(answer_text.startsWith("answer_text")||answer_text.startsWith("new-answer-text new-answer-cut new-pre-answer-text")){
    						//此处为回答的具体内容
    						String answertext = answer.text();
    						text = text+answertext+"\r\n";
    					}else if(answer_text.startsWith("answer_tip")||answer_text.startsWith("new-user-bar cf")){
    						//此处为回答的tips  也就是时间 和 评价
    						if(answer_text.startsWith("answer_tip")){
    							Elements responders = answer.getElementsByTag("a");
    							for(Element responder:responders){
    								String responder_class=responder.attr("class");
    								if(responder_class.startsWith("blue408")){
    									String respondertext = responder.text();
    									if(respondertext != "" || respondertext != null) 
    										text = text+"回复者:"+respondertext;
    								}
    							}
    							Elements spans = answer.getElementsByTag("span");
    							for(Element span:spans){
    								String span_praise=span.attr("class");
    								String praise = span.text();
    								if(span_praise.startsWith("praise")){
    									if(span_praise.startsWith("praise1 mr15 step")){
    										//此处为回答的点赞数和踩数
    										String cai = span.text();
    										text = text+"  踩:"+cai+"\r\n";
    									}else{
    										//此处为回答的点赞数
    										String zan = span.text();
    										if(zan != "" || zan != null) 
//    											System.out.print("赞： "+zan+" ");
//    											out.write("  赞:"+zan);
    											text = text+"  赞:"+zan;
    									}
    								}else if(span_praise.startsWith("time mr10")){
    									String respondtime = span.text();
//    									System.out.print("回答时间： "+respondtime+"  ");
//    									out.write("  回答时间:"+respondtime);
    									text = text+"  回答时间:"+respondtime;
    								}
    							}
    						}else{
    							Elements responders = answer.getElementsByTag("a");
    							for(Element responder:responders){
    								String responder_class=responder.attr("class");
    								if(responder_class.startsWith("blue408")){
    									//此处为回答的回复者，这是通过观察发现的特异性css的class
    									String respondertext = responder.text();
    									if(respondertext != "" || respondertext != null) 
//    										System.out.println("回复者： "+respondertext+"  ");
//    										out.write("5."+"回复者:"+respondertext);
    										text = text+"回复者:"+respondertext;
    								}else if(responder_class.startsWith("time")){
    									String respondertext = responder.text();
    									if(respondertext != "" || respondertext != null) 
//    										System.out.print("回答时间： "+respondertext+"  ");
//    										out.write("  回答时间:"+respondertext);
    										text = text+"  回答时间:"+respondertext;
    								}
    							}
    							Elements spans = answer.getElementsByTag("a");
    							for(Element span:spans){
    								String span_praise=span.attr("class");
    								String praise = span.text();
    								if(span_praise.startsWith("step")){
    									//此处为回答的踩数量
//    									System.out.println("踩： "+praise);
//    									out.write("  踩:"+praise);
    									text = text+"  踩:"+praise+"\r\n";
    								}else if(span_praise.startsWith("praise")){
    									//此处为回答的点赞数
    									if(praise != "" || praise != null) 
//    										System.out.print("赞： "+praise+" ");
//    										out.write("  赞:"+praise);
    										text = text+"  赞:"+praise;
    								}
    							}
//    							out.write("\r\n");
    							text = text+"\r\n";
    						}
    					}
    				}
//    				System.out.println("");
    				
    			}else if(titleclass.startsWith("answer_list")){//其他回答
    				//一下全是其他回答
    				Elements answer_infos=div.getElementsByTag("div");
    				for(Element answer_info:answer_infos){
    					String answer_infos_class=answer_info.attr("class");
    					if(answer_infos_class.startsWith("answer-info")){
    						//此处为回答的具体内容
//    						System.out.println("其他回答： ");
//    						out.write("6."+"其他回答:"+"\r\n");
    						text = text+"其他回答:"+"\r\n";
    						Elements answers=answer_info.getElementsByTag("div");
    						for(Element answer:answers){
    							String classes=answer.attr("class");
    							if(classes.startsWith("answer_text")){
    								//此处为回答的具体内容
    								String answertext = answer.text();
//    								System.out.println("回答内容： "+answertext);
//    								out.write("7."+"回答内容:"+answertext+"\r\n");
    								text = text+"回答内容:"+answertext+"\r\n";
    							}else if(classes.startsWith("answer_tj")){
    								Elements responders = answer.getElementsByTag("a");
    								for(Element responder:responders){
    									String responder_class=responder.attr("class");
    									if(responder_class.startsWith("author_name")){
    										//此处为回答的回复者
    										String respondertext = responder.text();
    										if(respondertext != "" || respondertext != null) {
//    											System.out.println("回复者： "+respondertext+"  ");
//    											out.write("8."+"回复者:"+respondertext);
    											text = text+"回复者:"+respondertext;
    											break;
    										}
    									}
    								}
    								Elements spans = answer.getElementsByTag("span");
    								for(Element span:spans){
    									String span_praise=span.attr("class");
    									if(span_praise.startsWith("praise")){
    										//此处为回答的评价  如点赞和踩
    										if(span_praise.startsWith("praise1 mr15 step")){
    											String cai = span.text();
//    											System.out.println("踩： "+cai);
//    											out.write("  踩:"+cai);
    											text = text+"  踩:"+cai+"\r\n";
    										}else{
    											String zan = span.text();
    											if(zan != "" || zan != null) 
//    												System.out.print("赞： "+zan+" ");
//    												out.write("  赞:"+zan);
    												text = text+"  赞:"+zan;
    										}
    									}else if(span_praise.startsWith("answer_t")){
    										String respondtime = span.text();
//    										System.out.print("回答时间： "+respondtime+"  ");
//    										out.write("  回答时间:"+respondtime);
    										text = text+"  回答时间:"+respondtime;
    									}
    								}
//    								out.write("\r\n");
    								text = text+"\r\n";
    							}
    						}
    					}
    				}
    			}
    			if(text != "") out.write(text);
    		}
    		out.write("End Tag**\r\n");
    		System.out.print(count++);
        }
    }

    public static String JsoupClean(String cont){
        String doc = Jsoup.clean(cont, Whitelist.relaxed());    
        //System.out.println(doc);
        return doc;
     }
}
