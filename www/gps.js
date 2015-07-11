
var gps = function() {

};

gps.getStatus = function (successCallback, errorCallback) {
    var onsuccess = function(gpsStatus){
      successCallback(JSON.parse(gpsStatus));
    }
    cordova.exec(onsuccess, errorCallback, "GPS", "getStatus");
}

gps.getCurrentLocation = function (successCallback, errorCallback) {
    var onsuccess = function(location){
      successCallback(JSON.parse(location));
    }
    cordova.exec(onsuccess, errorCallback, "GPS", "getCurrentLocation");
}

module.exports = gps;
