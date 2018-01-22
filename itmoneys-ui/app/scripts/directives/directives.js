'use strict';
/**
 * a标签二次点击刷新路由
 * 指令:reload-when-click,必须指定 ng-href 或者 href 属性
 * 用例:<a ng-href=\'#/action\' reload-when-click>action</>
 */
app.directive('reloadWhenClick', ['$location', '$route',
    function ($location, $route) {
        return {
            restrict: 'A',
            link: function ($scope, element, attrs) {
                let href = attrs.ngHref || element.attr('href');
                element.click(function () {
                    if (href.substring(2) == $location.path()) {
                        $route.reload();
                    }
                });
            }
        };
    }
]);


app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            let model = $parse(attrs.fileModel);
            let modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);
app.directive('fileModel2', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            let model = $parse(attrs.fileModel2);
            let modelSetter = model.assign;

            element.bind('change', function () {
                // let file = $scope.myFile2;
                // let reader = new FileReader();
                // reader.onload = function () {
                //     var timer = angular.fromJson(this.result);
                //     console.log(timer);
                //     $edit_table.slideDown();
                //     $search_table.slideUp();
                //     upload_table.slideUp();
                //     $scope.$apply();
                // };
                // reader.readAsText(this);



                // console.log(element)
                // console.log(attrs)
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                    console.log(modelSetter)
                });
            });
        }
    };
}]);
app.directive('fileInput', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            $(element).fileinput({
                'showUpload': false,
                'previewFileType': 'text',
                language: 'zh', //设置语言
                allowedFileExtensions: ['json', 'txt'],//接收的文件后缀
                dropZoneEnabled: true,//是否显示拖拽区域

            });
        }
    };
});
app.directive('fileInput2', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            $(element).fileinput({
                'showUpload': false,
                'previewFileType': 'text',
                language: 'zh', //设置语言
                allowedFileExtensions: ['json', 'txt'],//接收的文件后缀
                dropZoneEnabled: true,//是否显示拖拽区域

            });
        }
    };
});
app.directive('chart', function () {
    return {
        restrict: 'E',
        scope: {
            width: '@',
            height: '@',
            margin: '@',
            data: '='
        },
        controller: function ($scope, $element, $attrs, TimeSeriesChart) {
            let chart = TimeSeriesChart.getChart($element[0], {
                width: $scope.width,
                height: $scope.height,
                margin: $scope.margin
            });
            chart.render($scope.data);
        }
    };
});
app.directive('sparkLine', function () {
    return {
        restrict: 'E',
        scope: {
            width: '@',
            height: '@',
            data: '='
        },
        controller: function ($scope, $element, $attrs, SparkLineChart) {
            let chart = SparkLineChart.getChart($element[0], {
                width: $scope.width,
                height: $scope.height,
            });
            chart.render($scope.data);
        }
    };
});

app.directive('page', [function () {
    return {
        replace: true,
        template: '<div class=\'hla-widget-foot\'>\
            <ul class=\'pagination pagination-top\'>\
                <li>\
                    <span class=\'bor-none pad-0\'>每页显示 \
                        <select ng-model=\'page.limit\' ng-change=\'load()\' ng-blur=\'first()\' ng-options=\'x for x in [10,20,50]\'></select>\
                    </span>\
                </li>\
                <li ng-if=\'page.current<=1\' class=\'disabled\'><span class=\'op_prev bor-r-5\'><i class=\'glyphicon glyphicon-chevron-left\'></i></span></li>\
                <li ng-if=\'page.current>1\'><span class=\'op_prev bor-r-5\' style=\'cursor:pointer;\' ng-click=\'prev()\'><i class=\'glyphicon glyphicon-chevron-left\'></i></span></li>\
                <li><span class=\'op_page_info pad-5 bor-none\'>{{page.current}}/{{page.total}}</span></li>\
                <li ng-if=\'page.current>=page.total\' class=\'disabled\'><span class=\'op_next bor-r-5\'><i class=\'glyphicon glyphicon-chevron-right\'></i></span></li>\
                <li ng-if=\'page.current<page.total\'><span class=\'op_next bor-r-5\' style=\'cursor:pointer;\' ng-click=\'next()\'><i class=\'glyphicon glyphicon-chevron-right\'></i></span></li>\
            </ul>\
            <ul class=\'pagination pagination-bottom\'>\
                <li>显示第 {{page.start+1}} 到 {{(page.start+page.limit) < page.count? (page.start+page.limit):page.count}} 条数据 , 共 {{page.count}} 项</li>\
            </ul>\
        </div>',
        link: function (scope, ele, attrs) {

            scope.page = {
                start: 0,
                limit: 10,
                current: 1,
                count: 0,
                total: 1,
                search: {},
                order: 'desc',
                orderBy: 'create_time'
            };
            if (!scope[attrs.method]) {
                throw new Error('load method is undefined');
            }
            scope.first = function () {
                scope.page.start = 0;
                scope.page.current = 1;
                scope.load();
            };
            scope.prev = function () {
                if (scope.page.current > 1) {
                    scope.page.current--;
                    scope.page.start -= scope.page.limit;
                    scope.load();
                }
            };
            scope.next = function () {
                if (scope.page.current < scope.page.total) {
                    scope.page.start += scope.page.limit;
                    scope.page.current++;
                    scope.load();
                }
            };
            scope.check = function () {
                if (scope.page.current >= scope.page.total) {
                    scope.page.current = scope.page.total;
                    scope.page.start = (scope.page.current - 1) * scope.page.limit;
                } else if (scope.page.current <= 1) {
                    scope.page.current = 1;
                    scope.page.start = 0;
                }
            };
            scope.last = function () {
                scope.page.current = scope.page.total;
                scope.page.start = (scope.page.current - 1) * scope.page.limit;
                scope.load();
            };
            //调用
            scope.load = function (to) {


                to && (scope.page.current = to);


                scope[attrs.method]();
                scope.list.$promise.then(function (list) {

                    scope.page.count = list.totalCount;
                    scope.page.total = Math.ceil(list.totalCount / scope.page.limit);
                    if (scope.page.current > 1 && scope.page.current < scope.page.total) {
                        scope.pages = [
                            scope.page.current - 1,
                            scope.page.current,
                            scope.page.current + 1
                        ];
                    } else if (scope.page.current == 1 && scope.page.total > 1) {
                        scope.pages = [
                            scope.page.current,
                            scope.page.current + 1
                        ];
                    } else if (scope.page.current == scope.page.total && scope.page.total > 1) {
                        scope.pages = [
                            scope.page.current - 1,
                            scope.page.current
                        ];
                    }
                });
            };
            scope.load();
        }
    }
}]);


