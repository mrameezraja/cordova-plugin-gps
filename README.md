
GPS Plugin
====================

GPS plugin for android. (under development)

Installation
------------

<code> cordova plugin add https://github.com/mrameezraja/cordova-plugin-gps </code>


Methods
-------
- window.plugins.gps.getStatus
- window.plugins.gps.getCurrentLocation


navigator.gps.getStatus
=================

<pre>
<code>
  window.plugins.gps.getStatus(function(gpsstats){
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

window.plugins.gps.getCurrentLocation
=================

<pre>
<code>
  window.plugins.gps.getCurrentLocation(function(position){
      console.log(JSON.stringify(position));
  }, function(error){
    console.log(error);
  })
</code>
</pre>


Supported Platforms
-------------------

- Android
