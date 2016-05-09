var myApp = angular.module('shoutApp', ['ngResource']).factory('myFactory', function($interval, $resource) {

    function pollShoutService() {
        $interval(function() {
             $resource('/shout').query(function(data){

                 if(data.length === 0) {
                     console.log("Nothing to speak as array is empty")
                 }
                 else{
                     console.log("speaking content in array")
                     for (var i=0; i<data.length; i++){
                         speak(data[i])
                     }
                 }
             }, function(err){
                 console.log('shout request failed');
             });



        }, 1000);
    }

    return {
        pollShoutService: pollShoutService
    };
});
myApp.controller('mainController', function($scope, myFactory) {
    myFactory.pollShoutService(function(){
        if(!$scope.$$phase) {
            $scope.$apply();
        }
    });
});

function speak(text, callback) {
    var u = new SpeechSynthesisUtterance();
    u.text = text;
    u.lang = 'de-de';

    u.onend = function () {
        if (callback) {
            callback();
        }
    };

    u.onerror = function (e) {
        if (callback) {
            callback(e);
        }
    };

    speechSynthesis.speak(u);
}

