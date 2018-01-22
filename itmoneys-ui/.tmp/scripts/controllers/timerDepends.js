'use strict';

app.controller('TimerDependsController', ['$scope', '$rootScope', '$location', 'Util', 'TimerDepends', 'Channel', 'Modeling', 'Knowledge', 'Collector', 'DataSource', function ($scope, $rootScope, $location, Util, TimerDepends, Channel, Modeling, Knowledge, Collector, DataSource) {
    $rootScope.$path = $location.path.bind($location);
    $scope.datasources = DataSource.query();
    $scope.channels = Channel.query();
    $scope.modelings = Modeling.query();
    $scope.knowledges = Knowledge.query();
    $scope.dependsString = "[{\"jobId\": \"name0\"},{\"jobId\": \"name1\",\"parentId\": \"name0\"}]";
    // $scope.list = $scope.channels;
    // ALTER TABLE TimerDepends ADD EXE_STATE INT DEFAULT 0;
    // ALTER TABLE TimerDepends ADD JOB_TYPE VARCHAR(20);
    // ALTER TABLE TimerDepends ADD TIMER_ACTION VARCHAR(20);
    console.log($scope.channels);
    console.log($scope.modelings);
    console.log($scope.knowledges);

    var formatStartTime = function formatStartTime(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        var d = date.getDate();
        d = d < 10 ? '0' + d : d;
        var h = date.getHours() + 1;
        h = h < 10 ? '0' + h : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? '0' + minute : minute;
        second = second < 10 ? '0' + second : second;
        return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
    };

    var formatEndTime = function formatEndTime(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        var d = date.getDate();
        d = d < 10 ? '0' + d : d;
        var h = date.getHours() + 2;
        h = h < 10 ? '0' + h : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? '0' + minute : minute;
        second = second < 10 ? '0' + second : second;
        return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
    };

    var $confirmModal = $('#confirmModal');

    var TimerDefault = {
        timer: {
            enable: false,
            repeat: false,
            name: 'single',
            first: formatStartTime(new Date()),
            end: formatEndTime(new Date()),
            action: 'START'
        }
    };
    //采集器
    $scope.collectors = Collector.query();
    // $scope.timers = TimerDepends.query();
    //重置表单
    $scope.resetForm = function () {
        $scope.timerDepends = angular.copy(TimerDefault);
        Util.resetFormValidateState($scope.ds_form);
    };
    $scope.resetForm();

    //保存
    $scope.saveTimer = function (formValid) {
        if (!formValid) return false;
        console.log(angular.fromJson($scope.dependsString));
        // $scope.timerDepends.timerType = $scope.timerDepends.data.name;
        $scope.timerDepends.depends = angular.fromJson($scope.dependsString);
        var dataForm = $scope.timerDepends;
        TimerDepends.save(dataForm, function (rt) {
            $scope.message = rt;
            if ($scope.message.status === '200') {
                $scope.reload();
                $('.hla-widget-add-table').slideUp();
            }
        });
    };

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
            $scope.timerDepends = angular.fromJson(this.result);
            $edit_table.slideDown();
            $search_table.slideUp();
            upload_table.slideUp();
            $scope.$apply();
        };
        reader.readAsText(file);
    };

    // $("#fileInput2").on("fileuploaded", function (event, data, previewId, index) {
    //     console.log(data);
    // }

    // $scope.uploadFile2 = function () {
    //     // let $edit_table = $('.hla-widget-add-table');
    //     // let $search_table = $('.hla-widget-search-table');
    //     // let upload_table = $('.hla-widget-upload-table');
    //     let file = $scope.myFile2;
    //     let reader = new FileReader();
    //     reader.onload = function () {
    //         var timerDepends = angular.fromJson(this.result);
    //         console.log(timerDepends);
    //         // $edit_table.slideDown();
    //         // $search_table.slideUp();
    //         // upload_table.slideUp();
    //         $scope.$apply();
    //     };
    //     reader.readAsText(file);
    // };

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
                TimerDepends.get({ id: id }, function (ds) {
                    console.log(ds);
                    $scope.timerDepends = ds;
                });
                $edit_table.slideDown();
                $search_table.slideUp();
                upload_table.slideUp();

                break;
            case 'copy':
                TimerDepends.get({ id: id }, function (ds) {
                    $scope.timerDepends = ds;
                    $scope.timerDepends.name = $scope.timer.name + '_copy';
                    delete $scope.timerDepends.id;
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
        TimerDepends.delete({ id: $scope.deleteId }, function (rt) {
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
        TimerDepends.delete({ id: $scope.deleteId }, function (rt) {
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
        $scope.list = TimerDepends.get($scope.page);
        console.log($scope.list);
    };

    //文件下载
    $scope.downloadFile = function (itemId) {

        //        console.log(sqlItemId);

        TimerDepends.download({ id: itemId }, function (response) {
            var fileName = '定时器下载';
            fileName = decodeURI(fileName);
            var url = URL.createObjectURL(new Blob([response.data]));
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
//# sourceMappingURL=timerDepends.js.map
