'use strict';

app.factory('DataSource', ['$resource', function ($resource) {
    return $resource('/datasource/:id', {}, {
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
        byType: {
            method: 'POST',
            url: '/datasource/type/:type',
            params: {
                type: '@type'
            },
            isArray: true,
            timeout: 5000
        },
        preview: {
            method: 'POST',
            url: '/datasource/preview',
            isArray: true,
            timeout: 5000
        }
    });
}]);
//# sourceMappingURL=datasource.js.map
