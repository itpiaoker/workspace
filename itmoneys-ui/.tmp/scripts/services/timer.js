'use strict';

app.factory('Timer', ['$resource', function ($resource) {
    return $resource('/timer/:id', {}, {
        download: {
            method: 'GET',
            responseType: 'blob',
            transformResponse: function transformResponse(data) {
                //                 console.log(data);
                //MESS WITH THE DATA
                var response = {};
                response.data = data;
                response.headers = {
                    'Content-Disposition': 'attachment'

                };
                return response;
            }
        } //end
    });
}]);
//# sourceMappingURL=timer.js.map
