/**
 * Created by zhhuiyan on 16/8/12.
 */
'use strict';

app.factory('Metrics', ['$resource', function ($resource) {
    return $resource('/metrics/:collector/:id', { id: '@id', collector: '@collector' }, {
        query: {
            method: 'GET',
            isArray: false
        }
    });
}]);
//# sourceMappingURL=metrics.js.map
