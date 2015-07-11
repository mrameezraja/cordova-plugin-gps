
var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

var gps = function() {

};

gps.prototype.getStatus = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "GPS", "getStatus", []);
}

gps.prototype.isEnabled = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "GPS", "isEnabled", []);
}

gps.prototype.getCurrentLocation = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "GPS", "getCurrentLocation", []);
}
var gpsPlugin = new gps();
module.exports = gpsPlugin;
