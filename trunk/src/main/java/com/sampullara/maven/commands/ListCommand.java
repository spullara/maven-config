package com.sampullara.maven.commands;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: samp
 * Date: Jun 24, 2007
 * Time: 11:00:56 AM
 */
public class ListCommand extends NestedCommand {

    public void help() {
        System.err.println("list [type]     add a new stanza of the following type");
        System.err.println("  dependencies  external dependencies of this project");
    }
}
