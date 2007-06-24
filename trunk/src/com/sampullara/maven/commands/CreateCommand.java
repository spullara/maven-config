package com.sampullara.maven.commands;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * User: samp
 * Date: Jun 23, 2007
 * Time: 5:02:56 PM
 */
public class CreateCommand extends Command {
    public void execute(String[] args) {
        if (args.length != 4) {
            help();
            return;
        }
        File file = new File("pom.xml");
        if (file.exists()) {
            System.err.println("pom.xml already exists in this directory");
            return;
        }

        // Setup the model
        Model model = new Model();
        model.setModelVersion("4.0.0");
        model.setGroupId(args[1]);
        model.setArtifactId(args[2]);
        model.setVersion(args[3]);

        writeModel(model);
    }

    public void help() {
        System.err.println("create [groupid] [artifactid] [version]" +
                "\n   Create a new pom.xml in the current directory with the given group id," +
                "\n   artifact id and version.");
    }
}
