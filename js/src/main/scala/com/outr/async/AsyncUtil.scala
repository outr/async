package com.outr.async

import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js
import scala.scalajs.js.timers.SetIntervalHandle

object AsyncUtil {
  private[async] def schedule(delay: FiniteDuration,
                              task: Task): ScheduledTask = {
    val scheduledTask = new ScheduledTask(task)
    var handleOption: Option[SetIntervalHandle] = None
    val terminate = () => {
      handleOption.foreach(js.timers.clearInterval)
    }
    val handle = js.timers.setInterval(delay) {
      scheduledTask.call(terminate)
    }
    scheduledTask.onCancel {
      js.timers.clearInterval(handle)
    }
    handleOption = Some(handle)
    scheduledTask.scheduled()
    scheduledTask
  }

  def shutdown(): Unit = {}
}