'use strict';

app.factory('Writer', function ($resource) {
    return $resource('/writer/:id',{}, {
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
