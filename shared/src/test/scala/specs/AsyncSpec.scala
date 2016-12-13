package specs

import com.outr.async.{Async, TaskResponse, TaskStatus}
import org.scalatest.{AsyncWordSpec, Matchers, WordSpec}

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success}

class AsyncSpec extends AsyncWordSpec with Matchers {
  "Async" should {
    "asynchronously count up" in {
      val changes = ListBuffer.empty[Double]
      var total = 0.0
      val task = Async.schedule() { delta =>
        total += delta
        println(s"delta: $delta, total: $total")
        if (total < 1.0) {
          TaskResponse.Continue
        } else {
          TaskResponse.Finished
        }
      }

      task.future.onComplete { result =>
        println(s"COMPLETE! $result")
      }

      task.future.map { status =>
        assert(status == TaskStatus.Successful)
        assert(total >= 1.0)
      }
    }
  }
}
