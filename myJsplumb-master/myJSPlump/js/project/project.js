/*
 作者：Wang zhiwen
 时间：2017-01-13
 */

$(function() {

    toolbar.addButtonAndADDFunction("glyphicon glyphicon-play","运行","alert('运行')")
    toolbar.addButtonAndADDFunction("glyphicon glyphicon-stop","终止","alert('终止')")
    toolbar.addButtonAndADDFunction("glyphicon glyphicon-ok-sign","部署","alert('部署')")

	//左侧nav悬浮提示
	$(function() {
		$("[data-toggle='tooltip']").tooltip();
	});

	//点击左侧的收缩 
	$("#toLeft").click(function() {
		if($("#toLeft").text() == "<") {
			$("#c_nav_menu").find(".m_contain").fadeOut(600);
			$("#c_window").animate({
				left: "32"
			}, 600,function () {
                $("#toLeft").text(">");
            });

		} else {
			$("#c_nav_menu").find(".m_contain").fadeIn(600);
			$("#c_window").animate({
				left: "255"
			}, 600,function () {
                $("#toLeft").text("<");
            });
		}
	});

	//点击右侧的收缩 
	$("#toRight").click(function() {
		if($("#toRight").text() == ">") {
			$("#right_content").fadeOut(600);
			$("#c_window").animate({
				right: "33"
			}, 600,function () {
                $("#toRight").text("<");
            });

		} else {
			$("#right_content").fadeIn(800);
			$("#c_window").animate({
				right: "280"
			}, 600,function () {
                $("#toRight").text(">");
            });
		}
	});
});

//////////////////////////////////////////////////////////////////
$('.glyphicon.glyphicon-chevron-down').on('click', function (e) {
	Util.upDownEvent(e);
});

$('.label.label-primary.darg-data').draggable({
		revert: "invalid",
		helper: "clone",
		cursor: "move",
		opacity: 0.8, //拖拽时透明
		scope: "park",
		appendTo: "#c_window"
	}

);

//节点fang下时事件 并保存该节点到数据库
$("#c_window").droppable({
	scope: "park",
	drop: function(event, ui) {
		debugger;
		var type = ui.draggable[0].attributes['task-type'].nodeValue;

		var helper = Util.getHelper();
		var left = ui.position.left;
		var top = ui.position.top;
		//测试 以后创建节点的参数需要根据拖动的空间类型来决定
		var node = helper.createTaskNode(type);

		helper.container.updatePosition(node.id,{
			x : left,
			y : top
		});
		var rectStr = Util.buildRect(node);
		var container = $("#c_window");
		container.append(rectStr);
		helper.setPosition(node.id,left,top);//必须先设置位置 否则端点会留在顶端

		var sps = node.sourcePointMeta;
		for(var is=0;is<sps.length;is++) {
			var anchor = [sps[is][0],sps[is][1],sps[is][2],sps[is][3]];
			var type = sps[is][4];
			var source = PointFactory.getInPoint(type,anchor);
			if(sps[is].length == 6) {
				var num = sps[is][5];
				source.style = $.extend(true,  {isSource : true, isTarget : false, maxConnections: num},endpoint);
			}
			helper.addEndpoint(node.id, source);
		}
		var tps = node.targetPointMeta;
		for(var it=0;it<tps.length;it++) {
			var anchor = [tps[it][0],tps[it][1],tps[it][2],tps[it][3]];
			var type = tps[it][4];
			var target = PointFactory.getOutPoint(type,anchor);
			if(tps[it].length == 6) {
				var num = sps[it][5];
				target.style =  $.extend(true, {isTarget : true, isSource : false, maxConnections: num,dropOptions: {
					hoverClass: "hover",
					activeClass: "active"
				}},endpoint);
			}
			helper.addEndpoint(node.id, target);
		}

		helper.container.addNode(node.id,node);
		$('#'+ node.id).bind('click',function (e) {
			Util.clickEventOnNode(e);
		});
		Util.bindcontextmenu(node.id);
		$('#'+ node.id +'>.glyphicon.glyphicon-chevron-down').bind('click', Util.upDownEvent);
		helper.setDraggable('.tasknode');
		//第一次保存节点信息 后期可以封装起来
		 
	}
});

//点击c_window事件 主要用来更新节点
//当用户在编辑完一个节点的属性时 可以点击非节点区域更新该节点
$('#c_window').on('click', function (e) {
	var helper = Util.getHelper();
	var currentNode = helper.getCurrentNode();
	if(currentNode != null) {
		var container = $('#prop_container');
		//enter code here for node process
		var panels = container.children('.panel')
		for(var i = 0 ;i <panels.length; i++) {
			//通过jquery找到editor
			var editor = $(panels[i]).children('.panel-collapse').children('.panel-body')
				.children('.editor').children(":first");
			var editorType = editor.attr('editor');
			switch (editorType) {
				//根据不同的editor分不同处理方式
				case 'input':
					var newValue = editor.val();
					var prop = currentNode.prop[i];
					currentNode.prop[i].value = newValue;
					//todo update node here
					
					
					break;
				case 'select':
					var newValue=editor.val();
					var prop = currentNode.prop[i];
					currentNode.prop[i].value = newValue;
					//todo update node here
					
					break;
				default :
					//todo update node here
					break;
			}
		}
		helper.container.addNode(currentNode.id,currentNode);
		//enter code here for ajax to update database
		
		
		helper.setCurrentNodeEmpty();
		container.empty();
	}
});

////////////////////////////////////////////////////////////////////

//复现方法
function recurrence(nodes) {

	var helper = Util.getHelper();
	//init nodes
	for(var i = 0;i < nodes.length;i++) {
		var node =  nodes[i];
		helper.container.addNode(node.id,node);
		var rectStr = Util.buildRect(node);
		var container = $("#c_window");
		var left = node.position.x;
		var top = node.position.y;
		container.append(rectStr);
		helper.setPosition(node.id, left, top);//必须先设置位置 否则端点会留在顶端

		var sources = node.sourcePoint;
		for(var j = 0 ;j<sources.length;j++) {
			helper.instance.addEndpoint(node.id,sources[j].style, {
				anchor: sources[j].anchor,
				uuid: sources[j].uuid});
		}
		var targets = node.targetPoint;
		for(var k = 0 ;k<targets.length;k++) {
			helper.instance.addEndpoint(node.id,targets[k].style, {
				anchor: targets[k].anchor,
				uuid: targets[k].uuid});
		}
		$('#' + node.id).bind('click', function (e) {
			Util.clickEventOnNode(e);
		});
		Util.bindcontextmenu(node.id);
		$('#' + node.id + '>.glyphicon.glyphicon-chevron-down').bind('click', Util.upDownEvent);
		helper.setDraggable('.tasknode');
	};
	//init connection
	for(var k = 0;k < nodes.length;k++) {
		var node =  nodes[k];
		var connections = node.connections;
		for(var l=0 ;l<connections.length;l++) {
			helper.connectByUuid(connections[l].suuid,connections[l].tuuid);
		}
	}

}

//进入project页面调用的函数
function init(proj) {
	var index
	$.ajax({
		type: "GET",
		url: "/myJSPlump/html/data.json",
        dataType:"json",
		beforeSend : function () {
			 index = layer.load(1);
		},
		success: function(msg){
			if(msg.code==1) {

				var tasks = msg.result;
				recurrence(tasks);
				layer.close(index);
			}
			else {
				layer.close(index);
				layer.msg(msg.msg)
			}
		}
	});
}
init(null);



