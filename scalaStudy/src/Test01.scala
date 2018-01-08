package mavenJars

import java.util
import java.util.{ArrayList, HashMap, List, Map}


/**
  * Hello world!
  *
  */
object Test02 {
	def main(args: Array[String]): Unit = {
		var list = List[Job]()
//		val list2 = List[String]
//		val idAndParentId = Map[String, Job]
//		val maps = Map[Integer, List[String]]
		val j4 = new Job("job0", null)
		val j1 = new Job("job1", "job0")
		val j2 = new Job("job2", "job1")
		val j3 = new Job("job3", "job2")
		val j8 = new Job("job5", null)
		val j5 = new Job("job6", "job5")
		val j6 = new Job("job7", "job6")
		val j7 = new Job("job8", "job7")
		val j9 = new Job("job9", null)
		val j10 = new Job("job10", "job9")
		val j11 = new Job("job11", "job10")
		val j12 = new Job("job12", "job11")
		list = list.+:(j1)
		list = list.+:(j2)
		list = list.+:(j3)
		list = list.+:(j4)
		list = list.+:(j5)
		list = list.+:(j6)
		list = list.+:(j7)
		list = list.+:(j8)
		list = list.+:(j9)
		list = list.+:(j10)
		list = list.+:(j11)
		list = list.+:(j12)

		val list2 = list.sortWith((pre, next)=>{pre.dependsId.equals(next.id)})
		val list3 = list2
		
	}
}

class Job(jobId: String, parentId: String) {
	val id = jobId
	val dependsId = parentId
}
