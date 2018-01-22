'use strict';

app.factory('Dictionary', ['$resource', function ($resource) {
    return $resource('/dictionary/:id');
}]);
//# sourceMappingURL=dictionary.js.map
