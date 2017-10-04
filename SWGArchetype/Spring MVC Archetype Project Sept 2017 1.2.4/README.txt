**** Updated to 1.2.4 in Sept 2017 java cohort to current versions of plugins:

jquery 3.2.1
Bootstrap 4.0 beta
Spring: 5.0.0.RELEASE
aspectjtools: 1.8.11
apache aommons-dbcp2: 2.1.1
mysql-connector: 8.0.8-dmr
javax.servlet api: 4.0.0
javax.inject 1
jstl 1.2
javax validation-api 2.0.0 Final
jackson-core 2.9.1
junit 4.12
hibernate-validator 5.2.2.Final
jsp-api: 2.2


SWC Guild Spring MVC Maven Archetype Installation Instructions
==============================================================
    1. Copy archetype-catalog.xml to .m2 directory
    2. Open terminal window in the archetype-spring-mvc directory
       and type 'mvn clean install'
    3. Configure NetBeans:
        a. Go to Tools-->Options
        b. Select 'Java' and then the 'Maven' tab
        c. Click 'Index Now'
    4. Test Setup:
        a. Click File-->New Project
        b. Select Maven-->Project from Archetype
        c. Type 'swcguild' in the Search bar - if swcguild-spring-mvc-archetype
           appears as an option, your configuration is complete.
