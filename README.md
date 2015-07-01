
GPS Plugin
====================

GPS plugin for android.


Methods
-------
- navigator.gps.getStatus
- navigator.gps.getCurrentLocation


navigator.gps.getStatus
=================

<pre>
<code>
  navigator.gps.getStatus(function(gpsstats){

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
      console.log(position);
  }, function(error){
    console.log(error);
  })
</code>
</pre>


Supported Platforms
-------------------

- Android
