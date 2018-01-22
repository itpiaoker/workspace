//基本连接线样式
var connectorPaintStyle = {
    strokeStyle: "#1e8151",
    fillStyle: "transparent",
    radius: 5,
    lineWidth: 2
};
// 鼠标悬浮在连接线上的样式
var connectorHoverStyle = {
    lineWidth: 3,
    strokeStyle: "#216477",
    outlineWidth: 2,
    outlineColor: "white"
};
var endpointHoverStyle = {
    fillStyle: "#216477",
    strokeStyle: "#216477"
};
//空心圆端点样式设置
var hollowCircle = {
    DragOptions: { cursor: 'pointer', zIndex: 2000 },
    endpoint: ["Dot", { radius: 7 }], //端点的形状
    connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
    connectorHoverStyle: connectorHoverStyle,
    paintStyle: {
        strokeStyle: "#1e8151",
        fillStyle: "transparent",
        radius: 5,
        lineWidth: 2
    },    //端点的颜色样式
    //anchor: "AutoDefault",
    isSource: true,  //是否可以拖动（作为连线起点）
    connector: ["Straight", { stub: [0, 0], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }], //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
    isTarget: true,  //是否可以放置（连线终点）
    maxConnections: -1,  // 设置连接点最多可以连接几条线
    connectorOverlays: [["Arrow", { width: 10, length: 10, location: 1 }]]
};

$(function(){
    //左边区域的draggable事件
    $("#divContentLeftMenu .node").draggable({
        helper: "clone",
        scope: "plant"
    });

    //中间拖拽区的drop事件
    $("#divCenter").droppable({
        scope: "plant",
        drop: function (event, ui) {
            // 创建工厂模型到拖拽区
            CreateModel(ui, $(this));
        }
    });
});

