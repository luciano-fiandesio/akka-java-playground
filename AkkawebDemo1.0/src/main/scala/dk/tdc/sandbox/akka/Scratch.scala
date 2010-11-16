package dk.tdc.sandbox.akka


object Scratch extends Application {
  def mytest() = {"test"}
  def anothertest(a:String) = a
  def toList[A](value:A) = List(value)
  //println ("azzo"(1))
  println("hello" + mytest())
  println("go " + anothertest("jello"))
  println("size " + toList(1).length)

  val num = List(2,3,4,10,20)
  println (num.foldLeft(0) ((a:Int, b:Int) => a + b))

  println (num.foldLeft(0) (_ + _))

  println ("piApo".exists(_.isUpper))

  def breakException = new RuntimeException("break exc")
  def breakable(op: => Unit) {

    try {
      op

    } catch {case _ => }
  }
  def break = throw breakException
  def install = {
    val env = System.getenv("SCALA_HOME")
    println ("---> "+env)
    if (env == null) break
    println ("ok")
  }
  breakable(install)
  
}