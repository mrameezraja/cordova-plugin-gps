
var GPS = function() {

};

GPS.prototype.getStatus = function (successCallback, errorCallback) {
    var onsuccess = function(gpsStatus){
      successCallback(JSON.parse(gpsStatus));
    }
    cordova.exec(onsuccess, errorCallback, "GPS", "getStatus");
}

GPS.prototype.getCurrentLocation = function (successCallback, errorCallback) {
    var onsuccess = function(location){
      successCallback(JSON.parse(location));
    }
    cordova.exec(onsuccess, errorCallback, "GPS", "getCurrentLocation");
}

module.exports = GPS;
