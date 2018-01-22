'use strict';

app.controller('AnalyzerController', ['$scope', '$rootScope', '$location', 'Modeling', 'DataSource', 'Analyzer', 'Util', function ($scope, $rootScope, $location, Modeling, DataSource, Analyzer, Util) {

    $scope.aceLoaded = function (_editor) {
        // Options
        //_editor.setReadOnly(true);
    };
    $scope.aceValue = 'Foobar';
    $scope.aceChanged = function (e) {
        //
    };

    $scope.previewData = {};
    //解析规则
    $scope.channels = DataSource.byType({ type: "channel" });
    //分析规则
    $scope.analyzers = Analyzer.query();
    //数据模型
    $scope.modelings = Modeling.queryByType({ type: 'model' });
    /**
     * 构造 字段属性
     */
    $scope.addProperty = function () {
        if ($scope.rule.channel) {
            if (!$scope.rule.data.fromFields) {
                $scope.rule.data.fromFields = [];
            }
            $scope.rule.data.fromFields.push(['', 'string']);
        } else {
            $scope.confirm = {
                title: '提示',
                message: "您未选择解析规则，确认添加字段么？",
                ok: function ok() {
                    $scope.rule.data.fromFields.push(['', 'string']);
                    $('#commonConfirmModal').modal('hide');
                },
                cancel: function cancel() {
                    $('#commonConfirmModal').modal('hide');
                }
            };
            $('#commonConfirmModal').modal('show');
        }
    };
    /**
     * 删除字段属性
     * @param index
     */
    $scope.deleteProperty = function (index) {
        $scope.rule.data.fromFields.splice(index, 1);
    };

    $scope.addHashField = function () {
        if (!$scope.rule.data.cols) {
            $scope.rule.data.cols = [];
        }
        $scope.rule.data.cols.push(['', 'null', '', 'null']);
    };

    $scope.deleteHashField = function (index) {
        $scope.rule.data.cols.splice(index, 1);
    };

    $scope.channelChange = function () {
        $scope.resolver = $scope.channels.filter(function (r) {
            return r.id === $scope.rule.channel;
        }).shift();
        $scope.timefields = $scope.resolver.properties.filter(function (r) {
            return r.type === 'datetime' || r.type === 'long';
        });
    };

    $scope.changeFieldName = function (index) {
        var fieldName = $scope.rule.data.fromFields[index][0];
        if ($scope.resolver && $scope.resolver.properties) {
            var p = $scope.resolver.properties.filter(function (p) {
                return p.key === fieldName;
            }).shift();
            if (p) {
                $scope.rule.data.fromFields[index][1] = p.type;
            }
        }
    };
    $scope.changeVecFiledName = function (index) {
        var fieldName = $scope.rule.data.cols[index][0];
        if ($scope.resolver && $scope.resolver.properties) {
            var p = $scope.resolver.properties.filter(function (p) {
                return p.key === fieldName;
            }).shift();
            if (p) {
                if (p.type == 'string') {
                    $scope.rule.data.cols[index][1] = "simhash";
                } else if (p.type == "integer" || p.type == "long" || p.type == "float" || p.type == "double") {
                    $scope.rule.data.cols[index][1] = "null";
                } else {
                    $scope.confirm = {
                        title: "错误",
                        message: "字段[" + fieldName + "]类型为[" + p.type + "],不可参与向量化",
                        ok: function ok() {
                            $scope.rule.data.cols[index][0] = "";
                        }
                    };
                }
            }
        }
    };

    $scope.addFilter = function () {
        if (!$scope.rule.data.filter) {
            $scope.rule.data.filter = [];
        }
        $scope.rule.data.filter.push({
            name: 'streamRedirect',
            cases: []
        });
        $scope.addCase($scope.rule.data.filter.length - 1);
    };
    $scope.removeFilter = function (index) {
        $scope.rule.data.filter.splice(index, 1);
    };
    $scope.addCase = function (index) {
        if (!$scope.rule.data.filter[index].cases) {
            $scope.rule.data.filter[index].cases = [];
        }
        $scope.rule.data.filter[index].cases.push({
            name: 'streamCase',
            rule: {
                name: 'streamAnalyzer'
            }
        });
    };

    $scope.removeCase = function (findex, index) {
        $scope.rule.data.filter[findex].cases.splice(index, 1);
    };
    $scope.caseChange = function (fIndex, index) {
        delete $scope.rule.data.filter[fIndex].cases[index].rule.ref;
    };

    $scope.validateForm = function (formValid) {
        if (!formValid) return false;
        if (!formValid.$valid) {
            return false;
        }
        if (!formValid.$submitted) {
            formValid.$submitted = true;
        }
        /*if ($scope.rule.data.name === 'sql') {
            $scope.resolver = $scope.channels.filter(p => p.id === $scope.rule.channel).shift();
            if (!$scope.resolver) {
                $scope.message = {
                    status: '500',
                    title: '错误！',
                    message: '数据一致性错误，请重新选择解析规则'
                };
                return false;
            }
            if ($scope.rule.data.fromFields.length == 0) {
                return false;
            }
            if (!$scope.validateSql()) {
                return false;
            }
        }*/
        return true;
    };

    $scope.saveRule = function (formValid) {
        if (!$scope.validateForm(formValid)) {
            return;
        }
        /*if ($scope.rule.data.timeCharacteristic == 'ProcessingTime' && $scope.rule.data.name == 'sql') {
            $scope.rule.data.timeField = null;
            $scope.rule.data.maxOutOfOrderness = null;
        }*/
        Analyzer.save($scope.rule, function (rt) {
            $scope.message = rt;
            if ($scope.message.status == '200') {
                $scope.reload();
                $scope.show();
                $('.hla-widget-add-table').slideUp();
                $scope.reload();
            }
        });
    };
    $scope.showPreview = false;
    //预览函数
    $scope.preview = function (formValid) {
        //校验字段开始
        delete $scope.message;
        /*if ($scope.rule.data.name == 'sql') {
            let keys = $scope.resolver.properties.map(k => k.key);//解析规则中的key
            let fields = [];
            $scope.rule.data.fromFields.forEach(f => {
                if (!keys.includes(f[0])) {
                    fields.push(f[0]);
                }
            });
            if (fields.length > 0) {
                $scope.message = {
                    status: '400',
                    title: "警告！",
                    message: '解析规则[' + $scope.resolver.name + ']中不存在字段[' + fields.join(",") + '],请确认配置是否正确'
                };
            }
            if ($scope.timefields && $scope.rule.data.timeField) {
                let timefield = $scope.timefields.filter(t => t.key === $scope.rule.data.timeField).shift();
                if (!timefield) {
                    let message = '解析规则[' + $scope.resolver.name + ']中不存在您填写的时间字段[' + $scope.rule.data.timeField + ']，请确认字段是否为时间类型'
                    if ($scope.message) {
                        message = '（1）' + $scope.message.message + '；（2）' + message;
                    }
                    $scope.message = {
                        status: '400',
                        title: "警告！",
                        message: message
                    };
                  }
            }
            //校验字段结束
          }*/

        if (!$scope.validateForm(formValid)) {
            return;
        }
        Analyzer.preview($scope.rule, function (data) {
            if (data.status && data.status == 500) {
                $scope.message = {
                    status: '500',
                    title: '错误！',
                    message: data.message
                };
            } else {
                // delete $scope.message;
                $scope.previewData = data;
                $scope.showPreview = true;
            }
        });
    };
    $scope.reload = function () {
        $scope.list = {};
        $scope.list = Analyzer.get($scope.page);
    };
    //
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
                    data: {
                        name: 'sql',
                        timeSeriesModel: {
                            modelType: 'ADDITIVE'
                        },
                        timeCharacteristic: 'ProcessingTime',
                        maxOutOfOrderness: 10000,
                        fromFields: []
                    }
                };
                //TODO get form data
                Util.resetFormValidateState($scope.add_form);
                edit.slideToggle();
                search.slideUp();
                list.slideToggle();
                upload_table.slideUp();
                break;
            case 'edit':
                delete $scope.message;
                Analyzer.get({ id: id }, function (data) {
                    if (data.data && data.channel) {
                        $scope.resolver = $scope.channels.filter(function (p) {
                            return p.id === data.channel;
                        }).shift();
                        if ($scope.resolver && $scope.resolver.properties) {
                            $scope.timefields = $scope.resolver.properties.filter(function (r) {
                                return r.type === 'datetime' || r.type === 'long';
                            });
                        }
                    }
                    $scope.rule = data;
                });
                Util.resetFormValidateState($scope.add_form);
                edit.slideDown();
                search.slideUp();
                upload_table.slideUp();
                list.slideUp();
                break;
            case 'copy':
                delete $scope.message;
                Analyzer.get({ id: id }, function (data) {
                    $scope.rule = data;
                    delete $scope.rule.id;
                    $scope.rule.name = $scope.rule.name + '_copy';
                    $scope.resolver = $scope.channels.filter(function (r) {
                        return r.id === $scope.rule.channel;
                    }).shift();
                    if ($scope.resolver) {
                        $scope.timefields = $scope.resolver.properties.filter(function (r) {
                            return r.type === 'datetime' || r.type === 'long';
                        });
                    }
                    //                    $scope.fromRule();
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
                $scope.page.search.name = null;
                break;
            default:
                edit.slideUp();
                search.slideUp();
                upload_table.slideUp();
                list.slideDown();
        }
    }; //

    $scope.downloadFile = function (sqlItemId) {

        //        console.log(sqlItemId);

        Analyzer.download({ id: sqlItemId }, function (response) {
            var fileName = 'streamSql分析规则';
            fileName = decodeURI(fileName);
            var url = URL.createObjectURL(new Blob([response.data]));
            var a = document.createElement('a');
            document.body.appendChild(a); //此处增加了将创建的添加到body当中
            a.href = url;
            a.download = fileName + '-' + sqlItemId + '.json';
            a.target = '_blank';
            a.click();
            a.remove(); //将a标签移除
        }, function (response) {
            //            console.log(response);
        });
    };

    $scope.uploadFile = function () {
        delete $scope.message;
        var $edit_table = $('.hla-widget-add-table');
        var $search_table = $('.hla-widget-search-table');
        var upload_table = $('.hla-widget-upload-table');
        var list = $('.hla-widget-data-table');
        var file = $scope.myFile;
        var reader = new FileReader();
        reader.onloadend = function (e) {
            //            console.log(this.result);
            $scope.rule = angular.fromJson(this.result);

            try {
                //  delete    $scope.rule.id;
                var flag = false;

                //                $scope.fromRule();
                $edit_table.slideDown();
                $search_table.slideUp();
                upload_table.slideUp();
                list.slideUp();
                delete $scope.myFile;
                //                console.log(fileInput);
                $scope.$apply();
            } catch (e) {
                //                console.log(e);
                $scope.message = {
                    status: '500',
                    title: '错误！',
                    message: '数据解析出错,你检查你的数据文件是否是正确的解析规则内容!'
                };
            }
        };

        try {
            reader.readAsText(file);
        } catch (e) {
            $scope.message = {
                status: '500',
                title: '错误！',
                message: '数据解析出错,你检查你的数据文件是否正确!'
            };
        }
    }; //


    $scope.validateSql = function () {
        var sql = $scope.rule.data.sql;
        if (sql) {
            sql = sql.toUpperCase();
            if (sql.indexOf("SELECT") == -1 || sql.indexOf("FROM") == -1) {
                $scope.message = {
                    status: '500',
                    title: '错误！',
                    message: "SQL语句不合法，请填写正确的SQL"
                };
                return false;
            }
            return true;
        }
    };

    $scope.delete = function (id) {
        $scope.delete_id = id;
        $('#confirmModal').modal('show');
    };
    //删除确定
    $scope.confirm_yes = function () {

        Analyzer.delete({ id: $scope.delete_id }, function (data) {
            $scope.message = data;
            $('#confirmModal').modal('hide');
            $scope.reload();
        });
    };
    $('#confirmModal').find('.op_yes').off().click(function () {
        $scope.confirm_yes();
    });

    $scope.typeChange = function () {
        //分析规则
        console.log($scope.rule.type + "====================" + $scope.type);
        Modeling.queryByType({ type: $scope.rule.type }, function (rt) {
            $scope.modelings = rt;
        });
    };
}]);
//# sourceMappingURL=analyzer.js.map
