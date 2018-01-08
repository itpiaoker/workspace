package cn.com.carenet.scheduler.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/7/3
 */
public class TransposeTree {
    /** */
	public Node root;
	/** 存储转置信息 */
	String startID;
	/** 存储转置信息 */
	String endID;
	/** 存储转置信息 */
	Map<String, String> componentIdAndWorkFlowID;
	/** 存储转置信息 */
	List<String> transposeIds = new ArrayList<>();
	/** 存储转置信息 */
	Map<String, List<String>> dependWorkIdAndWorkFlowIds;
	
	
//    private static ArrayList<Node> childrens = new ArrayList();
//    private int rootIndex = 0;
//    private int size = 0;

    public TransposeTree(String t, String startID, String endID, Map<String, String> componentIdAndWorkFlowID, 
    		Map<String, List<String>> dependWorkIdAndWorkFlowIds){
    	
    	this.startID = startID;
    	this.endID = endID;
    	this.componentIdAndWorkFlowID = componentIdAndWorkFlowID;
    	this.dependWorkIdAndWorkFlowIds = dependWorkIdAndWorkFlowIds;
    	this.root = new Node(t);
    }
    

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void insert(String data, Node node) {
    	ArrayList<Node> sons = node.childrens;
    	Node n = new Node(data);
    	n.data = data;
    	if(sons.size() == 0){
    		sons.add(n);
    	} else {
    		List<String> dependWorkIds = dependWorkIdAndWorkFlowIds.get(node.data);
    		if(dependWorkIds.contains(data)){
    			sons.add(n);
    		} else {
            	for (Node son : sons) {
            		insert(data, son);
        		}
    		}
    	}
    }
    
//    /**
//     * 构造一颗空树
//     *
//     * @param node
//     */
//    public Node insert(TransposeInfo data, Node node) {
//    	TransposeInfo parent = node.getData();
//    	String current = data.getId();
//    	List<String> childs = dependWorkIdAndWorkFlowIds.get(parent.getId());
//    	if(childs.contains(current)){
//    		ArrayList<Node> childrens = node.getChildrens();
//        	Node n = new Node(data);
//        	n.setData(data);
//        	childrens.add(n);
//    	} else {
//    		ArrayList<Node> childrens = node.getChildrens();
//        	for (Node node2 : childrens) {
//        		Node insert = insert(data, node2);
//    		}
//    	}
//    	
//        return node;
//    }
    
    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void initTree(String t) {
    	if(root == null){
    		root = new Node(t);
    	}
    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void destroryTree(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void createTree(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void clearTree(Node node) {
//        root = nullNode;
    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public boolean isEmpty(Node node) {
//        return root == nullNode;
    	return false;
    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void treeDepth(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void treeRoot(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void value(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void assign(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void parent(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void leftChild(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void rightChild(Node node) {

    }

    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void rightSibling(Node node) {

    }
    


    /**
     * 构造一颗空树
     *
     * @param node
     */
    public void deleteChild(Node node, int degree, TransposeTree childNode) {

    }

}
