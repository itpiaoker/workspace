'use strict';

app.controller('ParserController', ['$scope', '$rootScope', '$location', 'Util', 'Parser', 'Knowledge', 'DataSource', function ($scope, $rootScope, $location, Util, Parser, Knowledge, DataSource) {
    //页面目录
    $rootScope.$path = $location.path.bind($location);

    $scope.previewFields = [];
    $scope.datasources = DataSource.query();

    $scope.dsPreview = function () {

        DataSource.preview({ id: $scope.rule.datasource }, function (data) {
            $scope.datas = data; //.map((item, index) => JSON.stringify(item)).reduce((pre, cur) => pre + "\n#next#" + cur);
            //$scope.rule.sample = data.map((item, index) => JSON.stringify(item)).reduce((pre, cur) => pre + "\n#next#" + cur);
            $scope.showPreview = true;
            $('#log_property_list').show();
        });
    };
    $scope.all = Parser.query();
    $scope.knowledges = Knowledge.query(); //.queryByStatus();
    $('#addModal').modal('hide');
    $scope.uploadFile = function () {
        delete $scope.message;
        var $edit_table = $('.hla-widget-add-table');
        var $search_table = $('.hla-widget-search-table');
        var upload_table = $('.hla-widget-upload-table');
        var list = $('.hla-widget-data-table');
        var file = $scope.myFile;
        var reader = new FileReader();
        var allList = [];
        if ($scope.all.length > 0) {
            $scope.all.forEach(function (value) {
                allList.push(value.id);
            });
        }
        reader.onloadend = function (e) {
            var wrapper = angular.fromJson(this.result);
            if (wrapper.parser && wrapper.parser.filter && wrapper.parser.filter.length > 0) {
                wrapper.parser.filter.forEach(function (item) {
                    if (angular.equals(item.name, 'byKnowledge')) {
                        //删除不存在的知识库
                        var knowledgeList = $scope.knowledges.filter(function (knowledge) {
                            return knowledge.id == item.id;
                        });
                        if (knowledgeList.length == 0) {
                            item.id = '';
                        }
                    }

                    if (item.cases) {
                        item.cases.forEach(function (c, index) {
                            console.log(c);
                            if (c.rule && c.rule.ref && -1 === $.inArray(c.rule.ref, allList)) {
                                c.rule.ref = '';
                            }
                        });
                    }
                    if (item.default && item.default.ref && -1 === $.inArray(item.default.ref, allList)) {
                        item.default.ref = '';
                    }
                });
            }
            $scope.rule = wrapper;
            Parser.get({
                id: $scope.rule['id']
            }, function (data) {
                //$scope.message = data;
                if (data.status === '404') {
                    try {
                        //  delete    $scope.rule.id;
                        $scope.fromRule();
                        $edit_table.slideDown();
                        $search_table.slideUp();
                        upload_table.slideUp();
                        list.slideUp();
                        delete $scope.myFile;
                        //$scope.$apply();
                    } catch (e) {
                        $scope.message = {
                            status: '500',
                            message: '数据解析出错,你检查你的数据文件是否是正确的解析规则内容!'
                        };
                    }
                } else {
                    $('#addModal').modal('show');
                }
            });
        };

        try {
            reader.readAsText(file);
        } catch (e) {
            $scope.message = {
                status: '500',
                message: '数据解析出错,你检查你的数据文件是否正确!'
            };
        }
    };
    $scope.show = function (clazz, id) {
        var edit = $('.hla-widget-add-table');
        var search = $('.hla-widget-search-table');
        var list = $('.hla-widget-data-table');
        var upload_table = $('.hla-widget-upload-table');
        switch (clazz) {
            case 'upload':
                delete $scope.message;
                $scope.searchDataSourceForm = {};
                edit.slideUp();
                search.slideUp();
                upload_table.slideDown();
                break;

            case 'add':
                delete $scope.message;
                $scope.rule = {
                    name: '',
                    sample: '',
                    parser: {
                        name: 'nothing'
                    }
                };
                Util.resetFormValidateState($scope.add_form);
                edit.slideToggle();
                search.slideUp();
                list.slideToggle();
                upload_table.slideUp();
                break;
            case 'edit':
                delete $scope.message;
                Parser.get({
                    id: id
                }, function (data) {

                    console.log(data.datasource);
                    $scope.rule = data;
                    $scope.fromRule();
                });
                Util.resetFormValidateState($scope.add_form);
                edit.slideDown();
                search.slideUp();
                upload_table.slideUp();
                list.slideUp();
                break;
            case 'copy':
                delete $scope.message;
                Parser.get({
                    id: id
                }, function (data) {
                    $scope.rule = data;
                    delete $scope.rule.id;
                    $scope.rule.name = $scope.rule.name + '_copy';
                    $scope.fromRule();
                });
                Util.resetFormValidateState($scope.add_form);
                edit.slideDown();
                search.slideUp();
                upload_table.slideUp();
                list.slideUp();
                break;
            case 'search':
                delete $scope.message;
                edit.slideUp();
                search.slideToggle();
                upload_table.slideUp();
                list.slideDown();
                $scope.page.search = {};
                break;
            default:
                edit.slideUp();
                search.slideUp();
                upload_table.slideUp();
                list.slideDown();
        }
    };
    $scope.reload = function () {

        $scope.list = Parser.get($scope.page);
        $scope.all = Parser.query();
        // $scope.show();
    };

    $scope.toRule = function () {
        var filters = $scope.rule.parser.filter;
        if ('delimit' == $scope.rule.parser.name) {
            $scope.rule.parser.delimit = $scope.rule.parser.delimit.replace(/\\s/g, ' ').replace(/\\t/g, '\t').replace(/\\n/g, '\n');
            if ($scope.rule.parser.fields && $scope.rule.parser.fields.length > 0) {
                var ff = $scope.rule.parser.fields.split(',');
                delete $scope.rule.parser.fields;
                $scope.rule.parser.fields = ff;
            }
        }
        if ('delimitWithKeyMap' == $scope.rule.parser.name) {
            $scope.rule.parser.delimit = $scope.rule.parser.delimit.replace(/\\s/g, ' ').replace(/\\t/g, '\t').replace(/\\n/g, '\n');
            $scope.rule.parser.tab = $scope.rule.parser.tab.replace(/\\s/g, ' ').replace(/\\t/g, '\t').replace(/\\n/g, '\n');
        }

        if (filters) {
            for (var i = 0; i < filters.length; i++) {
                switch (filters[i].name) {
                    case '':
                        delete filters[i];
                        break;
                    case 'merger':
                        if (filters[i].sep) filters[i].sep = filters[i].sep.replace(/\\s/g, ' ').replace(/\\t/g, '\t').replace(/\\n/g, '\n');

                        break;
                    case 'addFields':
                    case 'mapping':
                        var obj = {};

                        for (var _j = 0; _j < filters[i].fields.length; _j++) {
                            obj[filters[i].fields[_j][0]] = filters[i].fields[_j][1];
                        }

                        filters[i].fields = obj;
                        break;
                    case 'redirect':
                    case 'startWith':
                    case 'endWith':
                    case 'match':
                    case 'scriptFilter':
                    case 'contain':
                        if (filters[i].cases) {
                            var _cases = filters[i].cases;

                            for (var _j2 = 0; _j2 < _cases.length; _j2++) {
                                switch (_cases[_j2].rule.name) {
                                    case '':
                                        delete _cases[_j2];
                                        break;
                                    case 'merger':
                                        if (_cases[_j2].rule.sep) _cases[_j2].rule.sep = _cases[_j2].rule.sep.replace(/\\s/g, ' ').replace(/\\t/g, '\t').replace(/\\n/g, '\n');

                                        break;
                                    case 'addFields':
                                    case 'mapping':
                                        obj = {};

                                        for (var x = 0; x < _cases[_j2].rule.fields.length; x++) {
                                            obj[_cases[_j2].rule.fields[x][0]] = _cases[_j2].rule.fields[x][1];
                                        }

                                        _cases[_j2].rule.fields = obj;
                                        break;
                                }
                            }
                            filters[i].cases = _cases;
                        }
                        if (filters[i].default) {
                            switch (filters[i].default.name) {
                                case '':
                                    delete filters[i].default;
                                    break;
                                case 'merger':
                                    if (cases[j].rule.sep) filters[i].default.sep = filters[i].default.sep.replace(/\\s/g, ' ').replace(/\\t/g, '\t').replace(/\\n/g, '\n');

                                    break;
                                case 'addFields':
                                case 'mapping':
                                    obj = {};

                                    for (var _x = 0; _x < filters[i].default.fields.length; _x++) {
                                        obj[filters[i].default.fields[_x][0]] = filters[i].default.fields[_x][1];
                                    }

                                    filters[i].default.fields = obj;
                                    break;

                            }
                        }

                }
            }
            $scope.rule.parser.filter = filters;
        }
    };
    $scope.fromRule = function () {
        var filters = $scope.rule.parser.filter;
        if ('delimit' == $scope.rule.parser.name) {
            $scope.rule.parser.delimit = $scope.rule.parser.delimit.replace(/ /g, '\\s').replace(/\t/g, '\\t').replace(/\n/g, '\\n');

            var ff = $scope.rule.parser.fields.reduce(function (first, second) {
                return first + ',' + second;
            });
            delete $scope.rule.parser.fields;
            $scope.rule.parser.fields = ff;
        }
        if ('delimitWithKeyMap' == $scope.rule.parser.name) {
            $scope.rule.parser.delimit = $scope.rule.parser.delimit.replace(/ /g, '\\s').replace(/\t/g, '\\t').replace(/\n/g, '\\n');
            $scope.rule.parser.tab = $scope.rule.parser.tab.replace(/ /g, '\\s').replace(/\t/g, '\\t').replace(/\n/g, '\\n');
        }
        if (filters) {
            for (var i = 0; i < filters.length; i++) {
                switch (filters[i].name) {
                    case 'merger':
                        if (filters[i].sep) filters[i].sep = filters[i].sep.replace(/ /g, '\\s').replace(/\t/g, '\\t').replace(/\n/g, '\\n');

                        break;
                    case 'addFields':
                    case 'mapping':
                        var obj = [];

                        for (var _j3 in filters[i].fields) {
                            if (filters[i].fields.hasOwnProperty(_j3)) {
                                obj.push([_j3, filters[i].fields[_j3]]);
                            }
                        }

                        filters[i].fields = obj;
                        break;
                    case 'redirect':
                    case 'startWith':
                    case 'endWith':
                    case 'match':
                    case 'scriptFilter':
                    case 'contain':
                        if (filters[i].cases) {
                            var _cases2 = filters[i].cases;

                            for (var _j4 = 0; _j4 < _cases2.length; _j4++) {
                                switch (_cases2[_j4].rule.name) {
                                    case 'merger':
                                        _cases2[_j4].rule.sep = _cases2[_j4].rule.sep.replace(/ /g, '\\s').replace(/\t/g, '\\t').replace(/\n/g, '\\n');

                                        break;
                                    case 'addFields':
                                    case 'mapping':
                                        obj = [];
                                        for (var x in _cases2[_j4].rule.fields) {
                                            if (_cases2[_j4].rule.fields.hasOwnProperty(x)) {
                                                obj.push([x, _cases2[_j4].rule.fields[x]]);
                                            }
                                        }
                                        _cases2[_j4].rule.fields = obj;

                                }
                            }
                        }
                        if (filters[i].default) {
                            switch (filters[i].default.name) {
                                case 'merger':
                                    cases[j].rule.sep = cases[j].rule.sep.replace(/ /g, '\\s').replace(/\t/g, '\\t').replace(/\n/g, '\\n');

                                    break;
                                case 'addFields':
                                case 'mapping':
                                    obj = [];

                                    for (var _x2 in filters[i].default.fields) {
                                        if (filters[i].default.fields.hasOwnProperty(_x2)) {
                                            obj.push([_x2, filters[i].default.fields[_x2]]);
                                        }
                                    }
                                    filters[i].default.fields = obj;

                            }
                        }

                        break;

                }
            }
        }
    };

    //添加规则
    $scope.saveRule = function (form) {

        if (!(form.$valid && (!$scope.rule.parser.fields || $scope.rule.parser.fields.length > 0))) {
            return false;
        }
        $scope.toRule();
        if (!$scope.rule.properties) $scope.rule.properties = [];
        for (var i = 0; i < $scope.rule.properties.length; i++) {
            if ($scope.rule.properties[i].hasOwnProperty('key')) {
                if ($scope.rule.properties[i].type == 'list') {
                    $scope.rule.properties[i].type = 'list[' + $scope.rule.properties[i].subtype + ']';
                    delete $scope.rule.properties[i].subtype;
                }
            }
        }
        /* Parser.get({id: $scope.rule['id']}, function (data) {
           $scope.message = data;
           if($scope.message.status == '404'){
             console.log($scope.rule);
             Parser.save($scope.rule, function (data) {
               $scope.message = data;
               if ($scope.message.status == '200') {
                 $scope.reload();
                 $scope.show();
               }
             });
           }else {
             $('#addModal').modal('show');
           }
           });*/

        Parser.save($scope.rule, function (data) {
            console.log(data);
            $scope.message = data;
            if ($scope.message.status == '200') {
                $scope.reload();
                $scope.show();
            }
        });
    };
    $scope.saveOrEdit = function (clazz) {
        var $edit_table = $('.hla-widget-add-table');
        var $search_table = $('.hla-widget-search-table');
        var upload_table = $('.hla-widget-upload-table');
        var list = $('.hla-widget-data-table');
        var file = $scope.myFile;
        var reader = new FileReader();
        switch (clazz) {
            case 'edit':
                delete $scope.message;
                try {
                    $scope.fromRule();
                    $edit_table.slideDown();
                    $search_table.slideUp();
                    upload_table.slideUp();
                    list.slideUp();
                    delete $scope.myFile;
                    //$scope.$apply();
                    $('#addModal').modal('hide');
                } catch (e) {
                    $scope.message = {
                        status: '500',
                        message: '数据解析出错,你检查你的数据文件是否是正确的解析规则内容!'
                    };
                }
                break;

            case 'save':
                delete $scope.message;
                delete $scope.rule.id;
                $('#addModal').modal('hide');
                try {
                    $scope.fromRule();
                    $edit_table.slideDown();
                    $search_table.slideUp();
                    upload_table.slideUp();
                    list.slideUp();
                    delete $scope.myFile;
                    //$scope.$apply();
                } catch (e) {
                    $scope.message = {
                        status: '500',
                        message: '数据解析出错,你检查你的数据文件是否是正确的解析规则内容!'
                    };
                }
        }
    };

    $scope.changeType = function (index) {
        switch ($scope.rule.properties[index].type) {
            case 'list':
                break;
            default:
                delete $scope.rule.properties[index].subtype;
        }
    };

    $scope.delete = function (id) {
        $scope.delete_id = id;
        $('#confirmModal').modal('show');
    };
    //删除确定
    $scope.confirm_yes = function () {

        Parser.delete({ id: $scope.delete_id }, function (data) {
            $scope.message = data;
            $('#confirmModal').modal('hide');
            $scope.reload();
        });
    };
    $('#confirmModal').find('.op_yes').off().click(function () {
        $scope.confirm_yes();
    });

    function compare(first, second) {
        var reg = /^([a-zA-Z]+)?(\d+)$/;
        if (reg.test(first) && reg.test(second)) {
            var firstRT = reg.exec(first);
            var secondRT = reg.exec(second);
            if (firstRT[1] == secondRT[1]) {
                return firstRT[2] - secondRT[2];
            } else {
                return first - second;
            }
        } else {
            return first - second;
        }
    }

    $scope.initFields = function (callback) {
        if ($scope.rule.parser.name == 'delimit' && (!$scope.rule.parser.fields || $scope.rule.parser.fields.length == 0)) {
            if (!$scope.rule.parser.fields) {
                $scope.rule.parser.fields = [];
            }
            $scope.previewFields = [];
            if ($scope.rule.parser.fields.length == 0) {
                if ($scope.rule.sample && $scope.rule.sample.length > 0) {
                    var sample = $scope.rule.sample.split('#next#');
                    $scope.toRule();
                    Parser.preview($scope.rule, function (data) {
                        for (var i = 0; i < data.length; i++) {
                            for (var k in data[i].toJSON()) {
                                var rt = $.inArray(k, $scope.rule.parser.fields);

                                if (rt < 0) {
                                    $scope.rule.parser.fields.push(k);
                                    $scope.previewFields.push(k);
                                }
                            }
                        }
                        $scope.rule.parser.fields = $scope.rule.parser.fields.sort(compare);
                        $scope.fromRule();
                        if (callback) callback();
                    });
                } else {
                    if (callback) callback();
                }
            }
        } else {
            if ($scope.rule.sample && $scope.rule.sample.length > 0) {
                var _sample = $scope.rule.sample.split('#next#');
                $scope.toRule();
                Parser.preview($scope.rule, function (data) {
                    for (var i = 0; i < data.length; i++) {
                        for (var k in data[i].toJSON()) {
                            var rt = $.inArray(k, $scope.previewFields);
                            if (rt < 0) {
                                $scope.previewFields.push(k);
                            }
                        }
                    }
                    $scope.fromRule();
                    if (callback) callback();
                });
            } else {
                if ($scope.rule.properties) {
                    $scope.rule.properties.forEach(function (data) {
                        $scope.previewFields.push(data.key);
                        var subtype = data.type.match(/list\[(.*)]/);
                        if (subtype && subtype.length >= 2) {
                            data.type = 'list';
                            data.subtype = subtype[1];
                        }
                    });
                } else {
                    $scope.rule.properties = [];
                }

                if (callback) callback();
            }
        }
    };

    $scope.addDelimitField = function () {
        $scope.initFields();
    };

    $scope.removeDelimitField = function (index) {
        $scope.rule.parser.fields.splice(index, 1);
    };
    $scope.removeFilter = function (index) {
        $scope.rule.parser.filter.splice(index, 1);
    };
    $scope.addFilter = function () {

        $scope.initFields(function () {
            if (!$scope.rule.parser.filter) {
                $scope.rule.parser.filter = [];
            }
            $scope.rule.parser.filter.push({
                name: 'addFields',
                fields: [['', '']]
            });
        });
    };

    $scope.ruleChange = function (index) {
        reParserChange($scope.rule.parser.filter[index]);
    };

    function reParserChange(reParser) {
        switch (reParser.name) {
            case 'addFields':
            case 'mapping':
                reParser.fields = [['', '']];
                break;
            case 'removeFields':
                reParser.fields = [''];
                break;
            case 'addTags':
            case 'removeTags':
                reParser.fields = [''];
                break;
            case 'merger':
                reParser.fields = ['', ''];
                break;
            case 'fieldCut':
                break;
            case 'reParser':
                break;
            case 'script':
                break;
            case 'scriptFilter':
            case 'redirect':
            case 'startWith':
            case 'endWith':
            case 'match':
            case 'contain':
                if (!reParser.cases) {
                    reParser.cases = [{
                        name: 'case',
                        rule: {
                            name: 'addFields',
                            fields: [['', '']]
                        }
                    }];
                }

                break;
            default:
                delete reParser.fields;
        }
    }

    $scope.caseDefaultChange = function (index) {
        var reParser = $scope.rule.parser.filter[index].default;
        reParserChange(reParser);
    };

    $scope.addFilterTuple = function (index) {

        var fields = $scope.rule.parser.filter[index].fields;
        if (!fields) {
            $scope.rule.parser.filter[index].fields = fields = [];
        }
        fields.push(['', '']);
    };
    $scope.removeFilterTuple = function (index, key) {
        $scope.rule.parser.filter[index].fields.splice(key, 1);
    };
    $scope.addFilterOne = function (index) {
        if (!$scope.rule.parser.filter[index].fields) {
            $scope.rule.parser.filter[index].fields = [];
        }
        $scope.rule.parser.filter[index].fields.push('');
    };
    $scope.removeFilterOne = function (parent, index) {
        $scope.rule.parser.filter[parent].fields.splice(index, 1);
    };

    $scope.addFilterDefaultTuple = function (index) {
        var fields = $scope.rule.parser.filter[index].default.fields;
        if (!fields) {
            $scope.rule.parser.filter[index].default.fields = fields = [];
        }
        fields.push(['', '']);
    };
    $scope.removeFilterDefaultTuple = function (index, key) {
        $scope.rule.parser.filter[index].default.fields.splice(key, 1);
    };
    $scope.addFilterDefaultOne = function (index) {
        if (!$scope.rule.parser.filter[index].default.fields) {
            $scope.rule.parser.filter[index].default.fields = [];
        }
        $scope.rule.parser.filter[index].default.fields.push('');
    };
    $scope.removeFilterDefaultOne = function (parent, index) {
        $scope.rule.parser.filter[parent].default.fields.splice(index, 1);
    };

    $scope.addCase = function (index) {
        if (!$scope.rule.parser.filter[index].cases) {
            $scope.rule.parser.filter[index].cases = [];
        }
        $scope.rule.parser.filter[index].cases.push({
            name: 'case',
            rule: {
                name: 'addFields',
                fields: [['', '']]
            }
        });
    };
    $scope.removeCase = function (findex, index) {
        $scope.rule.parser.filter[findex].cases.splice(index, 1);
    };
    $scope.caseChange = function (fIndex, index) {
        reParserChange($scope.rule.parser.filter[fIndex].cases[index].rule);
    };
    $scope.addCaseTuple = function (fIndex, index) {

        var fields = $scope.rule.parser.filter[fIndex].cases[index].rule.fields;
        if (!fields || !angular.isArray(fields)) {
            $scope.rule.parser.filter[fIndex].cases[index].rule.fields = fields = [];
        }
        fields.push(['', '']);
    };
    $scope.removeCaseTuple = function (fIndex, index, key) {
        $scope.rule.parser.filter[fIndex].cases[index].rule.fields.splice(key, 1);
    };
    $scope.addCaseOne = function (fIndex, index) {
        if (!$scope.rule.parser.filter[fIndex].cases[index].rule.fields) {
            $scope.rule.parser.filter[fIndex].cases[index].rule.fields = [];
        }
        $scope.rule.parser.filter[fIndex].cases[index].rule.fields.push('');
    };
    $scope.removeCaseOne = function (fIndex, cindex, index) {
        $scope.rule.parser.filter[fIndex].cases[cindex].rule.fields.splice(index, 1);
    };

    $scope.resetPreview = function () {
        $scope.rule.properties = [];
    };
    $scope.preview = function (form) {

        if (!form.$submitted) {
            form.$submitted = true;
        }

        if (!(form.$valid && (!$scope.rule.parser.fields || $scope.rule.parser.fields.length > 0))) {
            return false;
        }

        //  let sample = $scope.rule.sample.split('#next#');

        $scope.toRule();
        Parser.properties($scope.rule, function (data) {
            if (!data) {
                console.log(data);
            } else {
                var properties = data[0];
                console.log(properties);
                $scope.rule.properties = [];

                for (var i in properties) {
                    if (properties[i].hasOwnProperty('key')) {
                        $scope.rule.properties.push(properties[i]);
                    }
                }

                // properties.foreach((key, value) => {
                //     console.log(key, value);
                //     $scope.rule.properties.push(value);
                // });

                for (var _i = 0; _i < $scope.rule.properties.length; _i++) {
                    if ($scope.rule.properties[_i].hasOwnProperty('key')) {
                        var subtype = $scope.rule.properties[_i].type.match(/list\[(.*)]/);
                        if (subtype && subtype.length >= 2) {
                            $scope.rule.properties[_i].type = 'list';
                            $scope.rule.properties[_i].subtype = subtype[1];
                        }
                    }
                }

                $scope.datas = data[1];
            }
            $scope.fromRule();

            $('#log_property_list').show();
            //$('#op_save_log_rule_button').show()
        });
    };

    /**
     * 构造 字段属性
     */
    $scope.addProperty = function () {
        $scope.rule.properties.push({
            key: '',
            name: '',
            type: 'object',
            sample: ''
        });
    };

    /**
     * 删除字段属性
     * @param index
     */
    $scope.deleteProperty = function (index) {
        $scope.rule.properties.splice(index, 1);
    };

    /**
     * 刷新字段属性列表的index属性
     * @param property_index 删除的字段属性index
     */
    $scope.properties_index_refresh = function (property_index) {
        var properties_count = $('#properties').children('div').length;

        var _loop = function _loop(index) {
            $.each($('#properties').find('input[name^=\'properties[' + index + ']\'],select[name^=\'properties[' + index + ']\']'), function (key, value) {
                if (key == 0) $(value).closest('.op_event_relate').attr('property_index', index - 1);
                $(value).attr('name', value.name.replace(index, index - 1));
            });
        };

        for (var index = property_index + 1; index <= properties_count; index++) {
            _loop(index);
        }
    };

    $scope.log_property_list_clean = function () {
        $('#properties').children().remove();
        $('#log_property_list').hide();
    };
    //下载配置文件
    $scope.downloadFile = function (sqlItemId) {
        Parser.download({ id: sqlItemId }, function (response) {
            var fileName = '解析规则';
            fileName = decodeURI(fileName);
            var url = URL.createObjectURL(new Blob([response.data]));
            var a = document.createElement('a');
            document.body.appendChild(a); //此处增加了将创建的添加到body当中
            a.href = url;
            a.download = fileName + '-' + sqlItemId + '.json';
            a.target = '_blank';
            a.click();
            a.remove(); //将a标签移除
        }, function (response) {});
    };
}]);
//# sourceMappingURL=parser.js.map
