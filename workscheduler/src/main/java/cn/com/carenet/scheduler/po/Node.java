package cn.com.carenet.scheduler.po;

import java.util.ArrayList;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/7/3
 */
public class Node {
	String data = null;
	String parent = null;
    ArrayList<Node> childrens = null;
    int degree = 0;

    Node(String data){
        this.data = data;
        this.childrens = new ArrayList<>();
        this.degree = 1;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [data=");
		builder.append(data);
		builder.append(", parent=");
		builder.append(parent);
		builder.append(", childrens=");
		builder.append(childrens);
		builder.append(", degree=");
		builder.append(degree);
		builder.append("]");
		return builder.toString();
	}
    
    
}
