'use strict';

app.factory('KnowledgeInfo', ['$resource', function ($resource) {
    return $resource('/knowledgeInfo/:id', {}, {} //end
    );
}]);
//# sourceMappingURL=knowledgeinfo.js.map
