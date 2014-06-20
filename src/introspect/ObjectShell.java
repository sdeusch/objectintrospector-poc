package introspect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import test.Sample;

public class ObjectShell {

    // the object under investigation
    private Object object;

    public ObjectShell(final Object object) {
        super();
        this.object = object;
    }

    public Object getObject() {
        return this.object;
    }

    public void setObject(final Object object) {
        this.object = object;
    }

    protected void printHelp() {
        System.out.printf("GET *                prints out all target object members and their current values.\n");
        System.out.printf("GET property         prints out the current value of the target object's property. \n");
        System.out.printf("SET property=value   sets target's object member property equal value. \n");
        System.out.printf("USE class            instantiated a new object with the fully qualified class path.\n");
        System.out.printf("HELP this screen\n");
    }

    /**
     * SET this object to a new instance of specified class (extensibility for future)
     * 
     * @param parms
     */
    protected void handleUse(final List<String> parms) {
        if (parms.size() <= 1) {
            this.printHelp();
            return;
        }

        final String classname = parms.get(1);
        final Object newObject = ObjectAnalyzer.makeEmptyObject(classname);

        if (newObject != null) {
            this.object = newObject;
            System.out.print("\n");
            System.out.println(this.printObjectInfo());
            System.out.print("\n");
        }
    }

    /**
     * Prints out one or all attributes of current object
     * 
     * @param parms
     */
    protected void handleGet(final List<String> parms) {
        if (parms.size() <= 1) {
            this.printHelp();
            return;
        }

        final String attribute = parms.get(1);
        if ("*".equals(attribute)) {
            ObjectAnalyzer.printAllAttributes(this.object);
        } else {
            ObjectAnalyzer.printAttribute(this.object, attribute);
        }

        return;
    }

    protected String printObjectInfo() {
        return "Loaded object of type [" + this.object.getClass().getCanonicalName() + "] with value " + this.object;
    }

    /**
     * Handles setting the value of an attribute of this.object
     * 
     * @param parms
     */
    protected void handleSet(final List<String> parms) {
        try {
            // parse attribute name and value
            final String token = parms.get(1); // no blank spaces allowed for now
            final int idx = token.indexOf("=");
            final String attribute = token.substring(0, idx);
            final String value = token.substring(idx + 1);
            ObjectAnalyzer.setAttribute(this.object, attribute, value);
        } catch (final Throwable e) {
            System.err.println("Could not set attribute: " + e.getMessage());
            this.printHelp();
        }
        return;
    }

    public static void main(final String[] args) throws IOException {
        final ObjectShell shell = new ObjectShell(new Sample());

        System.out.print("\n");
        System.out.println(shell.printObjectInfo());
        System.out.print("\n");

        String commandLine;
        final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // read what the user entered
            System.out.print("Object Analyzer>");
            commandLine = console.readLine();
            {
                // if the user entered a return, just loop again
                if (commandLine.equals("")) {
                    continue;
                } else if (commandLine.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye");
                    System.exit(0);
                }

                // split the string into a string array

                final List<String> parms = new ArrayList<String>();
                final String[] lineSplit = commandLine.split(" ");

                final int size = lineSplit.length;
                for (int i = 0; i < size; i++) {
                    parms.add(lineSplit[i]);
                }

                final String command = parms.get(0);
                switch (command.toUpperCase()) {
                    case "GET":
                        shell.handleGet(parms);
                        break;
                    case "SET":
                        shell.handleSet(parms);
                        break;
                    case "USE":
                        shell.handleUse(parms);
                        break;
                    default:
                        shell.printHelp();
                        break;
                }

            }
        }
    }

}
