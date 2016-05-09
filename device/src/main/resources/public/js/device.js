angular.module('deviceApp',
    ['ngResource'])

    .controller('mainController', function ($scope, Device) {

        $scope.deviceIds = [1, 2, 3, 4];
        $scope.roomNumbers = [1, 2, 3, 4];


        $scope.loadDevices = function() {
            Device.query(function(devices) {
                console.log(devices);
                $scope.devices = devices;
            });
        };
        $scope.loadDevices();


        $scope.saveDevices = function() {
            for (var i=0; i < $scope.devices.length; i++) {
                if ($scope.devices[i].roomNumber != null && $scope.devices[i].deviceId != null) {
                    Device.save($scope.devices[i]);
                }
            }
            //TODO: Wenn mal Zeit ist, könnte man hier noch reload und Fehlermeldung bei Nicht-Erfolg machen.
        };

        $scope.addLine = function() {
            $scope.devices.push({
                deviceId: null,
                roomNumber: null
            });
        };


    })

    .factory('Device', function ($resource) {
        return $resource('/device');
    })
;
