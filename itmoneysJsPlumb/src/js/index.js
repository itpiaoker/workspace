jsPlumb.ready(function() {
    var instance = jsPlumb.getInstance();
    instance.draggable(jsPlumb.getSelector(".item"), { containment:".cc"});

    var connectorStrokeColor = "rgba(50, 50, 200, 1)",
        connectorHighlightStrokeColor = "rgba(180, 180, 200, 1)",
        hoverPaintStyle = { strokeStyle:"#7ec3d9" };



           var color1 = "#316b31";
           var exampleEndpoint1 = {
               endpoint:["Dot", { radius:8 }],//设置连接点的形状为圆形
               paintStyle:{ fillStyle:color1 },//设置连接点的颜色
               detachable:true,	//是否可以拖动（作为连线起点）
               isEditable:true,	//是否可以拖动（作为连线起点）
               setEditable:true,	//是否可以拖动（作为连线起点）
               isSource:true,	//是否可以拖动（作为连线起点）
               scope:"green dot",//连接点的标识符，只有标识符相同的连接点才能连接
               connectorStyle:{ strokeStyle:color1, lineWidth:6 },//连线颜色、粗细
               connector: ["Bezier" ,{ curviness:10 }],//设置连线为贝塞尔曲线
               maxConnections:1,//设置连接点最多可以连接几条线
               hoverPaintStyle:hoverPaintStyle,
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
                   }]
               ],





               // ConnectionOverlays: [
               //     [ "Arrow", {//箭头的样式
               //         location: 1,
               //         visible:true,
               //         width:11,
               //         length:11,
               //         id:"ARROW",
               //     }],
               //     [ "Label", {//连线上的label
               //         location: 0.4,
               //         id: "label",
               //         cssClass: "aLabel",
               //     }]
               // ],
               // overlays:[
               //     ["Custom", {
               //         create:function(component) {
               //             return $("#endpointTargetLabel");
               //         },
               //         location:0.7,
               //         id:"customOverlay"
               //     }]
               // ],
               isTarget:true	//是否可以放置（作为连线终点）
           };




    var exampleEndpoint2 = {
        endpoint:["Dot", { radius:8 }],//设置连接点的形状为圆形
        paintStyle:{ fillStyle:color1 },//设置连接点的颜色
        isSource:true,	//是否可以拖动（作为连线起点）
        scope:"green dot",//连接点的标识符，只有标识符相同的连接点才能连接
        connectorStyle:{ strokeStyle:color1, lineWidth:6 },//连线颜色、粗细
        connector: ["Bezier" ,{ curviness:10 }],//设置连线为贝塞尔曲线
        maxConnections:1,//设置连接点最多可以连接几条线
        isTarget:true	//是否可以放置（作为连线终点）
    };

           var anchors = [[0.5, 0.5, 0.5, 0.5], [0.5, 0.5, 0.5, 0.5],
                   [0.5, 0.5, 0.5, 0.5], [0.5, 0.5, 0.5, 0.5] ],
               maxConnectionsCallback = function(info) {
                   console.log("Cannot drop connection " + info.connection.id + " : maxConnections has been reached on Endpoint " + info.endpoint.id);
               };


           var e1 = instance.addEndpoint("mysql_in", { anchor:"LeftMiddle" }, exampleEndpoint1);//将exampleEndpoint1类型的点绑定到id为state1的元素上
           instance.addEndpoint("mysql_in", { anchor:"RightMiddle" }, exampleEndpoint2);//将exampleEndpoint1类型的点绑定到id为state1的元素上
           instance.addEndpoint("mysql_out", { anchor:"LeftMiddle" }, exampleEndpoint1);//将exampleEndpoint1类型的点绑定到id为state2的元素上
           instance.addEndpoint("mysql_out", { anchor:"RightMiddle" }, exampleEndpoint2);//将exampleEndpoint1类型的点绑定到id为state2的元素上

    console.log(11111)
    console.log(e1)
    console.log(11111)





    // 连接事件
    // var conn4Color = "rgba(46,164,26,0.8)";
    // jsPlumb.connect({
    //     source:'mysql_in',
    //     target:'mysql_out',
    //     //连接器采用流程图连线，cornerRadius流程图线在折线处为圆角
    //     connector:[ "Flowchart", { cornerRadius:10 } ],
    //     //锚，连接器在两个window连接的位置
    //     anchors:["Center", "Center"],
    //     //连接线样式
    //     paintStyle:{
    //         lineWidth:9,
    //         strokeStyle:conn4Color,
    //         outlineColor:"#666",
    //         outlineWidth:2,
    //         joinstyle:"round"
    //     },
    //     //鼠标悬浮连接线样式
    //     hoverPaintStyle:hoverPaintStyle,
    //     detachable:false,
    //     endpointsOnTop:false,
    //     //端点样式
    //     endpointStyle:{ radius:1, fillStyle:conn4Color },
    //     labelStyle : { cssClass:"component label" },
    //     label : "big\nendpoints"
    // });





    // instance.setContainer("containerId");
    // instance.connect({ source:"mysql_in", target:"mysql_out" });`
    
    
    
    
    
    // var common = {
    //     anchors:[ "BottomCenter", "TopCenter" ],
    //     endpoints:["Dot", "Blank" ]
    // };
    // jsPlumb.connect({ source:"mysql_in", target:"mysql_out" }, common);
    // jsPlumb.connect({ source:"aThirdElement", target:"yetAnotherElement" }, common);

            // instance.draggable(jsPlumb.getSelector(".item"), { containment:".cc"});
//            nonPlumbing.draggable($(".item"), {
//                containment:".cc"
//            });

//            jsPlumb.setContainer($("body"));
//            jsPlumb.addEndpoint(someDiv, { endpoint options });

//            instance.draggable(jsPlumb.getSelector(".item"));
//            jsPlumb.addEndpoint("state3", exampleEndpoint2);//将exampleEndpoint2类型的点绑定到id为state3的元素上
//            jsPlumb.addEndpoint("state1", {anchor:anchors}, exampleEndpoint2);//将exampleEndpoint2类型的点绑定到id为state1的元素上，指定活动连接点


//            jsPlumb.



});