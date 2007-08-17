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

        Plugin compilerPlugin = new Plugin();
        compilerPlugin.setGroupId("org.apache.maven.plugins");
        compilerPlugin.setArtifactId("maven-compiler-compilerPlugin");
        Xpp3Dom compilerConf = new Xpp3Dom("compilerConf");
        Xpp3Dom source = new Xpp3Dom("source");
        Xpp3Dom target = new Xpp3Dom("target");
        compilerConf.addChild(source);
        compilerConf.addChild(target);
        compilerPlugin.setConfiguration(compilerConf);
        build.addPlugin(compilerPlugin);

        Plugin assemblyPlugin = new Plugin();
        assemblyPlugin.setArtifactId("maven-assembly-plugin");
        Xpp3Dom assemblyConf = new Xpp3Dom("assemblyConf");
        Xpp3Dom descriptorRefs = new Xpp3Dom("descriptorRefs");
        Xpp3Dom descriptorRef = new Xpp3Dom("descriptorRef");
        descriptorRefs.addChild(descriptorRef);
        assemblyConf.addChild(descriptorRefs);
        assemblyPlugin.setConfiguration(assemblyConf);
        build.addPlugin(assemblyPlugin);

        writeModel(model);
    }

    public void help() {
        System.err.println("create [groupid] [artifactid] [version]" +
                "\n   Create a new pom.xml in the current directory with the given group id," +
                "\n   artifact id and version.");
    }
}
