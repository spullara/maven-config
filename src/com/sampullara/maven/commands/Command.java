package com.sampullara.maven.commands;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.model.Model;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by IntelliJ IDEA.
 *
 * User: samp
 * Date: Jun 23, 2007
 * Time: 4:35:20 PM
 */
public abstract class Command {

    public static Command getCommand(String commandName) {
        try {
            if (commandName.length() < 2) {
                return null;
            }
            commandName = commandName.substring(0, 1).toUpperCase() + commandName.substring(1);
            Class clazz = Class.forName("com.sampullara.maven.commands." + commandName + "Command");
            return (Command) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }

    protected Model readModel() {
        File file = new File("pom.xml");
        if (!file.exists()) {
            System.err.println("There is no pom.xml in this directory");
            return null;
        }
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
        try {
            FileReader fr = new FileReader(file);
            model = reader.read(fr);
            fr.close();
        } catch (IOException e) {
            System.err.println("Failed to read pom.xml");
            return null;
        } catch (XmlPullParserException e) {
            System.err.println("Failed to parse pom.xml");
            return null;
        }
        return model;
    }

    protected void writeModel(Model model) {
        File file = new File("pom.xml");
        MavenXpp3Writer writer = new MavenXpp3Writer();
        try {
            FileWriter fw = new FileWriter(file);
            writer.write(fw, model);
            fw.close();
        } catch (IOException e) {
            System.err.println("Create failed: " + e);
        }
    }

    public abstract void execute(String[] args);

    public abstract void help();
}
