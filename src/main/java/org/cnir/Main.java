package org.cnir;

import org.cnir.commands.*;
import org.cnir.indexer.Indexer;
import org.cnir.query.Searcher;

import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Indexer indexer = new Indexer();
    private static Searcher searcher = new Searcher();

    public static void main(String[] args) throws Exception {
        HashMap<String, CommandFactory> factory = new HashMap<>();
        factory.put("i", new IndexingCommandFactory(scanner, indexer));
        factory.put("s", new SearchingCommandFactory(scanner, searcher));
        factory.put("x", new ExitCommandFactory());

        String command;
        while (true) {
            try{
                System.out.println(
                        "Chinese Searching System \n"+
                        "Please enter command: [i | s | x] \n"+
                        "i = indexing, s = searching, x = exit system \n"
                );
                command = scanner.next();
                Command com = factory.get(command).createCommand();
                com.execute();
            }catch(Exception e){
                System.out.println("wrong input!");
            }

        }

    }

}
