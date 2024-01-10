package org.cnir.commands;

import org.cnir.indexer.Indexer;

import java.io.IOException;
import java.util.*;

public class IndexingCommand implements Command{
    private String next;
    private Indexer indexer;
    private Scanner scanner;

    public IndexingCommand(Scanner scanner, Indexer indexer){
        this.scanner = scanner;
        this.indexer = indexer;
    }
    public void execute() throws IOException {
        System.out.println("Enter index path for indexing [Accepted Format: C:/XXX/XXX || C:\\XXX\\XXX]");
        next = scanner.next();
        indexer.setIndexPath(next);
        System.out.println("Enter collection path [Accepted Format: C:/XXX/XXX || C:\\XXX\\XXX]");
        next = scanner.next();
        indexer.setCollectionPath(next);
        indexer.IndxerMedthod();
    }
}
