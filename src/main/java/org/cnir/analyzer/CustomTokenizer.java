package org.cnir.analyzer;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.List;

public class CustomTokenizer extends Tokenizer {
    protected final CharTermAttribute termAttr = addAttribute(CharTermAttribute.class);
    protected final Segment segmenter = HanLP.newSegment();
    protected List<Term> termList;
    protected int currentTermIndex;

    @Override
    public final boolean incrementToken() throws IOException {
        clearAttributes();

        if (termList == null) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1024];
            int length;
            while ((length = input.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, length);
            }
            String text = stringBuilder.toString();
            termList = segmenter.seg(text);
            currentTermIndex = 0;  // Initialize currentTermIndex
        }

        if (currentTermIndex >= termList.size()) {
            return false; // No more tokens
        }

        try {
            Term term = termList.get(currentTermIndex++);
            termAttr.append(term.word);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void end() throws IOException {
        super.end();

    }

    @Override
    public void close() throws IOException {
        super.close();

    }

    @Override
    public void reset() throws IOException {
        super.reset();
        this.currentTermIndex = 0;
        this.termList = null;
    }
}
