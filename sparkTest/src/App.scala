package itmonyes

import scala.actors.Actor

object exam1 {
    def main(args: Array[String]): Unit = {
        //创建actor实例actor1
        val actor1 = new HiActor
        //启动actor1
        actor1.start()
        //向actor1发送消息并等待回复
        val reply = actor1 !? Person("Alice", 20)

        println(reply)

    }
}

//继承Actor特质类
class HiActor extends Actor {
    //重写Actor特质类的抽象方法act,从而实现自己的业务逻辑
    override def act(): Unit = {
        //消息循环
        while (true)
            receive {
                //可以是具体的对象
                //例如：case Person("Alice",20) => println("OK")
                case Person(name, age) => {
                    println("this is " + name + " :  age is " + age); sender ! "replied message..."
                }
            }
    }
}

//样例类
case class Person(val name: String, val age: Int)