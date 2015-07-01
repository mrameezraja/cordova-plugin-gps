
var GPS = function() {

};

GPS.prototype.getStatus = function (successCallback, errorCallback) {
    var onsuccess = function(contacts){
      successCallback(JSON.parse(contacts));
    }
    cordova.exec(onsuccess, errorCallback, "GPS", "getStatus");
}

GPS.prototype.getCurrentLocation = function (successCallback, errorCallback) {
    var onsuccess = function(contacts){
      successCallback(JSON.parse(contacts));
    }
    cordova.exec(onsuccess, errorCallback, "GPS", "getCurrentLocation");
}

/*if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.gps) {
    window.plugins.gps = new GPSFix();
}*/

if (typeof module != 'undefined' && module.exports) {
    module.exports = GPS;
}
