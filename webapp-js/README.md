# Nashorn experimentation for oskari-server/oskari-spring

Wraps oskari-spring/spring-map into a war file and includes the ability to
implement very basic javascript action routes for Oskari:

------- clip: myroute.js ---------

function handleAction(params) {
    print("log message from JS");
    params.getResponse().getWriter().print("Hello JS! " + params.getUser().getLastname());
    params.getResponse().getWriter().flush();
    params.getResponse().getWriter().close();
}

------- /clip: myroute.js ---------

## Setup

1) git clone https://github.com/nls-oskari/oskari-spring

2) run mvn clean install in <work dir>/oskari-spring/

3) git clone https://github.com/ZakarFin/oskari-misc

4) run mvn clean install in <work dir>/oskari-misc/webapp-js

5) deploy oskari-misc/webapp-js/target/oskari-js.war to a servlet container

6) configure oskari-ext.properties with the usual and include:

	oskari.extension.js.actionroutes.dir=[directory with .js scripts having handleAction()]

For example:

	oskari.extension.js.actionroutes.dir=<work dir>/oskari-misc/webapp-js/src/main/resources

Note that the user running the server must have filesystem read permissions to configured JS-dir.

7) Startup the servlet container and call the Oskari action route URL with the JS filename (like /action?action_route=sample)

## Known issue

* Changes in JS files and directory are not detected -> Servlet container needs to be restarted after any changes.

TODO: add a watch for directory for changes

* Requiring additional files don't work - Nashorn isn't compatible with JS modules.

TODO: try this https://github.com/nodyn/nodyn
