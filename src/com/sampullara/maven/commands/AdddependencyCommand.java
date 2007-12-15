package com.sampullara.maven.commands;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: samp
 * Date: Jun 24, 2007
 * Time: 10:01:06 AM
 */
public class AdddependencyCommand extends Command {

    // Temporarily use a static repository
    private static String REPO_URL = "http://mirrors.ibiblio.org/pub/mirrors/maven2/";

    public void execute(String[] args) {
        Model model = readModel();
        if (model == null) {
            return;
        }
        String url = REPO_URL;
        switch (args.length) {
            case 2:
                // List all the group ids available in the maven repositories we have in the settings.
                System.err.println("Available group ids:");
                getOptions(url);
                break;
            case 3: {
                // List all the artifact ids for the group id available in the maven repositories
                // we have in the settings.
                String groupid = args[2];
                System.err.println("Available artifact ids for group: " + groupid);
                url += "/" + groupid.replace(".", "/");
                getOptions(url);
                break;
            }
            case 4: {
                String groupid = args[2];
                String artifactid = args[3];
                // List all the versions for this group id and artifact id in the repositories.
                System.err.println("Available versions for group id: " + groupid + ", artifact id: " + artifactid);
                url += "/" + groupid.replace(".", "/") + "/" + artifactid;
                getOptions(url);
                break;
            }
            case 5: {
                // Add the dependency
                String groupid = args[2];
                String artifactid = args[3];
                String version = args[4];
                System.err.println("Asserting dependency on " + groupid + ":" + artifactid + ":" + version);
                Dependency dependency = new Dependency();
                dependency.setGroupId(groupid);
                dependency.setArtifactId(artifactid);
                dependency.setVersion(version);
                List<Dependency> dependencies = model.getDependencies();
                boolean found = false;
                for (Dependency current : dependencies) {
                    if (current.getGroupId().equals(dependency.getGroupId()) &&
                            current.getArtifactId().equals(dependency.getArtifactId())) {
                        System.err.println("Replacing current dependency on version: " + current.getVersion());
                        current.setVersion(version);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.err.println("Adding dependency on " + groupid + ":" + artifactid + ":" + version);
                    model.addDependency(dependency);
                }
                writeModel(model);
            }
            return;
            default:
                System.err.println("Invalid number of arguments: " + args.length);
                help();
                return;
        }
    }

    private void getOptions(String urlString) {
        try {
            URL url = new URL(urlString);
            InputStream is = (InputStream) url.getContent();
            Tidy tidy = new Tidy();
            OutputStream os = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(os);
            tidy.setErrout(pw);
            Document d = tidy.parseDOM(is, os);
            NodeList folders = d.getElementsByTagName("a");
            for (int i = 0; i < folders.getLength(); i++) {
                Node folder = folders.item(i);
                NamedNodeMap attributes = folder.getAttributes();
                Node hrefNode = attributes.getNamedItem("href");
                if (hrefNode != null) {
                    String folderName = hrefNode.getNodeValue();
                    if (folderName.endsWith("/") && folderName.equals(folder.getFirstChild().getNodeValue())) {
                        System.err.println("  " + folderName.substring(0, folderName.length() - 1));
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.err.println("Repository URL invalid: " + e);
            return;
        } catch (IOException e) {
            System.err.println("Repository not available: " + e);
            return;
        }
    }

    public void help() {
        System.err.println("add dependency [group id] [artifact id] [version]");
        System.err.println("  Adds a default jar dependency.  You can use set dependency to change\n" +
                "  the default options for type, scope, and optional.");
    }
}
