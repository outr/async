package com.outr.async

import org.scalajs.dom._

import scala.concurrent.duration.FiniteDuration

object AsyncUtil {
  private[async] def schedule(delay: FiniteDuration,
                              task: Task): ScheduledTask = {
    val scheduledTask = new ScheduledTask(task)
    var intervalIdOption: Option[Int] = None
    val terminate = () => {
      intervalIdOption.foreach(id => window.clearInterval(id))
    }
    val intervalId = window.setInterval(() => {
      scheduledTask.call(terminate)
    }, delay.toMillis / 1000.0)
    scheduledTask.onCancel {
      window.clearInterval(intervalId)
    }
    intervalIdOption = Some(intervalId)
    scheduledTask.scheduled()
    scheduledTask
  }

  def shutdown(): Unit = {}
}