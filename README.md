
GPS Plugin
====================

GPS plugin for android. (under development)

Installation
------------

<code> cordova plugin add https://github.com/mrameezraja/cordova-plugin-gps </code>


Methods
-------
- cordova.plugins.gps.getStatus
- cordova.plugins.gps.getCurrentLocation


cordova.plugins.gps.getStatus
=================

<pre>
<code>
  cordova.plugins.gps.getStatus(function(gpsstats){
    console.log(JSON.stringify(gpsstats));
  }, function(error){
    console.log(error);
  })
</code>
</pre>

Response fields:
- gpsEnabled
- gpsFix
- grade
- accuracy

cordova.plugins.gps.getCurrentLocation
=================

<pre>
<code>
  cordova.plugins.gps.getCurrentLocation(function(position){
      console.log(JSON.stringify(position));
  }, function(error){
    console.log(error);
  })
</code>
</pre>


Supported Platforms
-------------------

- Android
