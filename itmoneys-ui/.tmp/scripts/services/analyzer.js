'use strict';

app.factory('Analyzer', ['$resource', function ($resource) {
    return $resource('/analyzer/:id', {}, {
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
        },
        preview: {
            method: 'POST',
            url: '/analyzer/preview',
            timeout: 20000
        },
        queryByType: {
            method: 'GET',
            url: '/analyzer/type',
            isArray: true,
            timeout: 20000
        } //end
    });
}]);
//# sourceMappingURL=analyzer.js.map
