# DSpace ResourceSync Module

This module provides ResourceSync capabilities for DSpace, supporting the metadata harvesting use case. It is based on the original work from Richard Jones of CottageLabs. 4Science has used fund from OpenAIRE to update, extend and improve it, see https://www.openaire.eu/openaire-tender-calls-winners

**Please note this is the version for DSpace 5, check these other branches for a version compatible with [DSpace 6](https://github.com/4Science/DSpaceResourceSync/tree/D4CRIS-506-D6) and [DSpace 7](https://github.com/4Science/DSpaceResourceSync/tree/D4CRIS-506-D7)**

## Dependencies

This module depends on a generic ResourceSync Java library, which you will need to install before you can
build the code

    https://github.com/4Science/ResourceSyncJava

## Installation

The software can be compiled with simply

    mvn clean package

This will generate the webapp (WAR) file, and a JAR file containing all the classes.

To deploy this in DSpace, you should install the code into your local maven repository with

    mvn install

and then update your dspace pom.xml file to depend on this module, with a dependency like:

    <dependency>
        <groupId>org.dspace</groupId>
        <artifactId>dspace-resourcesync</artifactId>
        <version>1.1-SNAPSHOT</version>
        <type>jar</type>
        <classifier>classes</classifier>
    </dependency>

When you compile DSpace this will include all the relevant dependencies into the lib directories.

You can then deploy the dspace-resourcesync webapp in tomcat alongside your DSpace webapps.

You must also deploy the resourcesync.cfg file into the DSpace config/modules directory.

## Usage

In order to provide the ResourceSync documents via the webapp, you need to generate the documents.

### Create the initial Resource List

    ./dspace dsrun org.dspace.resourcesync.ResourceSyncGenerator -i

### Update the Change Lists periodically

This will generate a new Change List every time it is run, and make it available via the Change List Archive.  It is
best to run this as a cron job, at a frequency suitable to the rate of change of the content in your repository (for
example, once a week).

    ./dspace dsrun org.dspace.resourcesync.ResourceSyncGenerator -u

### Rebase the documents periodically

This will generate an up-to-date Resource List and a new Change List every time it is run.  It is best to run this as
a cron job at a longer frequency suitable to the rate of change of the content in your repository (for example, once
a month)

## Configuration

Configuration can be found in dspace/config/modules/resourcesync.cfg

Key configuration values to pay attention to:

    expose-bundles = ORIGINAL

If you have other item bundles that you want to expose, add them here.  To disable the sharing of bitstreams, remove
this configuration value.

    metadata.formats = \
        qdc = http://purl.org/dc/terms/

    metadata.types = \
        qdc = application/xml

This allows us to determine the formats that metadata items will be exposed using.  The key (in the example, "qdc")
should map to the "name" of a named plugin in the DSpace configuration; the default value here maps to the OAIDCCrosswalkDisseminator,
and thus produces metadata identical to that produced by the OAI-PMH webapp.

    changelist.include-restricted = false

This allows us to expose archived items which are not publicly accessible (i.e. that require access control).  It is
strongly advised to leave this set to "false", as otherwise consumers of your ResourceSync data will need to know that
they need to authenticate, and to have user accounts.

    capabilitylist.described-by = ${dspace.baseUrl}/dspace-resourcesync/about.txt

This points to a web page that has a human-readable description of the service provided by the repository.  You may
replace this with any other web-page that suits you.
