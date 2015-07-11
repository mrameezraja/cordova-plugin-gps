
var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

var gps = function() {

};

gps.prototype.getStatus = gps.getStatus = function (successCallback, errorCallback) {
    var onsuccess = function(gpsStatus){
      successCallback(JSON.parse(gpsStatus));
    }
    exec(onsuccess, errorCallback, "GPS", "getStatus");
}

gps.prototype.getCurrentLocation = function (successCallback, errorCallback) {
    var onsuccess = function(location){
      successCallback(JSON.parse(location));
    }
    exec(onsuccess, errorCallback, "GPS", "getCurrentLocation");
}

module.exports = gps;
