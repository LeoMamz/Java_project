package reptile;
import java.io.*;
import java.util.Scanner;

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
public class Search_demo {
	public static void main(String[] args) {
		Search_demo w=new Search_demo();
		String filePath="D:\\01250510/0618/0809/Junior��/java/work/HW4/����/Final_index1";//���������Ĵ洢Ŀ¼
		System.out.println("��ӭ����mamz�����ʴ�����ϵͳ��");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("��ѡ�����������1.title 2.url 3.context");
		int choice = sc.nextInt();
		while(choice<1 || choice>3){
			System.out.println("��ѡ����ȷ�ļ���������1.title 2.url 3.context");
			choice = sc.nextInt();
		}
		System.out.println("������Ҫ���������ݣ�");
		String inP = sc.next();
		while(true){
			if(inP.equals("exit") || inP.equals("EXIT")) {
				System.out.println("GOOD BYE!!!");
				break;
			}
			w.searrh(filePath, inP, choice);
//			inP = "abcd\r\ndfdf";
//			System.out.println(inP);
			System.out.println("��ѡ�����������1.title 2.url 3.context");
			choice = sc.nextInt();
			while(choice<1 || choice>3){
				System.out.println("��ѡ����ȷ�ļ���������1.title 2.url 3.context");
				choice = sc.nextInt();
			}
			System.out.println("������Ҫ���������ݣ�");
			inP = sc.next();
		}
	}
	
	public void searrh(String filePath, String inP, int choice){
		File f=new File(filePath);
		try {
			IndexSearcher searcher=new IndexSearcher(DirectoryReader.open(FSDirectory.open(f)));
			String queryStr=inP;
			Analyzer analyzer = new IKAnalyzer();
			String condition = "title";
			//ָ��fieldΪ��name����Lucene�ᰴ�չؼ�������ÿ��doc�е�name��
			if(choice == 1) condition = "title";
			else if(choice == 2) condition = "url";
			else if(choice == 3) condition = "context";
			@SuppressWarnings("deprecation")
			QueryParser parser = new QueryParser(Version.LUCENE_4_10_0, condition, analyzer);
			Query query=parser.parse(queryStr);
			TopDocs hits=searcher.search(query,5);//ǰ�漸�д���Ҳ�ǹ̶���·��ʹ��ʱֱ�Ӹ�field�͹ؼ��ʼ���
			for(ScoreDoc doc:hits.scoreDocs){
				Document d=searcher.doc(doc.doc);
				String a = d.get("title");
				String b = d.get("url");
				String c = d.get("context");
				String x[] = d.getValues("context");
				System.out.println(a);
				System.out.println(b);
				System.out.println(c);
				if(a.equals("") || b.equals("")) System.out.println("δ��������ƥ�����Ϣ��");
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

