
GPS Plugin
====================

GPS plugin for android. (under development)

Installation
------------

<code> cordova plugin add https://github.com/mrameezraja/cordova-plugin-gps </code>


Methods
-------
- navigator.gps.getStatus
- navigator.gps.getCurrentLocation


navigator.gps.getStatus
=================

<pre>
<code>
  navigator.gps.getStatus(function(gpsstats){
    console.log(JSON.stringify(gpsstats));
  }, function(error){
    console.log(error);
  })
</code>
</pre>

Available fields:
- gpsEnabled
- gpsFix
- grade
- accuracy

navigator.gps.getCurrentLocation
=================

<pre>
<code>
  navigator.gps.getCurrentLocation(function(position){
      console.log(JSON.stringify(gpsstats));
  }, function(error){
    console.log(error);
  })
</code>
</pre>


Supported Platforms
-------------------

- Android
