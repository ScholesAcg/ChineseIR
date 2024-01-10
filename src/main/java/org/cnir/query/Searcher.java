package org.cnir.query;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.cnir.analyzer.CustomAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

public class Searcher {

    private Directory index;
    private String indexPath = "C:\\Users\\pakyi\\Downloads\\Index";
    public Searcher(Directory index) {
        this.index = index;
    }
    public Searcher() {

    }
    public void setPath(String indexPath){
        this.indexPath = indexPath;
    }

    public void searchIndex(String querystr) throws IOException, ParseException {
        Directory index = FSDirectory.open(Paths.get(indexPath));

        QueryParser parser = new QueryParser("content", new CustomAnalyzer());

        Query q = parser.parse(querystr);

        int hitsPerPage = 10;

        try (IndexReader reader = DirectoryReader.open(index)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            System.out.println("Found " + hits.length + " hits.");
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                float score = hits[i].score;
                Document d = searcher.doc(docId);
                String filename = d.get("filename");
                String path = d.get("path");

                System.out.println((i + 1) + ". " + filename + " (Path: " + path + ", Score: " + score + ")");
            }
        }
    }
}
