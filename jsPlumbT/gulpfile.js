//导入工具包 require('node_modules里对应模块')
var gulp = require('gulp'), //本地安装gulp所用到的地方
    less = require('gulp-less');

//定义一个testLess任务（自定义任务名称）
gulp.task('testLess', function () {
    gulp.src('src/less/index.less') //该任务针对的文件
        .pipe(less()) //该任务调用的模块
        .pipe(gulp.dest('src/css')); //将会在src/css下生成index.css
});

gulp.task('default',['testLess']); //定义默认任务








//编译styles下的sass文件
gulp.task('styles', () => {
    return gulp.src('app/styles/*ss')
        .pipe($.plumber())
        .pipe($.sourcemaps.init())
        .pipe($.if('*.scss', $.sass.sync({
                outputStyle: 'expanded',
                precision: 10,
                includePaths: ['.']
            })).on('error', $.sass.logError)
        )
        .pipe($.if('*.less', $.less()))
        .pipe($.if('*_less.css',$.autoprefixer({browsers: ['> 1%', 'last 2 versions', 'Firefox ESR']})))
        .pipe($.sourcemaps.write('.'))
        .pipe(gulp.dest('.tmp/styles'));
});
//编译scripts下的js文件
gulp.task('scripts', () => {
    return gulp.src('app/scripts/**/*.js')
        .pipe($.plumber())
        .pipe($.sourcemaps.init())
        .pipe($.babel())
        .pipe($.ngAnnotate({single_quotes: true}))
        .pipe($.sourcemaps.write('.'))
        .pipe(gulp.dest('.tmp/scripts'))
        .pipe(reload({stream: true}));
});

//检测scripts下所有js文件代码风格
gulp.task('lint', lint('app/scripts/**/*.js'));
//检测test下所有js文件代码风格
gulp.task('lint:test', lint('test/spec/**/*.js', testLintOptions));
//编译html文件，并处理js和css依赖（使用build和endbuild注释配置）
gulp.task('html', ['styles', 'scripts'], () => {
    return gulp.src(['app/**/*.html'])
        .pipe($.useref({searchPath: ['.tmp', 'app', '.']}))
        .pipe($.if('*.js', $.uglify()))
        .pipe($.if('*.css', $.cssnano({ zindex: false })))
        //暂时禁用修改文件名加版本号的方式
        /*.pipe($.if((file)=>{return /\.(?:js|css)$/.test(file.path)}, $.rev()))
         .pipe($.revReplace())*/
        .pipe($.if('*.html', $.htmlmin({
            collapseWhitespace: false,
            removeEmptyAttributes: true
        })))
        .pipe(gulp.dest('dist'));
});

//启动服务,并监听文件变化，时时刷新
gulp.task('serve', ['styles', 'scripts', 'fonts'], () => {
    browserSync({
                    notify: false,
                    port: 9000,
                    server: {
                        baseDir: ['.tmp', 'app'],
                        routes: {
                            '/bower_components': 'bower_components'
                        },
                        middleware: function (req, res, next) {
    if (proxyAPI(req, res) == false) {
        next();
    }
}
}
});

gulp.watch([
    'app/*.html',
    'app/**/*.html',
    'app/images/**/*',
    '.tmp/fonts/**/*'
]).on('change', reload);

gulp.watch('app/styles/**/*ss', ['styles']);
gulp.watch('app/scripts/**/*.js', ['scripts']);
gulp.watch('app/fonts/**/*', ['fonts']);
gulp.watch('bower.json', [/*'wiredep', */'fonts']);
});










//gulp.task(name[, deps], fn) 定义任务  name：任务名称 deps：依赖任务名称 fn：回调函数
//gulp.src(globs[, options]) 执行任务处理的文件  globs：处理的文件路径(字符串或者字符串数组) 
//gulp.dest(path[, options]) 处理完后文件生成路径