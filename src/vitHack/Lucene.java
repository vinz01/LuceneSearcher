package vitHack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
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
import java.util.Date;

public class Lucene {

	public String convertLogToText() throws FileNotFoundException, IOException {
		Date date = new Date();
		long time = date.getTime();
		String fileName = "out" + time + ".txt";
		File fout = new File(fileName);
		FileOutputStream fos = new FileOutputStream(fout);
		File file = new File("System.log");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		Scanner sc = new Scanner(file);
		sc.useDelimiter(",|\r\n");
		while (sc.hasNext()) {
			bw.write(sc.next());
		}
		// closing the scanner stream
		sc.close();
		bw.close();

		return fileName;

	}

	public static void main(String[] args) throws IOException, ParseException {

		String fileName = "";
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);

		Directory index = new RAMDirectory();
		// Directory index = FSDirectory.open(new File("index-dir"));
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
		IndexWriter writer = new IndexWriter(index, config);

		Lucene x = new Lucene();
		try {
			fileName = x.convertLogToText();
			System.out.println("File created :" + fileName);
		} catch (Exception e) {
			System.out.println(e);
		}

		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			// Check if there is another line of input
			while (sc.hasNextLine()) {
				String str = sc.nextLine();
				if (str.length() > 16) {
					int n = str.length();
					addDoc(writer, str.substring(16, n), str.substring(0, 16));
				}

			}

		} catch (IOException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}

		sc.close();

		writer.close();
		String querystr = "ASL Sender Statistics";
		Query q = new QueryParser(Version.LUCENE_42, "title", analyzer).parse(querystr);

		// 3. searching
		int hitsPerPage = 10000;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		// 4. display results
		System.out.println("Query string: " + querystr);
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("course_code") + "\t" + d.get("title"));
		}

		// Finally , close reader
	}

	private static void addDoc(IndexWriter w, String title, String courseCode) throws IOException {

		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		// use a string field for course_code because we don't want it tokenized
		doc.add(new StringField("course_code", courseCode, Field.Store.YES));
		w.addDocument(doc);
	}

}
