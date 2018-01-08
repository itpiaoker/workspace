package com.github.abel533.graphs;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.Feature;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.style.LineStyle;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/11/18
 */
public class Graphs {

    public String exportLineGraph(
            String text,
            String subtext,
            String legendText,
            Object[] xAxisValueData,
            Object[] yAxisValueData
    ){



        GsonOption option = new GsonOption();
        option.title(text, subtext);
        option.tooltip().trigger(Trigger.axis);
        option.legend(legendText);
        option.toolbox().show(true);
        option.calculable(true);
        CategoryAxis xAxis = new CategoryAxis();
//        Object arr[] = {"衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"};
//        Object arr[] = {5, 20, 40, 10, 10, 20};
        xAxis.data(xAxisValueData);
        option.xAxis(xAxis);

        ValueAxis yAxis = new ValueAxis();
        option.yAxis(yAxis);

        Line line = new Line();
        line.name("销量");
        line.data(yAxisValueData);


        option.series(line);
        return option.toString();
    }


    public String exportPieGraph(
            String text,
            String subtext,
            String legendText,
            Object... list
    ){


        GsonOption option = new GsonOption();
        option.title(text, subtext);
        option.tooltip().trigger(Trigger.item);
//        option.legend(legendText);
        option.toolbox().show(true);
        option.calculable(true);
        Pie pie = new Pie();
        pie.name("aaa");
        pie.data(list);
        pie.radius("55%");
        pie.roseType(RoseType.angle);
        option.series(pie);
        System.out.println(option.toString());
        return option.toString();
    }

    public String exportBarGraph(
        String text,
        String subtext,
        String legendText,
        Object[] xAxisValueData,
        Object[] yAxisValueData
    ){
//

//
//        GsonOption option = new GsonOption();
//        option.title("服装销量", "纯属虚构");
//
//
//        Grid grid = new Grid();
//        grid.left("%3");
//        grid.right("%7");
//        grid.bottom("%3");
//        option.grid(grid);
//
//
//        Tooltip tooltip = new Tooltip();
////        tooltip.trigger(Trigger.axis);
//        tooltip.showDelay(0);
////        tooltip.formatter();
//        AxisPointer axisPointer = new AxisPointer();
//        axisPointer.show(true);
//        axisPointer.type(PointerType.cross);
//        LineStyle lineStyle = new LineStyle();
//        lineStyle.type(LineType.dashed);
//        lineStyle.width(1);
//        axisPointer.lineStyle(lineStyle);
//        tooltip.axisPointer(axisPointer);
//        option.tooltip(tooltip);
//        Toolbox toolbox = new Toolbox();
////        Feature feature = new Feature();
////        feature.dataZoom
//        option.toolbox(toolbox);
//
//        Legend legend = new Legend();
//        legend.data("女性", "男性");
//        legend.left("center");
//        option.legend(legend);
//
//
////        option.calculable(true);
//
//
//        ValueAxis xAxis = new ValueAxis();
//        xAxis.type(AxisType.value);
//        xAxis.scale(true);
//        xAxis.axisLabel().formatter("{value} cm");
//        xAxis.splitLine().show(false);
//        option.xAxis(xAxis);
//
//
//        ValueAxis yAxis = new ValueAxis();
//        yAxis.type(AxisType.value);
//        yAxis.scale(true);
//        yAxis.axisLabel().formatter("{value} kg");
//        yAxis.splitLine().show(false);
//        option.yAxis(yAxis);
//
//
//        Scatter bar = new Scatter();
//        bar.name("女性");
//        bar.data(arr0);
//        MarkPoint markPoint = new MarkPoint();
//        com.github.abel533.echarts.data.Data data0 = new Data();
//        com.github.abel533.echarts.data.Data data1 = new Data();
//        data0.max();
//        data0.name("最大值");
//        data1.min();
//        data1.name("最小值");
//        markPoint.data(data0, data1);
//        bar.markPoint(markPoint);
//
//
//        Scatter bar2 = new Scatter();
//        bar2.name("男性");
//        bar2.data(arr1);
//        MarkPoint markPoint2 = new MarkPoint();
//        com.github.abel533.echarts.data.Data data3 = new Data();
//        com.github.abel533.echarts.data.Data data4 = new Data();
//        data3.max();
//        data3.name("最大值");
//        data4.min();
//        data4.name("最小值");
//        markPoint2.data(data3, data4);
//        bar2.markPoint(markPoint2);
//
////        MarkLine markLine = new MarkLine();
////        LineStyle lineStyle1 = new LineStyle();
////        lineStyle1.normal().
//        option.series(bar, bar2);
//
//
//
//        return option.toString();
        return null;
    }

