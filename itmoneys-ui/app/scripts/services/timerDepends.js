'use strict';

app.factory('TimerDepends', function ($resource) {
    return $resource('/timerDepends/:id', {}, {
         download: {
             method: 'GET',
             responseType: 'blob',
             transformResponse: function(data){
//                 console.log(data);
                 //MESS WITH THE DATA
                 var response = {};
                 response.data = data;
                 response.headers = {
                     'Content-Disposition': 'attachment'

                 };
                 return response;
             }
         }
     }//end
     );
});
