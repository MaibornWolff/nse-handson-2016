angular.module('dashboardApp', ['ngResource', 'nvd3'])

    .controller('mainController', function ($scope, RoomCount, $interval) {


        $scope.options = {
            chart: {
                type: 'discreteBarChart',
                height: 450,
                margin : {
                    top: 20,
                    right: 20,
                    bottom: 50,
                    left: 55
                },
                x: function(d){return d.label;},
                y: function(d){return d.value;},
                showValues: true,
                valueFormat: function(d){
                    return d3.format('d')(d);
                },
                duration: 500,
                showYAxis: false,
                yDomain: [0,50]
            }
        };

        function getCountForRoom(roomNr, roomcounts) {
            if (roomcounts.length >= (roomNr+1)) {
                $scope.data[0].values.push({"label":roomcounts[roomNr].roomName, "value":roomcounts[roomNr].count});
            }
        }

        $interval(function() {
            RoomCount.query(function(roomcounts) {
                $scope.data = [{ key: "Room Counts", values: []}];
                getCountForRoom(0, roomcounts);
                getCountForRoom(1, roomcounts);
                getCountForRoom(2, roomcounts);
                getCountForRoom(3, roomcounts);
            });

            if(!$scope.$$phase) {
                $scope.$apply();
            }

        }, 1000);
    })

    .factory('RoomCount', function ($resource) {
        return $resource('/roomCount');
    })
;
