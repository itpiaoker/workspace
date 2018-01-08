package mavenJars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){

    	List<Job> list = new ArrayList<Job>();
    	List<String> list2 = new ArrayList<String>();
	    Map<String, Job> idAndParentId = new HashMap<String, Job>();
	    Map<Integer, List<String>> maps = new HashMap<Integer, List<String>>();

    	Job j1 = new Job("job1", "job0");
    	Job j2 = new Job("job2", "job1");
    	Job j3 = new Job("job3", "job2");
    	Job j4 = new Job("job0", null);

	    list.add(j1);
	    list.add(j2);
	    list.add(j3);
	    list.add(j4);

//	    Job j8 = new Job("job5", null);
//	    Job j5 = new Job("job6", "job5");
//	    Job j6 = new Job("job7", "job6");
//	    Job j7 = new Job("job8", "job7");
//
//	    Job j9 = new Job("job9", null);
//	    Job j10 = new Job("job10", "job9");
//	    Job j11 = new Job("job11", "job10");
//	    Job j12 = new Job("job12", "job11");


//    	list.add(j5);
//    	list.add(j6);
//    	list.add(j7);
//    	list.add(j8);
//    	list.add(j9);
//    	list.add(j10);
//    	list.add(j11);
//    	list.add(j12);


	    for (int i = 0; i < list.size(); i++){
//		    for (int j = 0; j < list.size(); j++){
			    idAndParentId.put(list.get(i).jobId, list.get(i));
			    if(list2.contains(list.get(i).parentId)){
				    int index = list2.indexOf(list.get(i).parentId);
				    list2.add(index + 1, list.get(i).jobId);
			    } else if(list.get(i).parentId == null){
				    list2.add(0, list.get(i).jobId);
			    } else {
			        list2.add(list.get(i).jobId);
			    }
//		    }
	    }

	    List<String> list3 = list2;
//	    int from = 0;
//	    int to = 0;
//	    int pas = 0;
//	    for (int i = 0; i < list2.size() - 1; i++){
//		    String parentId = idAndParentId.get(list2.get(i)).parentId;
//		    String nextId = list2.get(i + 1);
//		    to = i + 1;
//		    if(!nextId.equals(parentId)){
////			    to = i + 1;
//			    maps.put(pas, list2.subList(from, to));
//			    from = i;
//			    pas ++;
//		    }
//	    }
//
//	    from ++;
//	    to ++;
//	    maps.put(pas, list2.subList(from, to));
//
//	    Map m = maps;

    }
}

class Job{
	String jobId;
	String parentId;
//	public Job(){}
	public Job(String id, String parentId){
		this.jobId = id;
		this.parentId = parentId;
	}
}
