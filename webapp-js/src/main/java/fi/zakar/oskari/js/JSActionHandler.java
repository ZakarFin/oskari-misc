package fi.zakar.oskari.js;

import fi.nls.oskari.control.ActionException;
import fi.nls.oskari.control.ActionHandler;
import fi.nls.oskari.control.ActionParameters;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * Created by zakar on 03/12/14.
 * http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html
 * http://stackoverflow.com/questions/19500141/how-can-i-use-commonjs-modules-with-oracles-new-nashorn-js-engine
 *
 * TODO: We propably want to offer some js-functions for writing the response from js
 * Also module system/require is missing.
 */
public class JSActionHandler extends ActionHandler {

    private String name;
    private String path;
    private Invocable invocable = null;

    public JSActionHandler(final String name, final String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String getName() {
        return name;
    }

    public void init() {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");

        try {
            engine.eval(new FileReader(path));
            invocable = (Invocable) engine;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void handleAction(ActionParameters actionParameters) throws ActionException {

        try {
            invocable.invokeFunction("handleAction", actionParameters);
        } catch (Exception ex) {
            throw new ActionException("Error invocing handleAction() for " + path, ex);
        }

    }
}
