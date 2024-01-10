package org.cnir.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;

public class CustomAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer source = new CustomTokenizer();
        TokenStream filter = new LowerCaseFilter(source);
        return new TokenStreamComponents(source, filter);
    }
}