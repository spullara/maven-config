package com.sampullara.maven.commands;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * <p/>
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
        Build build = new Build();
        model.setBuild(build);

        Plugin plugin = new Plugin();
        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setArtifactId("maven-compiler-plugin");
        Xpp3Dom configuration = new Xpp3Dom("configuration");
        Xpp3Dom source = new Xpp3Dom("source");
        Xpp3Dom target = new Xpp3Dom("target");
        configuration.addChild(source);
        configuration.addChild(target);
        plugin.setConfiguration(configuration);
        build.addPlugin(plugin);

        writeModel(model);
    }

    public void help() {
        System.err.println("create [groupid] [artifactid] [version]" +
                "\n   Create a new pom.xml in the current directory with the given group id," +
                "\n   artifact id and version.");
    }
}
