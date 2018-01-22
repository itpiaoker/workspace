'use strict';

app.filter('ParserTypeName', function () {
    return function (type) {
        switch (type) {
            case 'nothing':
                return '不解析';
            case 'cef':
                return 'CEF';
            case 'delimit':
                return '分隔符';
            case 'delimitWithKeyMap':
                return '键值对';
            case 'xml':
                return 'XML';
            case 'json':
                return 'JSON';
            case 'regex':
                return '正则';
            case 'transfer':
                return '传输';
            default:
                return type.toUpperCase();
        }
    };
}).filter('AnalyzerTypeName', function () {
    return function (type) {
        switch (type) {
            case 'sql':
                return 'SQL';
            case 'model':
                return '生产模型';
            case 'analyzer':
                return '数据分析';
            default:
                return type.toUpperCase();
        }
    };
}).filter('channelTypeName', function () {
    return function (type) {
        switch (type) {
            case 'parser':
                return '数据解析';
            case 'analyzer':
                return '数据分析';
            default:
                return type.toUpperCase();
        }
    };
}).filter('SourceTypeName', function () {
    return function (type) {
        switch (type) {
            case 'es2':
                return 'ELASTICSEARCH-2.X';
            case 'es5':
                return 'ELASTICSEARCH-5.X';
            case 'es':
                return 'ELASTICSEARCH';
            case 'net':
                return '网络';
            case 'single-table':
                return '通道转数据表';
            case 'tuple':
                return '通道组合';
            case 'jdbc':
                return '数据库';
            case 'forward':
                return '数据转发';
            case 'file':
                return '本地文件';
            case 'switch':
                return '条件输出';
            default:
                return type.toUpperCase();
        }
    };
}).filter('writerTypeName', function () {
    return function (type) {
        switch (type) {
            case 'es2':
                return 'ELASTICSEARCH-2.X';
            case 'es5':
                return 'ELASTICSEARCH-5.X';
            case 'es':
                return 'ELASTICSEARCH';
            case 'net':
                return '网络';
            case 'jdbc':
                return '数据库';
            case 'forward':
                return '数据转发';
            case 'file':
                return '本地文件';
            case 'switch':
                return '条件输出';
            default:
                return type.toUpperCase();
        }
    };
}).filter('StatusName', function () {
    return function (status) {
        switch (status) {
            case 'RUNNING':
                return '运行';
            case 'STOPPING':
                return '正在停止';
            case 'STOPPED':
                return '停止';
            case 'READING_ERROR':
                return '读取数据异常';
            case 'WRITER_ERROR':
                return '写数据异常';
            case 'MONITOR_ERROR':
                return '停止【创建输入流异常】';
            case 'LEXER_ERROR':
                return '数据分析异常';
            case 'WORKER_STOPPED':
                return '采集器已关闭,或者不存在';
            case 'UNAVAILABLE':
                return '采集器已关闭,或者不存在';
            case 'PENDING':
                return '初始状态';
            case 'STARTING':
                return '正在启动';
            case 'FINISHED':
                return '完成';
            case 'NOT_EXEC':
                return '未执行';
            case 'FAIL':
                return '失败';
            default:
                return '未知';
        }
    };
});
