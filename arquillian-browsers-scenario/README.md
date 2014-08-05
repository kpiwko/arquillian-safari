arquillian-browsers-scenario: Example AeroGear Application Using Multiple HTML5, Mobile & JAX-RS Technologies
==============================================================================================
Author: Jay Balunas, Karel Piwko

What is it?
-----------

This is your project! It's a deployable Maven 3 project to help you
get your foot in the door developing HTML5 based desktop/mobile web applications with Java EE 6
on JBoss. This project is setup to allow you to create a basic Java EE 6 application
using HTML5, jQuery Mobile, JAX-RS, CDI 1.0, EJB 3.1, JPA 2.0 and Bean Validation 1.0. It includes
a persistence unit and some sample persistence and transaction code to help 
you get your feet wet with database access in enterprise Java. Additionally, this project shows you
how to test various aspects of your application using Arquillian.

This application is built using a HTML5 + REST approach. This uses a pure HTML
client that interacts with with the application server via restful end-points (JAX-RS).  This
application also uses some of the latest HTML5 features and advanced JAX-RS. And since testing
is just as important with client side as it is server side, this application uses QUnit to show
you how to unit test your JavaScript.

What is a modern web application without mobile web support? This application also integrates
jQuery mobile and basic client side device detection to give you both a desktop and mobile 
version of the interface. Both support the same features, including form validation, member
registration, etc. However the mobile version adds in mobile layout, touch, and performance 
improvements needed to get you started with mobile web development on JBoss.  

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven
3.0 or better and recent Android SDK.

The application this project produces is designed to be run on JBoss AS 7 or JBoss Enterprise Application Platform 6.

An HTML5 compatible browser such as Chrome, Safari 5+, Firefox 5+, or IE 9+ are
required. and note that some behaviors will vary slightly (ex. validations) based on browser support,
especially IE 9.

Mobile web support is limited to Android and iOS devices.  It should run on HP,
and Black Berry devices as well.  Windows Phone, and others will be supported as 
jQuery Mobile announces support.
 
With the prerequisites out of the way, you're ready to build and deploy.

Deploying the application
-------------------------

First you need to start the JBoss container. To do this, run
  
    $JBOSS_HOME/bin/standalone.sh
  
or if you are using windows
 
    $JBOSS_HOME/bin/standalone.bat
    
Note: Adding "-b 0.0.0.0" or "-b {external.ip.address}" to the above commands will allow external clients (phones, tablets, 
desktops, etc...) connect through your local network.
      
For example

    $JBOSS_HOME/bin/standalone.sh -b 0.0.0.0 

To deploy the application, you first need to produce the archive to deploy using
the following Maven goal:

    mvn package

You can now deploy the artifact by executing the following command:

    mvn jboss-as:deploy

The client application will be running at the following URL <http://localhost:8080/jboss-as-kitchensink-html5-mobile/>.

To undeploy run this command:

    mvn jboss-as:undeploy

You can also start the JBoss container and deploy the project using JBoss Tools. See the
<a href="https://docs.jboss.org/author/display/AS71/Getting+Started+Developing+Applications+Guide" title="Getting Started Developing Applications Guide">Getting Started Developing Applications Guide</a> 
for more information.


You can also deploy the application directly to OpenShift, Red Hat's cloud based PaaS offering, follow the instructions [here](https://community.jboss.org/wiki/DeployingHTML5ApplicationsToOpenshift)

Minification
============================

By default, the project uses the [wro4j](http://code.google.com/p/wro4j/) plugin,
which provides the ability to concatenate, validate and minify JavaScript and CSS
files. These minified files, as well as their unmodified versions are deployed with
the project.

With just a few quick changes to the project, you can link to the minified versions
of your JavaScript and CSS files.

First, in the <project-root>/src/main/webapp/index.html file, search for
references to minification and comment or uncomment the appropriate lines.

Finally, wro4j runs in the compile phase so any standard build command like package,
install, etc. will trigger it. The plugin is in a profile with an id of "minify" so
you will want to specify that profile in your maven build.

NOTE: You must either specify the default profile for no tests or the arquillian test
profile to run tests when minifying to avoid test errors. For example:

    #No Tests
    mvn clean package jboss-as:deploy -Pminify,default

OR

    #With Tests
    mvn clean package jboss-as:deploy -Pminify,arq-jbossas-remote
 
Running the Arquillian tests
============================

By default, tests are configured to be skipped. The reason is that the sample
test is an Arquillian test, which requires the use of a container. You can
activate this test by selecting one of the container configuration provided 
for JBoss.

To run the test in JBoss, first start the container instance. Then, run the
test goal with the following profile activated and choose the browser that you want to use:

    mvn clean test -Parq-jbossas-remote -Dbrowser=firefox

Development notes
=================

Copyright headers
-----------------

To update the copyright headers, just run `mvn license:format -Dyear=<current year>`

