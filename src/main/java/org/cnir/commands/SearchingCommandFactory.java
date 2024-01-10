package org.cnir.commands;

import org.cnir.query.Searcher;

import java.util.*;

public class SearchingCommandFactory implements CommandFactory {
    private Searcher searcher;
    private Scanner scanner;
    public SearchingCommandFactory(Scanner scanner, Searcher searcher){
        this.scanner = scanner;
        this.searcher = searcher;
    }

    public Command createCommand(){
        return new SearchingCommand(scanner, searcher);
    }
}

