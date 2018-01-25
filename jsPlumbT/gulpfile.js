// 安装依赖
var gulp = require('gulp'),
less = require('gulp-less');
browserSync = require('browser-sync');

gulp.task('compileLess', function () {
    gulp.src('src/less/index.less')
        .pipe(less())
        .pipe(gulp.dest('src/css'));
});
// 设置任务---架设静态服务器
gulp.task('default', ['compileLess'], function () {
    browserSync.init({
        files:['**'],
        server:{
            baseDir:'src',  // 设置服务器的根目录
            index:'index.html' // 指定默认打开的文件
        },
        port:9000  // 指定访问服务器的端口号
    });
});
