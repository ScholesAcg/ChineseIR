package org.cnir.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class AnalyzerTest {
    public static void main(String[] args) throws IOException {
        testCustomAnalyzer();
    }

    public static void testCustomAnalyzer() throws IOException {
        Analyzer analyzer = new CustomAnalyzer();
        try (TokenStream stream = analyzer.tokenStream("field", new StringReader("香港你好"))) {
            CharTermAttribute termAttr = stream.addAttribute(CharTermAttribute.class);
            stream.reset();
            while (stream.incrementToken()) {
                System.out.println(termAttr.toString());
            }
            stream.end();
        }
    }

}

