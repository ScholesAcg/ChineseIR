package org.cnir.commands;

import org.apache.lucene.queryparser.classic.ParseException;
import org.cnir.query.Searcher;
import java.io.IOException;
import java.util.*;

public class SearchingCommand implements Command{
    private String next;
    private Searcher searcher;
    private Scanner scanner;

    public SearchingCommand(Scanner scanner, Searcher searcher){
        this.scanner = scanner;
        this.searcher = searcher;
    }
    public void execute() throws IOException, ParseException {
        System.out.println("Enter index path for searching [Accepted Format: C:/XXX/XXX || C:\\XXX\\XXX]");
        next = scanner.next();
        searcher.setPath(next);
        while(!next.equals("q")){
            System.out.println("Enter query string || q for exit");
            next = scanner.next();
            searcher.searchIndex(next);
        }

    }
}
