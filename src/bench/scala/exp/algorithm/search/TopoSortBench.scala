package exp.algorithm.search

import org.scalameter.api._
import org.scalameter.picklers.noPickler._

import exp.structure.typeclass.{Edge, Node}
import exp.structure.Implicits.directedGraph
import exp.algorithm.graph._

import scala.collection.mutable
import scala.io.Source

object TopoSortBench extends Bench.LocalTime {
  def loadResource(filePath: String): (List[Node[String]], List[Edge[String, Unit]]) = {
    val br = Source.fromResource(filePath).bufferedReader
    val nodeBuff: mutable.ArrayBuilder[String] = mutable.ArrayBuilder.make()
    val edgeBuff: mutable.ArrayBuilder[(String, String)] = mutable.ArrayBuilder.make()

    var n = Integer.valueOf(br.readLine)
    while(n > 0) {
      nodeBuff.+=(br.readLine)
      n = n - 1
    }
    var m = Integer.valueOf(br.readLine)
    while(m > 0) {
      val s = br.readLine.split(" ")
      edgeBuff.+=(s(0) -> s(1))
      m = m - 1
    }
    br.close()

    nodeBuff.result().toList.map(s => Node(s)) ->
      edgeBuff.result().toList.map(p => Edge(Node(p._1), Node(p._2), ()))
  }

  def ts3: Gen[(List[Node[String]], List[Edge[String, Unit]])] = Gen.single("ts3") {
    loadResource("toposort/ts4.in")
  }

  def ts4: Gen[(List[Node[String]], List[Edge[String, Unit]])] = Gen.single("ts4") {
    loadResource("toposort/ts4.in")
  }

  def ts5: Gen[(List[Node[String]], List[Edge[String, Unit]])] = Gen.single("ts5") {
    loadResource("toposort/ts5.in")
  }

  performance of "Search" in {
    measure method "toposort" in {
      using(ts3) config (
        exec.benchRuns -> 5
        ) in { case (nodes, edges) =>
        topoSort(nodes, edges)
      }
    }
  }

  performance of "Search" in {
    measure method "toposort(2)" in {
      using(ts4) config (
        exec.benchRuns -> 5
      ) in { case (nodes, edges) =>
        topoSort(nodes, edges)
      }
    }
  }

  performance of "Search" in {
    measure method "toposort(3)" in {
      using(ts5) config (
        exec.benchRuns -> 5
        ) in { case (nodes, edges) =>
        topoSort(nodes, edges)
      }
    }
  }

  performance of "Search" in {
    measure method "toposort" in {
      using(ts3) config (
        exec.benchRuns -> 5
        ) in { case (nodes, edges) =>
        stateTopoSort(nodes, edges)
      }
    }
  }

  performance of "Search" in {
    measure method "toposort(2)" in {
      using(ts4) config (
        exec.benchRuns -> 5
        ) in { case (nodes, edges) =>
        stateTopoSort(nodes, edges)
      }
    }
  }

  performance of "Search" in {
    measure method "toposort(3)" in {
      using(ts5) config (
        exec.benchRuns -> 5
        ) in { case (nodes, edges) =>
        stateTopoSort(nodes, edges)
      }
    }
  }
}
