package org.cnir.commands;

import org.cnir.indexer.Indexer;
import java.util.*;

public class IndexingCommandFactory implements CommandFactory {
    private Indexer indexer;
    private Scanner scanner;
    public IndexingCommandFactory(Scanner scanner, Indexer indexer){
        this.scanner = scanner;
        this.indexer = indexer;
    }

    public Command createCommand(){
        return new IndexingCommand(scanner, indexer);
    }
}
