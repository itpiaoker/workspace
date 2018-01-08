import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by itmoneys on 2016/9/21.
 */
public class HbaseTest {
    static Configuration conf = null;
    public static TableName table =  TableName.valueOf("phone");
    public static Random ra= new Random();



    @Before
    public void setConf() throws IOException {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","node5,node6,node7,node8");
    }

    @Test
    public void createTable() throws IOException {
        HBaseAdmin admin  = new HBaseAdmin(conf);
        if(admin.tableExists(table)){
            admin.disableTable(table);
            admin.deleteTable(table);
        }
        HTableDescriptor hd = new HTableDescriptor(TableName.valueOf("phone"));
        HColumnDescriptor hc = new HColumnDescriptor("cf1");
        hc.setMaxVersions(10);
        hc.setBlockCacheEnabled(true);
        hc.setBlocksize(1280000);
        hd.addFamily(hc);
        admin.createTable(hd);
    }
    @Test
    public void insert() throws IOException {
        HTable hTable = new HTable(conf,table);
        Put put = new Put("123".getBytes());
        put.add("cf1".getBytes(),"name".getBytes(),"zs".getBytes());
        put.add("cf1".getBytes(),"age".getBytes(),"12".getBytes());
        hTable.put(put);
    }


    @Test
    public void insert2() throws IOException {
        HTable hTable = new HTable(conf,table);
        List<Put> list = new ArrayList<Put>();
        for(int i=0;i<1000;i++){
            Put put = new Put(String.valueOf(i).getBytes());
            put.add("cf1".getBytes(),"name".getBytes(),"zs".getBytes());
            list.add(put);
        }
        hTable.put(list);
    }

    @Test
    //插入通话的模拟数据，这里只模拟一个人插入了1000条
    //type=0是主叫  type=1是被叫
    public void insert3() throws IOException {
        HTable hTable = new HTable(conf,table);
        List<Put> list = new ArrayList<Put>();
        for(int i=0;i<1000;i++){
            Put put = new Put(getRowkey("18611719356_2016").getBytes());
            put.add("cf1".getBytes(),"addr".getBytes(),"北京".getBytes());
            put.add("cf1".getBytes(),"type".getBytes(),String.valueOf(ra.nextInt(2)).getBytes());
            put.add("cf1".getBytes(),"number".getBytes(),getNumber("138").getBytes());
            put.add("cf1".getBytes(),"time".getBytes(),String.valueOf(ra.nextInt(500)).getBytes());
            list.add(put);
        }
        hTable.put(list);
    }


    @Test
    public void search() throws IOException {
        HTable hTable = new HTable(conf,table);
        Get get = new Get("123".getBytes());
//        get.addColumn("cf1".getBytes(),"age".getBytes());
        Result result = hTable.get(get);
        Cell cell = result.getColumnLatestCell("cf1".getBytes(), "name".getBytes());
        System.out.println(new String(CellUtil.cloneValue(cell),"UTF-8"));

    }
    @Test
    //查询5月的通话详单
    public void scan2() throws IOException {
        HTable hTable = new HTable(conf,table);
        Scan scan = new Scan("18611719356_20160501000000".getBytes(),"18611719356_20160601000000".getBytes());
        ResultScanner results = hTable.getScanner(scan);
        Iterator<Result> it = results.iterator();
        while (it.hasNext()){
            Result result = it.next();
            Cell cell = result.getColumnLatestCell("cf1".getBytes(), "addr".getBytes());
            System.out.println(new String(CellUtil.cloneRow(cell),"UTF-8"));
            System.out.println(new String(CellUtil.cloneValue(cell),"UTF-8"));
        }
    }

    @Test
    // 查询5月的被叫通话详单
    public void scan3() throws IOException {
        HTable hTable = new HTable(conf,table);
        Scan scan = new Scan();
        FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        PrefixFilter pr = new PrefixFilter("18611719356_201612".getBytes());
        SingleColumnValueFilter sc = new SingleColumnValueFilter("cf1".getBytes(),"type".getBytes(),
                CompareFilter.CompareOp.EQUAL,"1".getBytes());
        list.addFilter(pr);
        list.addFilter(sc);
        scan.setFilter(list);
        ResultScanner results = hTable.getScanner(scan);
        Iterator<Result> it = results.iterator();
        while (it.hasNext()){
            Result result = it.next();
            Cell cell = result.getColumnLatestCell("cf1".getBytes(), "number".getBytes());
            System.out.println(new String(CellUtil.cloneRow(cell),"UTF-8"));
            System.out.println(new String(CellUtil.cloneValue(cell),"UTF-8"));
        }
    }

    private String getRowkey(String s) {
        StringBuffer sb = new StringBuffer(s);
        sb.append(String.format("%02d",ra.nextInt(12)+1))
                .append(String.format("%02d",ra.nextInt(30)+1))
                .append(String.format("%02d",ra.nextInt(24)))
                .append(String.format("%02d",ra.nextInt(60)))
                .append(String.format("%02d",ra.nextInt(60)));
        return sb.toString();
    }

    private String getNumber(String s) {
        return new StringBuilder(s).append(String.format("%08d",ra.nextInt(99999999))).toString();
    }

    public static void main(String[] args){

    }
}
