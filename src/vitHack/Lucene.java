package vitHack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.swing.JFrame;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.jfree.ui.RefineryUtilities;

public class Lucene {

	static int ctr = 0;
	static String fileName = "";

	private static void addToTextFile(IndexWriter w, String title, String courseCode) throws IOException {

		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new StringField("course_code", courseCode, Field.Store.YES));
		w.addDocument(doc);
	}

	public static void main(String[] args) throws IOException, ParseException {

		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
		Directory index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
		IndexWriter writer = new IndexWriter(index, config);
		logToText LogToText = new logToText();
		try {

			fileName = LogToText.convertLogToText();
			System.out.println("Text File created :" + fileName);
		} catch (Exception e) {
			System.out.println(e);
		}

		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			// Check if there is another line of input
			while (sc.hasNextLine()) {
				ctr++;
				String str = sc.nextLine();
				if (str.length() > 16) {
					int n = str.length();
					addToTextFile(writer, str.substring(16, n), str.substring(0, 16));
				}

			}

		} catch (IOException exp) {
			exp.printStackTrace();
		}

		sc.close();

		writer.close();
		// Search keywords/strings to be changed here
		String querystr = "abnormal + error";
		Query q = new QueryParser(Version.LUCENE_42, "title", analyzer).parse(querystr);

		// 3. searching
		int hitsPerPage = 10000;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		// 4. display results
		double result = (double) ((double) hits.length * 100 / (double) ctr);// Error percentage
		System.out.println("Query string: " + querystr);
		System.out.println("Found " + hits.length + " hits.");
		System.out.println("Total size of log file : " + ctr);
		File fout = new File("gui.txt");
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			String s = (i + 1) + ". " + d.get("course_code") + "\t" + d.get("title");
			// System.out.println(s);
			bw.write(s);
			bw.newLine();
		}

		bw.close();

		// write to JText frame (GUI)

		textWriter tw = new textWriter();
		tw.guiConsoleTest(querystr);

		// 5 seconds wait
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println(e);
		}

		// generate pie chart

		createChart cc = new createChart("Pie chart", "Log File analysis", result);
		cc.pack();
		cc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cc.setVisible(true);

		// 5 seconds wait
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println(e);
		}

		// generate graph

		createGraph chart = new createGraph("Graph", "Numer of Logs vs Time");
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);

	}

}
