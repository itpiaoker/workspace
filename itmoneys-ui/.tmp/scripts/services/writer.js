'use strict';

app.factory('Writer', ['$resource', function ($resource) {
    return $resource('/writer/:id', {}, {
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
//# sourceMappingURL=writer.js.map
