package com.sampullara.maven.commands;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: samp
 * Date: Jun 24, 2007
 * Time: 12:43:46 PM
 */
public class RemovedependencyCommand extends ListdependenciesCommand {

    @Override
    protected void operateOnDependency(Model model, Dependency dependency) {
        model.getDependencies().remove(dependency);
    }

    public void help() {
        System.err.println("remove dependency [group id] [artifact id] [version]");
        System.err.println("  Removes a default jar dependency.  You can use set dependency to change\n" +
                "  the default options for type, scope, and optional.");
    }
}