    public String exportScatterGraph(
        String text,
        String subtext,
        String legendText,
        Object[] xAxisValueData,
        Object[] yAxisValueData
    ){


        GsonOption option = new GsonOption();
        option.title(text, subtext);


//        Grid grid = new Grid();
//        grid.left("%3");
//        grid.right("%7");
//        grid.bottom("%3");
//        option.grid(grid);


        Tooltip tooltip = new Tooltip();
        tooltip.trigger(Trigger.axis);
        tooltip.showDelay(0);
//        tooltip.formatter();
        AxisPointer axisPointer = new AxisPointer();
        axisPointer.show(true);
        axisPointer.type(PointerType.cross);
        LineStyle lineStyle = new LineStyle();
        lineStyle.type(LineType.dashed);
        lineStyle.width(1);
        axisPointer.lineStyle(lineStyle);
        tooltip.axisPointer(axisPointer);
        option.tooltip(tooltip);


        Legend legend = new Legend();
        legend.data("女性", "男性");
//        legend.left("center");
        option.legend(legend);


        Toolbox toolbox = new Toolbox();
        toolbox.show(true);
        Feature feature = new Feature();
        feature.mark.show(true);
        feature.mark.show(true);
        feature.dataZoom.show(true);
        feature.dataView.show(true).readOnly(false);
        feature.restore.show(true);
        feature.saveAsImage.show(true);
//        toolbox.feature(feature);
        option.toolbox(toolbox);


//        option.calculable(true);


        ValueAxis xAxis = new ValueAxis();
        xAxis.type(AxisType.value);
        xAxis.scale(true);
        xAxis.axisLabel().formatter("{value} cm");
//        xAxis.splitLine().show(false);
        option.xAxis(xAxis);


        ValueAxis yAxis = new ValueAxis();
        yAxis.type(AxisType.value);
        yAxis.scale(true);
        yAxis.axisLabel().formatter("{value} kg");
//        yAxis.splitLine().show(false);
        option.yAxis(yAxis);


        Scatter bar = new Scatter();
        bar.name("女性");
        bar.data(xAxisValueData);
        com.github.abel533.echarts.data.Data data0 = new com.github.abel533.echarts.data.Data();
        com.github.abel533.echarts.data.Data data1 = new com.github.abel533.echarts.data.Data();
        com.github.abel533.echarts.data.Data data5 = new com.github.abel533.echarts.data.Data();
        data0.name("最大值");
        data0.type(MarkType.max);
        data1.name("最小值");
        data1.type(MarkType.min);
        bar.markPoint().data(data0, data1);
        data5.type(MarkType.average);
        data5.name("平均值");
        bar.markLine().data(data5);


        Scatter bar2 = new Scatter();
        bar2.name("男性");
        bar2.data(yAxisValueData);
        com.github.abel533.echarts.data.Data data3 = new com.github.abel533.echarts.data.Data();
        com.github.abel533.echarts.data.Data data4 = new com.github.abel533.echarts.data.Data();
        com.github.abel533.echarts.data.Data data6 = new com.github.abel533.echarts.data.Data();
        data3.type(MarkType.max);
        data3.name("最大值");
        data4.type(MarkType.min);
        data4.name("最小值");
        bar2.markPoint().data(data3, data4);
        data6.type(MarkType.average);
        data6.name("平均值");
        bar2.markLine().data(data6);

//        MarkLine markLine = new MarkLine();
//        LineStyle lineStyle1 = new LineStyle();
//        lineStyle1.normal().
        option.series(bar, bar2);

        return option.toString();
    }


}
