'use strict';

/**
 * Description: datetimepicker
 * Author: xinfeng_qian
 * Update: xinfeng_qian(2016-09-19 18:22)
 */
app.directive('datetimePicker', function () {

	return {
		require: '?ngModel',
		restrict: 'AE',
		scope: {
			datetimePicker: '='
		},
		link: function link(scope, elem, attr, ngModel) {
			var defOptions = {
				format: 'YYYY-MM-DD HH:mm:ss'
			};
			var options = angular.merge({}, defOptions, scope.datetimePicker || {});
			//input-group里的按钮点击
			var parent = elem.parent();
			if (parent.hasClass('input-group')) {
				var btn = null;
				// in case there is more then one 'input-group-addon' Issue #48
				if (parent.find('.datepickerbutton').length === 0) {
					btn = parent.find('.input-group-btn');
				} else {
					btn = parent.find('.datepickerbutton');
				}
				btn.click(function () {
					elem.datetimepicker('show');
				});
			}
			elem.datetimepicker(options).on('dp.change', function () {
				ngModel.$setViewValue(this.value);
			});
			scope.$watch('datetimePicker', function (n, o) {
				if (n === o) return;
				var dp = getInstance(elem);
				dp.options(scope.datetimePicker);
				if (scope.datetimePicker.defaultDate) {
					dp.date(scope.datetimePicker.defaultDate);
					dp.viewDate(scope.datetimePicker.defaultDate); // default viewDate is moment(), bug #1325
				}
			}, true);
			//渲染
			ngModel.$render = function () {
				var date = ngModel.$viewValue ? moment(ngModel.$viewValue) : null;
				getInstance(elem).date(date);
			};
			//修复viewMode为years或months时显示days日期选择界面的bug
			elem.on('dp.show', function (event) {
				var instance = getInstance(angular.element(event.target));
				var viewMode = instance.options().viewMode;
				if (viewMode == 'years' || viewMode == 'months') {
					instance.viewMode(viewMode);
				}
			});
			//获取实例
			function getInstance(element) {
				return element.data('DateTimePicker');
			}
		}

	};
});
//# sourceMappingURL=datetimepicker.js.map
