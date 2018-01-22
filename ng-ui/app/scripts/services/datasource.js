'use strict';
app.factory('DataSource', function ($resource) {
    return $resource('/datasource/:id', {}, {
        download: {
            method: 'GET',
            responseType: 'blob',
            transformResponse: function (data) {
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
                type: '@type',
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
});
