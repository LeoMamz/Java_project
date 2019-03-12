package reptile;
import java.awt.TextArea;
import java.io.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
public class LuceneTest {
	public static void main(String[] args) {
		LuceneTest w=new LuceneTest();
		String filePath="D:\\01250510/0618/0809/JuniorⅠ/java/work/HW4/数据/Final_index1";//创建索引的存储目录
		w.createIndex(filePath);//创建索引
	}
	
	public void createIndex(String filePath){
		File f=new File(filePath);
		IndexWriter iwr=null;
		try {
			Directory dir=FSDirectory.open(f);
			Analyzer analyzer = new IKAnalyzer();
			
//			String contents="开门";
	//		printTerms(contents);
			
			IndexWriterConfig conf=new IndexWriterConfig(Version.LUCENE_4_10_0,analyzer);
			iwr=new IndexWriter(dir,conf);//建立IndexWriter。固定套路
//			Document doc=getDocument();
//			iwr.addDocument(doc);//添加doc，Lucene的检索是以document为基本单位
			File Read_File = new File("D:\\01250510/0618/0809/JuniorⅠ/java/work/HW4/数据/QA1.txt");
			FileInputStream fis = new FileInputStream(Read_File);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			int count = 0;
			while((line = br.readLine()) != null){
				String title = "";
				String url = "";
				String text = "";
				Document doc=new Document();
				String count_s = "";
				if(line.equals("Begin Tag**")){
					System.out.print(++count+"\r\n");
					title = br.readLine();
					url = br.readLine();
					int count1 = 1;
					while((line = br.readLine()) != null && !line.equals("End Tag**")){
						text = text + line + "\r\n";
						count_s = String.valueOf(count1++);
						Field f4=new TextField(count_s,line,Field.Store.YES);
						doc.add(f4);
					}
//					if(count == 2) System.out.println(text);
				}
				Field f1=new TextField("title",title,Field.Store.YES);
				Field f2=new TextField("url",url,Field.Store.YES);
				Field f3=new TextField("context",text,Field.Store.YES);
				Field f5=new TextField("conut",count_s,Field.Store.YES);
				doc.add(f1);
				doc.add(f2);
				doc.add(f3);
				doc.add(f5);
				iwr.addDocument(doc);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			iwr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public Document getDocument() throws IOException{
//		//doc中内容由field构成，在检索过程中，Lucene会按照指定的Field依次搜索每个document的该项field是否符合要求。
//		File Read_File = new File("D:\\01250510/0618/0809/JuniorⅠ/java/work/HW4/数据/QA1.txt");
//		FileInputStream fis = new FileInputStream(Read_File);
//		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//		String line = null;
//		Document doc=new Document();
//		while((line = br.readLine()) != null){
//			String title = "";
//			String url = "";
//			String text = "";
//			if(line == "Begin Tag**"){
//				title = line;
//				url = br.readLine();
//				while((line = br.readLine()) != null && line != "End Tag**"){
//					text = text + line;
//				}
//			}
//			doc=new Document();
//			Field f1=new TextField("title",title,Field.Store.YES);
//			Field f2=new TextField("url",url,Field.Store.YES);
//			Field f3=new TextField("context",text,Field.Store.YES);
//			doc.add(f1);
//			doc.add(f2);
//			doc.add(f3);
//			
//		}
////		Document doc=new Document();
////		Field f1=new TextField("name","XXX",Field.Store.YES);
////		Field f2=new TextField("pic","梵高是一名画家",Field.Store.YES);
////		Field f3=new TextField("grade","钱学森的学习成绩十分优秀",Field.Store.YES);
////		Field f4=new TextField("address","浙江大学玉泉校区",Field.Store.YES);
////		Field f5=new StringField("id","大学生",Field.Store.YES);
////		doc.add(f1);
////		doc.add(f2);
////		doc.add(f3);
////		doc.add(f4);
////		doc.add(f5);
//		
//		return doc;
//		
//	}
	
	public void searrh(String filePath){
		File f=new File(filePath);
		try {
			IndexSearcher searcher=new IndexSearcher(DirectoryReader.open(FSDirectory.open(f)));
			String queryStr="XXX";
			Analyzer analyzer = new IKAnalyzer();
			//指定field为“name”，Lucene会按照关键词搜索每个doc中的name。
			@SuppressWarnings("deprecation")
			QueryParser parser = new QueryParser(Version.LUCENE_4_10_0, "name", analyzer);
			
			Query query=parser.parse(queryStr);
			TopDocs hits=searcher.search(query,1);//前面几行代码也是固定套路，使用时直接改field和关键词即可
			for(ScoreDoc doc:hits.scoreDocs){
				Document d=searcher.doc(doc.doc);
				System.out.println(d.get("address"));
				System.out.println(d.get("id"));
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 

}
