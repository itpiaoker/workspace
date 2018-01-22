'use strict';

app.factory('Collector', ['$resource', function ($resource) {
    return $resource('/collector/:id');
}]);
app.factory('CollectorOpt', ['$resource', function ($resource) {
    return $resource('/collector/:id/:opt', { opt: '@opt', id: '@id' }, { operate: { method: 'PUT' } });
}]);
//# sourceMappingURL=collector.js.map
