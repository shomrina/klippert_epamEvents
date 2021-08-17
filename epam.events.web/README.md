# klippert_epamEvents
Project work

=========================RUNNING===========================

* browser options:

Tests can run from cmd (from project folder) with parameters:

mvn clean test -Dbrowser="${browser_name}" -Doptions="${browser_options}" -Dparallel="{parallel_options}

if you need to use several options, use " " between each one.

Examples:

mvn clean test -Dbrowser="chrome"

mvn clean test -Dbrowser="chrome" -Doptions="start-maximized incognito headless"

mvn clean test -Dbrowser="chrome" -Doptions="start-maximized" -Dparallel="false"

Also, you can run just use command: mvn clean test

in this case tests will run with default options: 
browser = chrome, options = start-maximized, parallel = true.
You can change it in the pom-file in the 'properties' section.

* Parallel running:

Parallel options are configured in the pom-file in the 'build -> maven-surefire-plugin' section.
If you need switch off parallel running just change parameter 'junit.jupiter.execution.parallel.enabled' to 'false'. 
 

* Remote running

start hub: 

\\klippert_epamEvents\epam.events.web\src\main\resources> java -jar selenium-server-standalone-3.149.59.jar -role hub -port 4444

then start node:

java -Dwebdriver.chrome.driver="{your path to project}\klippert_epamEvents\epam.events.web\src\main\resources\chromedriver-92.0.4515.exe" -jar selenium-server-standalone-3.141.59.jar -role node -hub "http://localhost:4444/grid/register/"

or

java -Dwebdriver.chrome.driver="{your path to project}\klippert_epamEvents\epam.events.web\src\main\resources\chromedriver-92.0.4515.exe" -jar selenium-server-standalone-3.141.59.jar -role node -hub "http://localhost:4444/grid/register/" -browser browserName="chrome",version="92.0.4515",platform="WIN10"