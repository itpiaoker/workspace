jsPlumb.bind("ready", function() {

    window.jsPlumbDemo = {

        init : function() {

            jsPlumb.importDefaults({
                DragOptions : { cursor: "pointer", zIndex:2000 },
                HoverClass:"connector-hover"
            });

            var connectorStrokeColor = "rgba(50, 50, 200, 1)",
                connectorHighlightStrokeColor = "rgba(180, 180, 200, 1)",
                hoverPaintStyle = { strokeStyle:"#7ec3d9" };   // hover paint style is merged on normal style, so you
            // don't necessarily need to specify a lineWidth

            //
            // connect window1 to window2 with a 13 px wide olive colored Bezier, from the BottomCenter of
            // window1 to 3/4 of the way along the top edge of window2.  give the connection a 1px black outline,
            // and allow the endpoint styles to derive their color and outline from the connection.
            // label it "Connection One" with a label at 0.7 of the length of the connection, and put an arrow that has a 50px
            // wide tail at a point 0.2 of the length of the connection.  we use 'cssClass' and 'endpointClass' to assign
            // our own css classes, and the Label overlay has three css classes specified for it too.  we also give this
            // connection a 'hoverPaintStyle', which defines the appearance when the mouse is hovering over it.
            //
            var connection1 = jsPlumb.connect({
                //开始点
                source:"window1",
                //目的点
                target:"window2",
                //连接器采用Bezier曲线，还有直线和流程图连线
                connector:["Bezier", { curviness:70 }],
                //样式
                cssClass:"c1",
                //端点类型，点对点（Dot Endpoint ）,矩形端点(Rectangle Endpoint).图片端点(Image Endpoint)
                //blank端点类型为空
                endpoint:"Blank",
                endpointClass:"c1Endpoint",
                //anchors锚（动态锚，静态锚）,[x,y,dx,dy];x,y的区间为[0,1]意思为锚的位置;dx,dy的区间为[-1,1]意思为曲线的方向
                anchors:["BottomCenter", [ 0.75, 0, 0, -1 ]],
                //连接线的样式
                paintStyle:{
                    //连接线的宽度，int值
                    lineWidth:6,
                    //连接器的颜色
                    strokeStyle:"#a7b04b",
                    //连接器或端点的轮廓宽度
                    outlineWidth:1,
                    //连接器或端点的颜色
                    outlineColor:"#666"
                },
                //fillStyle:定义Endpoint的颜色
                endpointStyle:{ fillStyle:"#a7b04b" },
                //连接线悬浮样式
                hoverPaintStyle:hoverPaintStyle,
                //覆盖物类型，四个值Arrow可配置的箭头（可折回），Label点的连接器上可配置的标签，PlainArrow一个三角形的箭头，不可折回，Diamond钻石
                overlays : [
                    //连接器上配置的label
                    ["Label", {
                        cssClass:"l1 component label",
                        //显示的label
                        label : "Connection One",
                        //连接器的位置
                        location:0.7,
                        id:"label",
                        //点击事件
                        events:{
                            "click":function(label, evt) {
                                alert("clicked on label for connection " + label.component.id);
                            }
                        }
                    }],
                    //连接器上配置的箭头
                    ["Arrow", {
                        cssClass:"l1arrow",
                        location:0.5, width:20,length:20,
                        //点击事件
                        events:{
                            "click":function(arrow, evt) {
                                alert("clicked on arrow for connection " + arrow.component.id);
                            }
                        }
                    }]
                ]
            });


            var w23Stroke = "rgb(189,11,11)";
            var connection3 = jsPlumb.connect({
                source:"window2",
                target:"window3",
                //连接线的样式
                paintStyle:{
                    //连接线的宽度，int值
                    lineWidth:8,
                    //连接器的颜色
                    strokeStyle:w23Stroke,
                    outlineColor:"#666",
                    outlineWidth:1
                },
                detachable:false,
                //连接线悬浮样式
                hoverPaintStyle:hoverPaintStyle,
                //锚
                anchors:[ [ 0.3 , 1, 0, 1 ], "TopCenter" ],
                //端点类型
                endpoint:"Rectangle",
                endpointStyles:[
                    //gradient渐变，Linear线条渐变和radial半径渐变两种
                    { gradient : { stops:[[0, w23Stroke], [1, "#558822"]] }},
                    { gradient : {stops:[[0, w23Stroke], [1, "#882255"]] }}
                ]
            });

            var connection2 = jsPlumb.connect({
                source:'window3', target:'window4',
                //连接线的样式
                paintStyle:{
                    lineWidth:10,
                    //连接器颜色
                    strokeStyle:connectorStrokeColor,
                    outlineColor:"#666",
                    outlineWidth:1
                },
                //鼠标悬浮连接器样式
                hoverPaintStyle:hoverPaintStyle,
                //锚
                anchor:"AutoDefault",
                detachable:false,
                //端点样式
                endpointStyle:{
                    gradient : {
                        stops:[[0, connectorStrokeColor], [1, connectorHighlightStrokeColor]],
                        offset:17.5,
                        innerRadius:15
                    },
                    //端点半径
                    radius:35
                },
                //覆盖物label显示时间
                label : function(connection) {
                    var d = new Date();
                    var fmt = function(n, m) { m = m || 10;  return (n < m ? new Array(("" + m).length - (""+n).length + 1).join("0") : "") + n; };
                    return (fmt(d.getHours()) + ":" + fmt(d.getMinutes()) + ":" + fmt(d.getSeconds())+ "." + fmt(d.getMilliseconds(), 100));
                },
                labelStyle:{
                    cssClass:"component label"
                }
            });


            //
            //this connects window5 with window6 using a Flowchart connector that is painted green,
            //with large Dot endpoints that are placed in the center of each element.  there is a
            //label at the default location of 0.5, and the connection is marked as not being
            //detachable.
            //
            var conn4Color = "rgba(46,164,26,0.8)";
            var connection4 = jsPlumb.connect({
                source:'window5',
                target:'window6',
                //连接器采用流程图连线，cornerRadius流程图线在折线处为圆角
                connector:[ "Flowchart", { cornerRadius:10 } ],
                //锚，连接器在两个window连接的位置
                anchors:["Center", "Center"],
                //连接线样式
                paintStyle:{
                    lineWidth:9,
                    strokeStyle:conn4Color,
                    outlineColor:"#666",
                    outlineWidth:2,
                    joinstyle:"round"
                },
                //鼠标悬浮连接线样式
                hoverPaintStyle:hoverPaintStyle,
                detachable:false,
                endpointsOnTop:false,
                //端点样式
                endpointStyle:{ radius:65, fillStyle:conn4Color },
                labelStyle : { cssClass:"component label" },
                label : "big\nendpoints"
            });

            var connection5 = jsPlumb.connect({
                source:"window4",
                target:"window5",
                //锚点位置
                anchors:["BottomRight", "TopLeft"],
                //连接线样式
                paintStyle:{
                    lineWidth:7,
                    //连接器颜色
                    strokeStyle:"rgb(131,8,135)",
//          outlineColor:"#666",
//           outlineWidth:1,
                    //闪烁的线
                    dashstyle:"4 2",
                    joinstyle:"miter"
                },
                //鼠标悬浮样式
                hoverPaintStyle:hoverPaintStyle,
                //端点类型为图片
                endpoint:["Image", {url:"endpointTest1.png"}],
                //连接器直线
                connector:"Straight",
                endpointsOnTop:true,
                //覆盖物
                overlays:[ ["Label", {
                    cssClass:"component label",
                    label : "4 - 5",
                    location:0.3
                }],
                    //箭头
                    "Arrow"

                ]
            });

            var stateMachineConnector = {
                //连接器
                connector:"StateMachine",
                //连接器样式
                paintStyle:{lineWidth:3,strokeStyle:"#056"},
                //鼠标悬浮样式
                hoverPaintStyle:{strokeStyle:"#dbe300"},
                //端点为空
                endpoint:"Blank",
                //锚位置
                anchor:"Continuous",
                //覆盖物，三件箭头不可折回
                overlays:[ ["PlainArrow", {location:1, width:15, length:12} ]]
            };

            jsPlumb.connect({
                source:"window3",
                target:"window7"
            }, stateMachineConnector);

            jsPlumb.connect({
                source:"window7",
                target:"window3"
            }, stateMachineConnector);

            // jsplumb event handlers

            // double click on any connection
            jsPlumb.bind("dblclick", function(connection, originalEvent) { alert("double click on connection from " + connection.sourceId + " to " + connection.targetId); });
            // single click on any endpoint
            jsPlumb.bind("endpointClick", function(endpoint, originalEvent) { alert("click on endpoint on element " + endpoint.elementId); });
            // context menu (right click) on any component.
            jsPlumb.bind("contextmenu", function(component, originalEvent) {
                alert("context menu on component " + component.id);
                originalEvent.preventDefault();
                return false;
            });

            // make all .window divs draggable. note that here i am just using a convenience method - getSelector -
            // that enables me to reuse this code across all three libraries. In your own usage of jsPlumb you can use
            // your library's selector method - "$" for jQuery, "$$" for MooTools, "Y.all" for YUI3.
            jsPlumb.draggable(jsPlumb.getSelector(".window"), { containment:".demo"});


            jsPlumb.bind("click", function(c) {
                var p = $(c.canvas).find("path"),
                    l = p[0].getTotalLength(),
                    i = l, d = -10, s = 150,
                    att = function(a, v) {
                        for (var i = 0; i < p.length; i++)
                            p[i].setAttribute(a, v);
                    },
                    tick = function() {
                        if (i > 0) {
                            i += d;
                            att("stroke-dashoffset", i);
                            window.setTimeout(tick, s);
                        }
                    };
                att("stroke-dasharray", l + " " + l);
                tick();

            });

        }
    };


    // render mode
    var resetRenderMode = function(desiredMode) {
        var newMode = jsPlumb.setRenderMode(desiredMode);

        jsPlumbDemo.init();
    };
    resetRenderMode(jsPlumb.SVG);

});