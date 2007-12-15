package com.sampullara.maven.commands;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: samp
 * Date: Jun 24, 2007
 * Time: 9:56:51 AM
 */
public class AddCommand extends NestedCommand {

    public void help() {
        System.err.println("add [type]     add a new stanza of the following type");
        System.err.println("  dependency   add a new dependency to this project");
    }
}
