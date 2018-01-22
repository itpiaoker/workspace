'use strict';

app.controller('TimerController', ['$scope', '$rootScope', '$location', 'Util', 'Timer', 'Channel', 'Modeling', 'Knowledge', 'Collector', 'DataSource', function ($scope, $rootScope, $location, Util, Timer, Channel, Modeling, Knowledge, Collector, DataSource) {
    $rootScope.$path = $location.path.bind($location);
    $scope.datasources = DataSource.query();
    $scope.channels = Channel.query();
    $scope.modelings = Modeling.query();
    $scope.knowledges = Knowledge.query();
    $scope.list = $scope.channels;
    // ALTER TABLE TIMER ADD EXE_STATE INT DEFAULT 0;
    // ALTER TABLE TIMER ADD JOB_TYPE VARCHAR(20);
    // ALTER TABLE TIMER ADD TIMER_ACTION VARCHAR(20);
    // console.log($scope.channels);
    // console.log($scope.modelings);
    // console.log($scope.knowledges);

    // const formatStartTime = function (date) {
    //     let y = date.getFullYear();
    //     let m = date.getMonth() + 1;
    //     m = m < 10 ? ('0' + m) : m;
    //     let d = date.getDate();
    //     d = d < 10 ? ('0' + d) : d;
    //     let h = date.getHours() + 1;
    //     h = h < 10 ? ('0' + h) : h;
    //     let minute = date.getMinutes();
    //     let second = date.getSeconds();
    //     minute = minute < 10 ? ('0' + minute) : minute;
    //     second = second < 10 ? ('0' + second) : second;
    //     return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
    // };

    $scope.index = 1;

    // const formatEndTime = function (date) {
    //     let y = date.getFullYear();
    //     let m = date.getMonth() + 1;
    //     m = m < 10 ? ('0' + m) : m;
    //     let d = date.getDate();
    //     d = d < 10 ? ('0' + d) : d;
    //     let h = date.getHours() + 2;
    //     h = h < 10 ? ('0' + h) : h;
    //     let minute = date.getMinutes();
    //     let second = date.getSeconds();
    //     minute = minute < 10 ? ('0' + minute) : minute;
    //     second = second < 10 ? ('0' + second) : second;
    //     return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
    // };

    var $confirmModal = $('#confirmModal');

    var TimerDefault = {
        timer: {
            // enable: false,
            isDepend: false,
            jobType: 'CHANNEL',
            // name: 'single',
            // first: formatStartTime(new Date()),
            // end: formatEndTime(new Date),
            action: 'START'
        }
    };
    //采集器
    $scope.collectors = Collector.query();
    $scope.timers = Timer.query();
    //重置表单
    $scope.resetForm = function () {
        $scope.timer = angular.copy(TimerDefault);
        Util.resetFormValidateState($scope.ds_form);
    };
    $scope.resetForm();

    //保存
    $scope.saveTimer = function (formValid) {
        if (!formValid) return false;
        // $scope.timer.timerType = $scope.timer.timer.name;
        var dataForm = $scope.timer;
        Timer.save(dataForm, function (rt) {
            $scope.message = rt;
            if ($scope.message.status === '200') {
                $scope.reload();
                $('.hla-widget-add-table').slideUp();
            }
        });
    };

    $scope.addFilterTuple = function (index) {
        console.log(index);
        // let fields = $scope.timer.timer.depends[index].dependsId;
        // if (!fields) {
        //     $scope.timer.timer.depends[index].dependsId = fields = []
        // }
        // fields.push(['', '']);
    };

    // $scope.addFilter = function () {
    //
    //     $scope.initFields(function () {
    //         if (!$scope.rule.parser.filter) {
    //             $scope.rule.parser.filter = []
    //         }
    //         $scope.rule.parser.filter.push({
    //             name: 'addFields',
    //             fields: [['', '']]
    //         })
    //     });
    //
    // };

    // //
    //
    // $scope.switchCategory = function (id){
    //     switch (id) {
    //         case 'channel' : ;
    //             $scope.list = $scope.channels;
    //             $scope.list.
    //             console.log(id);
    //             console.log($scope.list);
    //             break;
    //         case 'knowledge' : ;
    //             $scope.list = $scope.knowledges;
    //             console.log(id);
    //             console.log($scope.list);
    //             break;
    //         case 'modeling' : ;
    //             $scope.list = $scope.modelings;
    //             console.log(id);
    //             console.log($scope.list);
    //             break;
    //         default :
    //             $scope.list = $scope.channels;
    //             console.log(id);
    //             console.log($scope.list);
    //             break;
    //     }
    // }


    $scope.uploadFile = function () {
        var $edit_table = $('.hla-widget-add-table');
        var $search_table = $('.hla-widget-search-table');
        var upload_table = $('.hla-widget-upload-table');
        var file = $scope.myFile;
        var reader = new FileReader();
        reader.onload = function () {
            $scope.timer = angular.fromJson(this.result);
            $edit_table.slideDown();
            $search_table.slideUp();
            upload_table.slideUp();
            $scope.$apply();
        };
        reader.readAsText(file);
    };
    $scope.show = function (clazz, id) {
        delete $scope.message;
        var $edit_table = $('.hla-widget-add-table');
        var $search_table = $('.hla-widget-search-table');
        var upload_table = $('.hla-widget-upload-table');
        Util.resetFormValidateState($scope.ds_form);
        switch (clazz) {
            case 'upload':
                $scope.searchDataSourceForm = {};
                $edit_table.slideUp();
                $search_table.slideUp();
                upload_table.slideDown();
                break;
            case 'search':
                $scope.searchDataSourceForm = {};
                $edit_table.slideUp();
                $search_table.slideToggle();
                upload_table.slideUp();
                break;
            case 'add':
                $edit_table.slideToggle();
                $search_table.slideUp();
                upload_table.slideUp();
                $scope.resetForm();
                break;
            case 'edit':
                Timer.get({ id: id }, function (ds) {
                    console.log(ds);
                    $scope.timer = ds;
                });
                $edit_table.slideDown();
                $search_table.slideUp();
                upload_table.slideUp();

                break;
            case 'copy':
                Timer.get({ id: id }, function (ds) {
                    $scope.timer = ds;
                    $scope.timer.name = $scope.timer.name + '_copy';
                    delete $scope.timer.id;
                });
                $edit_table.slideDown();
                $search_table.slideUp();
                upload_table.slideUp();

                break;
            default:
                $edit_table.slideUp();
                $search_table.slideUp();
                upload_table.slideUp();
                break;
        }
    };

    //删除按钮
    $scope.delete = function (id) {
        $scope.deleteId = id;
        $confirmModal.modal('show');
    };

    //删除确定
    $scope.confirm_yes = function () {
        Timer.delete({ id: $scope.deleteId }, function (rt) {
            $scope.message = rt;
            $scope.reload();
        });
        $confirmModal.modal('hide');
    };

    $confirmModal.find('.op_yes').off().click(function () {
        $scope.confirm_yes();
    });

    //暂停按钮
    var $pauseConfirmModal = $('#pauseConfirmModal');

    //暂停按钮
    $scope.pause = function (id) {
        $scope.pauseId = id;
        $pauseConfirmModal.modal('show');
    };
    //暂停确定
    $scope.pauseConfirm_yes = function () {
        Timer.delete({ id: $scope.deleteId }, function (rt) {
            $scope.message = rt;
            $scope.reload();
        });
        $pauseConfirmModal.modal('hide');
    };
    //暂停确定
    $pauseConfirmModal.find('.op_yes').off().click(function () {
        $scope.pauseConfirm_yes();
    });

    //
    $scope.reload = function () {
        $scope.list = Timer.get($scope.page);
    };

    //文件下载
    $scope.downloadFile = function (itemId) {

        //        console.log(sqlItemId);

        Timer.download({ id: itemId }, function (response) {
            var fileName = '定时器下载';
            fileName = decodeURI(fileName);
            var url = URL.createObjectURL(new Blob([response.timer]));
            var a = document.createElement('a');
            document.body.appendChild(a); //此处增加了将创建的添加到body当中
            a.href = url;
            a.download = fileName + '-' + itemId + '.json';
            a.target = '_blank';
            a.click();
            a.remove(); //将a标签移除
        }, function (response) {
            //            console.log(response);
        });
    }; //
}]);
//# sourceMappingURL=timer.js.map
