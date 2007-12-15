package com.sampullara.maven.commands;

import org.apache.maven.model.Model;
import org.apache.maven.model.Dependency;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: samp
 * Date: Jun 24, 2007
 * Time: 11:02:25 AM
 */
public class ListdependenciesCommand extends Command {
    public void execute(String[] args) {
        Model model = readModel();
        if (model == null) {
            return;
        }
        String groupid = null;
        String artifactid = null;
        String version = null;
        switch (args.length) {
            case 2: {
                System.err.println("All dependencies for project:");
                break;
            }
            case 3: {
                groupid = args[2];
                System.err.println("Dependencies for group: " + groupid);
                break;
            }
            case 4: {
                groupid = args[2];
                artifactid = args[3];
                System.err.println("Dependencies for group id: " + groupid + ", artifact id: " + artifactid);
                break;
            }
            case 5: {
                groupid = args[2];
                artifactid = args[3];
                version = args[4];
            }
            return;
            default:
                System.err.println("Invalid number of arguments: " + args.length);
                help();
                return;
        }
        // Add the dependency
        List<Dependency> dependencies = model.getDependencies();
        for (Dependency current : dependencies) {
            if (groupid == null) {
                operateOnDependency(model, current);
            } else if (current.getGroupId().equals(groupid)) {
                if (artifactid == null) {
                    operateOnDependency(model, current);
                } else if (current.getArtifactId().equals(artifactid)) {
                    if (version == null) {
                        operateOnDependency(model, current);
                    } else if (current.getVersion().equals(version)) {
                        operateOnDependency(model, current);
                    }
                }
            }
        }
    }

    protected void operateOnDependency(Model model, Dependency current) {
        System.err.println("  " + current.getGroupId() + ":" + current.getArtifactId() + ":" + current.getVersion());
    }

    public void help() {
        System.err.println("list dependencies [group id] [artifact id] [version]");
        System.err.println("  Shows dependencies that match the passed values.");
    }
}
