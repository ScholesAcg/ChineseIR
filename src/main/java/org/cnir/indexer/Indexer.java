package org.cnir.indexer;

import java.io.StringReader;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.cnir.analyzer.CustomAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Indexer {
    private String indexPath = "C:\\Users\\pakyi\\Downloads\\Index";
    private String collectionPath = "C:\\Users\\pakyi\\Downloads\\Collection";
    private Directory index;
    private Analyzer analyzer = new CustomAnalyzer();

    public Indexer(){

    }

    public void setIndexPath(String indexPath){
        this.indexPath = indexPath;
    }
    public void setCollectionPath(String collectionPath) { this.collectionPath = collectionPath; }

    public void IndxerMedthod() throws IOException {
        //index = new RAMDirectory(); <- use temp memory for indexing
        index = FSDirectory.open(Paths.get(indexPath));

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (IndexWriter w = new IndexWriter(index, config)) {
            File folder = new File(collectionPath);
            if (folder.exists() && folder.isDirectory()) {
                for (File file : folder.listFiles()) {
                    if (!file.isDirectory() && file.getName().endsWith(".txt")) {
                        addDoc(w, file);
                    }
                }
            }
        }
        //printIndex();
    }

    private void printIndex() throws IOException {
        try (IndexReader reader = DirectoryReader.open(index)) {
            System.out.println("Total Documents: " + reader.maxDoc());

            for (int i = 0; i < reader.maxDoc(); i++) {
                Document doc = reader.document(i);
                String content = doc.get("content"); // Assuming 'content' is the field to tokenize

                if (content != null) {
                    System.out.println("Document " + i + " tokens:");
                    try (TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content))) {
                        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
                        tokenStream.reset();
                        while (tokenStream.incrementToken()) {
                            System.out.println("\t" + attr.toString());
                        }
                        tokenStream.end();
                    } catch (IOException e) {
                        System.err.println("Error tokenizing document content: " + e.getMessage());
                    }
                } else {
                    System.out.println("No content found in Document " + i);
                }

                System.out.println("----------");
            }
        }
    }



    private void addDoc(IndexWriter w, File file) throws IOException {
        //try (FileReader fr = new FileReader(file)) { <- orphaned code
        try{

            Document doc = new Document();

            //doc.add(new TextField("content", fr)); <- orphaned code

            // Read the content of the file into a String
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);


            // Add the content as a stored TextField
            doc.add(new TextField("content", content, Field.Store.YES));

            // Add path and filename as StringFields
            doc.add(new StringField("path", file.getPath(), Field.Store.YES));
            doc.add(new StringField("filename", file.getName(), Field.Store.YES));

            // Add the document to the IndexWriter
            w.addDocument(doc);

        }catch (Exception err){
            System.err.println(err);
        }
    }



    public Directory getIndex() {
        return index;
    }
}