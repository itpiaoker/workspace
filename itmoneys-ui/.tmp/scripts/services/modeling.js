'use strict';

app.factory('Modeling', ['$resource', function ($resource) {
    return $resource('/modeling/:id', {}, {
        download: {
            method: 'GET',
            // responseType: 'blob',
            transformResponse: function transformResponse(data) {
                var obj = JSON.parse(data);
                if (obj.jobId) {
                    delete obj.jobId;
                }
                //MESS WITH THE DATA
                var response = {};
                var blob = new Blob([JSON.stringify(obj)], { type: "application/json" });
                response.data = blob;
                response.headers = {
                    'Content-Disposition': 'attachment'

                };
                return response;
            }
        },
        operate: {
            method: 'PUT',
            url: '/modeling/:opt/:id',
            params: {
                opt: '@opt',
                id: '@id'
            },
            timeout: 20000
        },
        check: {
            method: 'POST',
            url: '/usability',
            isArray: true,
            timeout: 20000
        },
        connectTest: {
            method: 'POST',
            url: '/modeling/check',
            timeout: 20000
        },
        queryByType: {
            method: 'GET',
            url: '/modeling/type/:type',
            isArray: true,
            timeout: 20000
        }

    });
}]);
app.service('Util', function () {
    /**
     * 重置表单状态
     * @param form
     * @returns {boolean}
     */
    this.resetFormValidateState = function (form) {
        if (!form) return false;
        form.$submitted = false;
        angular.forEach(form, function (obj, key) {
            if (key.substring(0, 1) !== '$' && form[key].$dirty) {
                form[key].$dirty = false;
                form[key].$pristine = true;
            }
        });
    };
});
//# sourceMappingURL=modeling.js.map
