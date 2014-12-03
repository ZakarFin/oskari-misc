# Nashorn experimentation for oskari-server/oskari-spring

Wraps oskari-spring/spring-map into a war file and includes the ability to
implement very basic javascript action routes for Oskari:

------- clip: myroute.js ---------

	function handleAction(params) {
		print(params);
		for(var k in this) {
			print(k);
			print(this[k]);
		}
	}

------- /clip: myroute.js ---------

## Setup

1) git clone https://github.com/nls-oskari/oskari-spring

2) compile oskari-spring/spring-map

3) git clone https://github.com/ZakarFin/oskari-misc

4) compile oskari-misc/webapp-js

5) deploy oskari-misc/webapp-js/target/oskari-js.war to a servlet container

6) configure oskari-ext.properties with the usual and include:

	oskari.extension.js.actionroutes.dir=[directory with .js scripts having handleAction()]

Note that the user running the server must have filesystem read permissions to configured JS-dir.