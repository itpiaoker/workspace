'use strict';

app.controller('WriterController', ['$scope', '$rootScope', '$location', 'Util', 'Writer', 'Collector', function ($scope, $rootScope, $location, Util, Writer, Collector) {
    $rootScope.$path = $location.path.bind($location);
    $scope.encodings = ['UTF-8', 'GBK', 'GB18030', 'GB2312', 'BIG5', 'UNICODE', 'ASCII', 'ISO-8859-1'];
    var $confirmModal = $('#confirmModal');
    var WriterDefault = {
        data: {
            name: 'es5',
            number_of_shards: 5,
            number_of_replicas: 0,
            cache: 1000,
            date_detection: true,
            persisRef: {
                name: 'none'
            },
            protocol: {
                name: 'udp'
            },
            contentType: {
                name: 'json',
                contentType: {
                    name: 'json'
                }
            },
            authentication: 'NONE',
            properties: {
                connectTimeout: '300'
            }
        }
    };
    //采集器
    $scope.collectors = Collector.query();
    $scope.writers = Writer.query();
    //重置表单
    $scope.resetForm = function () {
        $scope.writer = angular.copy(WriterDefault);
        Util.resetFormValidateState($scope.ds_form);
    };

    $scope.resetForm();

    $scope.changeName = function () {
        switch ($scope.writer.data.name) {
            case 'hdfs':
                $scope.writer.data.authentication = 'NONE';
            case 'ftp':
            case 'sftp':

                $scope.writer.data.path = angular.copy({
                    name: 'file',
                    persisRef: {
                        name: 'none'
                    },
                    contentType: {
                        name: 'json',
                        contentType: {
                            name: 'json'
                        }
                    }
                });
                break;
            default:
                delete $scope.writer.data.path;

        }
    };
    $scope.contentTypeChange = function () {
        switch ($scope.writer.data.contentType.name) {
            case 'syslog':
                $scope.writer.data.contentType.contentType = angular.copy({ name: 'json' });
                $scope.writer.data.contentType.syslogFormat = angular.copy({ facility: '0', level: '0', priorityType: "DATA", timestampType: "DATA" });
                break;
            default:

        }
    };
    $scope.pathContentTypeChange = function () {
        switch ($scope.writer.data.path.contentType.name) {
            case 'syslog':
                $scope.writer.data.path.contentType.contentType = angular.copy({ name: 'json' });
                $scope.writer.data.path.contentType.syslogFormat = angular.copy({ facility: '0', level: '0', priorityType: "DATA" });
                break;
            default:

        }
    };

    $scope.timestampTypeChange = function () {
        switch ($scope.writer.data.contentType.syslogFormat.timestampType) {
            case 'VAL':
                $scope.writer.data.contentType.syslogFormat.timestamp = new Date();
                break;
            default:
                delete $scope.writer.data.contentType.syslogFormat.timestamp;
        }
    };

    $scope.pathTimestampTypeChange = function () {
        switch ($scope.writer.data.path.contentType.syslogFormat.timestampType) {
            case 'VAL':
                $scope.writer.data.path.contentType.syslogFormat.timestamp = new Date();
                break;
            default:
                delete $scope.writer.data.path.contentType.syslogFormat.timestamp;
        }
    };
    //保存
    $scope.saveWriter = function (formValid) {

        if (!formValid) return false;
        var dataForm = $scope.writer;
        Writer.save(dataForm, function (rt) {
            $scope.message = rt;
            if ($scope.message.status == '200') {
                $scope.reload();
                $('.hla-widget-add-table').slideUp();
            }
        });
    };
    $scope.changeDBType = function () {
        var type = $scope.writer.data.protocol;
        var port = null;
        switch (type) {
            case 'mysql':
                port = 3306;
                $scope.writer.data.driver = "com.mysql.jdbc.Driver";
                break;
            case 'oracle':
                port = 1521;
                $scope.writer.data.driver = "oracle.jdbc.driver.OracleDriver";
                break;
            case 'sqlserver':
                port = 1433;
                $scope.writer.data.driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
                break;
            case 'DB2':
                $scope.writer.data.driver = "com.ibm.db2.jcc.DB2Driver";
                port = 50000;
                break;
            case 'sybase':
                port = 5000;
                $scope.writer.data.driver = "net.sourceforge.jtds.jdbc.Driver";
                break;
            case 'postgresql':
                $scope.writer.data.driver = "org.postgresql.Driver";
                port = 5432;
                break;
            case 'sqlite':
                $scope.writer.data.driver = "org.sqlite.JDBC";
                port = 0;
                break;
            case 'derby':
                $scope.writer.data.driver = "org.apache.derby.jdbc.EmbeddedDriver";
                port = 1527;
                break;
            case 'phoenix':
                $scope.writer.data.driver = "org.apache.phoenix.jdbc.PhoenixDriver";
                port = 2181;
                break;
        }
        $scope.writer.data.port = port;
    };
    $scope.uploadFile = function () {
        var $edit_table = $('.hla-widget-add-table');
        var $search_table = $('.hla-widget-search-table');
        var upload_table = $('.hla-widget-upload-table');
        var file = $scope.myFile;
        var reader = new FileReader();
        reader.onload = function (e) {
            $scope.writer = angular.fromJson(this.result);
            //  delete   $scope.writer.id;
            if ($scope.writer.writeType == 'forward') $scope.collectorChange();
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
                Writer.get({
                    id: id
                }, function (ds) {
                    $scope.writer = ds;
                    if ($scope.writer.writeType == 'forward') {
                        $scope.collectorChange();
                    }
                });
                $edit_table.slideDown();
                $search_table.slideUp();
                upload_table.slideUp();

                break;
            case 'copy':
                Writer.get({
                    id: id
                }, function (ds) {
                    $scope.writer = ds;
                    $scope.writer.name = $scope.writer.name + '_copy';
                    delete $scope.writer.id;
                    if ($scope.writer.writeType == 'forward') $scope.collectorChange();
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
        Writer.delete({
            id: $scope.deleteId
        }, function (rt) {
            $scope.message = rt;
            $scope.reload();
        });
        $confirmModal.modal('hide');
    };
    $confirmModal.find('.op_yes').off().click(function () {
        $scope.confirm_yes();
    });
    $scope.reload = function () {

        $scope.list = Writer.get($scope.page);
    };

    $scope.addZkAddress = function () {
        var zookeeperConnect = $scope.writer.data.zookeeperConnect;
        if (!zookeeperConnect) {
            zookeeperConnect = $scope.writer.data.zookeeperConnect = [];
        }
        zookeeperConnect.push(['', 2181]);
    };
    $scope.removeZkAddress = function (index) {
        $scope.writer.data.zookeeperConnect.splice(index, 1);
    };
    $scope.addKafAddress = function () {
        var kafAddresses = $scope.writer.data.metadataBrokerList;
        if (!kafAddresses) {
            kafAddresses = $scope.writer.data.metadataBrokerList = [];
        }
        kafAddresses.push(['', 9092]);
    };
    $scope.removeKafAddress = function (index) {
        $scope.writer.data.metadataBrokerList.splice(index, 1);
    };

    $scope.addHostPort = function () {
        var hostPort = $scope.writer.data.hostPorts;
        if (!hostPort) {
            hostPort = $scope.writer.data.hostPorts = [];
        }
        hostPort.push(['', 9300]);
    };
    $scope.removeHostPort = function (index) {
        $scope.writer.data.hostPorts.splice(index, 1);
    };

    $scope.addWriter = function () {
        var writers = $scope.writer.data.writers;
        if (!writers) {
            writers = $scope.writer.data.writers = [];
        }
        writers.push(['', '']);
    };
    $scope.removeWriter = function (index) {
        $scope.writer.data.writers.splice(index, 1);
    };
    $scope.collectorChange = function () {

        var data = $scope.writer.data;
        if (data.id) {
            var collector = null;
            if ($scope.collectors.$resolved) {
                angular.forEach($scope.collectors, function (obj, key) {
                    if (obj.id == data.id) {
                        collector = obj;
                        return true;
                    }
                });
            }

            if (collector) {
                data.host = collector.host;
                data.port = collector.port;
            }
        }
    };
    $scope.caseWriterChange = function (index) {

        //console.log($scope.writer.data.writers[index]);
        var id = $scope.writer.data.writers[index][1].id;
        //console.log(id);
        if (id) {
            var writer = null;
            if ($scope.writers.$resolved) {
                angular.forEach($scope.writers, function (obj, key) {
                    if (obj.id == id) {
                        writer = obj.data;
                        return true;
                    }
                });
            }

            if (writer) {
                $scope.writer.data.writers[index][1] = writer;
                $scope.writer.data.writers[index][1].id = id;
            }
        }
    };
    $scope.defaultWriterChange = function () {

        var id = $scope.writer.data.default.id;
        if (id) {
            var writer = null;
            if ($scope.writers.$resolved) {
                angular.forEach($scope.writers, function (obj, key) {
                    if (obj.id == id) {
                        writer = obj.data;
                        return true;
                    }
                });
            }

            if (writer) {
                $scope.writer.data.default = writer;
                $scope.writer.data.default.id = id;
            }
        }
    };
    // 文件下载
    $scope.downloadFile = function (itemId) {

        //        console.log(sqlItemId);

        Writer.download({
            id: itemId
        }, function (response) {
            var fileName = '存储配置';
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
//# sourceMappingURL=writer.js.map
