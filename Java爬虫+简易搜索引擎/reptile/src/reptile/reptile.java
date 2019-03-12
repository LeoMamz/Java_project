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
    	File file = new File("D:\\01250510/0618/0809/Junior��/java/work/HW4/����/href.txt");
		FileInputStream fis = new FileInputStream(file);
		File fileQA = new File("D:\\01250510/0618/0809/Junior��/java/work/HW4/����/QA1.txt");
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
			//��ʱ�������������������ģ���������
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
    			if(titleclass.startsWith("good_answer")||titleclass.startsWith("new-goods-answer")){//�����ش�
    				//������Ӧ�ı�ǩ��֪���Ǻ����ش�
    				text = text +"�����ش�:\r\n";
    				Elements answers=div.getElementsByTag("div");
    				for(Element answer:answers){
    					String answer_text=answer.attr("class");
    					if(answer_text.startsWith("answer_text")||answer_text.startsWith("new-answer-text new-answer-cut new-pre-answer-text")){
    						//�˴�Ϊ�ش�ľ�������
    						String answertext = answer.text();
    						text = text+answertext+"\r\n";
    					}else if(answer_text.startsWith("answer_tip")||answer_text.startsWith("new-user-bar cf")){
    						//�˴�Ϊ�ش��tips  Ҳ����ʱ�� �� ����
    						if(answer_text.startsWith("answer_tip")){
    							Elements responders = answer.getElementsByTag("a");
    							for(Element responder:responders){
    								String responder_class=responder.attr("class");
    								if(responder_class.startsWith("blue408")){
    									String respondertext = responder.text();
    									if(respondertext != "" || respondertext != null) 
    										text = text+"�ظ���:"+respondertext;
    								}
    							}
    							Elements spans = answer.getElementsByTag("span");
    							for(Element span:spans){
    								String span_praise=span.attr("class");
    								String praise = span.text();
    								if(span_praise.startsWith("praise")){
    									if(span_praise.startsWith("praise1 mr15 step")){
    										//�˴�Ϊ�ش�ĵ������Ͳ���
    										String cai = span.text();
    										text = text+"  ��:"+cai+"\r\n";
    									}else{
    										//�˴�Ϊ�ش�ĵ�����
    										String zan = span.text();
    										if(zan != "" || zan != null) 
//    											System.out.print("�ޣ� "+zan+" ");
//    											out.write("  ��:"+zan);
    											text = text+"  ��:"+zan;
    									}
    								}else if(span_praise.startsWith("time mr10")){
    									String respondtime = span.text();
//    									System.out.print("�ش�ʱ�䣺 "+respondtime+"  ");
//    									out.write("  �ش�ʱ��:"+respondtime);
    									text = text+"  �ش�ʱ��:"+respondtime;
    								}
    							}
    						}else{
    							Elements responders = answer.getElementsByTag("a");
    							for(Element responder:responders){
    								String responder_class=responder.attr("class");
    								if(responder_class.startsWith("blue408")){
    									//�˴�Ϊ�ش�Ļظ��ߣ�����ͨ���۲췢�ֵ�������css��class
    									String respondertext = responder.text();
    									if(respondertext != "" || respondertext != null) 
//    										System.out.println("�ظ��ߣ� "+respondertext+"  ");
//    										out.write("5."+"�ظ���:"+respondertext);
    										text = text+"�ظ���:"+respondertext;
    								}else if(responder_class.startsWith("time")){
    									String respondertext = responder.text();
    									if(respondertext != "" || respondertext != null) 
//    										System.out.print("�ش�ʱ�䣺 "+respondertext+"  ");
//    										out.write("  �ش�ʱ��:"+respondertext);
    										text = text+"  �ش�ʱ��:"+respondertext;
    								}
    							}
    							Elements spans = answer.getElementsByTag("a");
    							for(Element span:spans){
    								String span_praise=span.attr("class");
    								String praise = span.text();
    								if(span_praise.startsWith("step")){
    									//�˴�Ϊ�ش�Ĳ�����
//    									System.out.println("�ȣ� "+praise);
//    									out.write("  ��:"+praise);
    									text = text+"  ��:"+praise+"\r\n";
    								}else if(span_praise.startsWith("praise")){
    									//�˴�Ϊ�ش�ĵ�����
    									if(praise != "" || praise != null) 
//    										System.out.print("�ޣ� "+praise+" ");
//    										out.write("  ��:"+praise);
    										text = text+"  ��:"+praise;
    								}
    							}
//    							out.write("\r\n");
    							text = text+"\r\n";
    						}
    					}
    				}
//    				System.out.println("");
    				
    			}else if(titleclass.startsWith("answer_list")){//�����ش�
    				//һ��ȫ�������ش�
    				Elements answer_infos=div.getElementsByTag("div");
    				for(Element answer_info:answer_infos){
    					String answer_infos_class=answer_info.attr("class");
    					if(answer_infos_class.startsWith("answer-info")){
    						//�˴�Ϊ�ش�ľ�������
//    						System.out.println("�����ش� ");
//    						out.write("6."+"�����ش�:"+"\r\n");
    						text = text+"�����ش�:"+"\r\n";
    						Elements answers=answer_info.getElementsByTag("div");
    						for(Element answer:answers){
    							String classes=answer.attr("class");
    							if(classes.startsWith("answer_text")){
    								//�˴�Ϊ�ش�ľ�������
    								String answertext = answer.text();
//    								System.out.println("�ش����ݣ� "+answertext);
//    								out.write("7."+"�ش�����:"+answertext+"\r\n");
    								text = text+"�ش�����:"+answertext+"\r\n";
    							}else if(classes.startsWith("answer_tj")){
    								Elements responders = answer.getElementsByTag("a");
    								for(Element responder:responders){
    									String responder_class=responder.attr("class");
    									if(responder_class.startsWith("author_name")){
    										//�˴�Ϊ�ش�Ļظ���
    										String respondertext = responder.text();
    										if(respondertext != "" || respondertext != null) {
//    											System.out.println("�ظ��ߣ� "+respondertext+"  ");
//    											out.write("8."+"�ظ���:"+respondertext);
    											text = text+"�ظ���:"+respondertext;
    											break;
    										}
    									}
    								}
    								Elements spans = answer.getElementsByTag("span");
    								for(Element span:spans){
    									String span_praise=span.attr("class");
    									if(span_praise.startsWith("praise")){
    										//�˴�Ϊ�ش������  ����޺Ͳ�
    										if(span_praise.startsWith("praise1 mr15 step")){
    											String cai = span.text();
//    											System.out.println("�ȣ� "+cai);
//    											out.write("  ��:"+cai);
    											text = text+"  ��:"+cai+"\r\n";
    										}else{
    											String zan = span.text();
    											if(zan != "" || zan != null) 
//    												System.out.print("�ޣ� "+zan+" ");
//    												out.write("  ��:"+zan);
    												text = text+"  ��:"+zan;
    										}
    									}else if(span_praise.startsWith("answer_t")){
    										String respondtime = span.text();
//    										System.out.print("�ش�ʱ�䣺 "+respondtime+"  ");
//    										out.write("  �ش�ʱ��:"+respondtime);
    										text = text+"  �ش�ʱ��:"+respondtime;
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
