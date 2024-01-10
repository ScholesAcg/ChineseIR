package org.cnir.commands;

/*
Student:   HO Pak Yin Scholes 22028826d
Version:   0.0.0
Late Edit: 11/1/2024
*/

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

public interface Command {
    //execute commands
    public void execute() throws IOException, ParseException;
}
