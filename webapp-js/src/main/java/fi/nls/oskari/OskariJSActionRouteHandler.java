package fi.nls.oskari;

import fi.nls.oskari.control.ActionControl;
import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;
import fi.nls.oskari.util.PropertyUtil;
import fi.zakar.oskari.js.JSActionHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * This will propably not work since annotation configuration has basedir of fi.nls.oskari...
 * Reads the configured directory for js-files and registers them as action routes.
 */
@Configuration
public class OskariJSActionRouteHandler {

    private Logger log = LogFactory.getLogger(OskariJSActionRouteHandler.class);

    private String PATH_TO_SCRIPTS = PropertyUtil.getOptional("oskari.extension.js.actionroutes.dir");

    public static void main(String[] arguments) throws Exception {
        OskariJSActionRouteHandler h = new OskariJSActionRouteHandler();
        Map<String, String> paths = h.getRoutes();
        for (String p : paths.keySet()) {
            System.out.println(p + " -> " + paths.get(p));
        }
/*
        JSActionHandler handler = new JSActionHandler("somefile", "/somedir/somefile.js");
        handler.init();
        // try calling it with empty params
        handler.handleAction(new ActionParameters());
*/
    }

    @PostConstruct
    public void initRoutes() {
        // TODO: PATH_TO_SCRIPTS needs to be monitored for changes:
        // - file change == replace/re-init action handler
        // - file removal == route removal
        // - file addition == route addition

        Map<String, String> paths = getRoutes();
        for (String p : paths.keySet()) {
            log.debug("Adding JS action route with name:", p);
            JSActionHandler handler = new JSActionHandler(p, paths.get(p));
            ActionControl.addAction(p, handler);
        }
    }

    public Map<String, String> getRoutes() {
        Map<String, String> paths = new HashMap<>();
        if (PATH_TO_SCRIPTS != null) {
            Path dir = Paths.get(PATH_TO_SCRIPTS);
            try {
                DirectoryStream<Path> files = Files.newDirectoryStream(dir, "*.js");
                for (Path file : files) {
                    final String filename = file.getFileName().toString();
                    paths.put(filename.substring(0, filename.length() - 3), file.toAbsolutePath().toString());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return paths;
    }
}
