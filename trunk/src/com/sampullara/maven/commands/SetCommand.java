package com.sampullara.maven.commands;

import org.apache.maven.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: samp
 * Date: Jun 23, 2007
 * Time: 5:18:21 PM
 */
public class SetCommand extends Command {

    public void execute(String[] args) {
        if (args.length != 3) {
            help();
            return;
        }
        Model model = readModel();
        if (model == null) {
            return;
        }
        Method[] methods = Model.class.getMethods();
        boolean found = false;
        for (Method method : methods) {
            String name = method.getName();
            Class[] parameters = method.getParameterTypes();
            if (name.startsWith("set") && parameters.length == 1 && parameters[0] == String.class) {
                String variable = name.substring(3);
                if (variable.toLowerCase().equals(args[1].toLowerCase())) {
                    try {
                        method.invoke(model, args[2]);
                        found = true;
                    } catch (IllegalAccessException e) {
                        System.err.println("Failed to set variable '" + variable + "', " + e);
                        return;
                    } catch (InvocationTargetException e) {
                        System.err.println("Failed to set variable '" + variable + "', " + e);
                        return;
                    }
                }
            }
        }
        if (!found) {
            System.err.println("Failed to find variable: " + args[1]);
            help();
        } else {
            writeModel(model);
        }
    }

    public void help() {
        System.err.println("set [variable] [value]");
        Method[] methods = Model.class.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            Class[] parameters = method.getParameterTypes();
            if (name.startsWith("set") && parameters.length == 1 && parameters[0] == String.class) {
                String variable = name.substring(3);
                System.err.println("  " + variable.toLowerCase());
            }
        }
    }
}
