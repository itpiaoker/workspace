'use strict';

app.factory('Parser', ['$resource', function ($resource) {
    return $resource('/parser/:id', {}, {
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
        properties: {
            method: 'POST',
            url: '/previewer',
            isArray: true,
            timeout: 5000
        },
        preview: {
            method: 'POST',
            url: '/preview',
            isArray: true,
            timeout: 5000
        } //end
    });
}]);
/*
app.factory('ParserProperties', function ($resource) {
    return $resource('/previewer', {}, {
        preview: {
            method: 'POST',
            isArray: true,
            timeout: 20000
        }
    });
});
app.factory('ParserPreview', function ($resource) {
    return $resource('/preview', {}, {
        preview: {
            method: 'POST',
            isArray: true,
            timeout: 20000
        }
    });
});*/
//# sourceMappingURL=parser.js.map
