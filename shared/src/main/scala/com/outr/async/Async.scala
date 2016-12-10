package com.outr.async

import scala.concurrent.duration._

object Async {
  def schedule(delay: FiniteDuration = 16.milliseconds)(task: Task): ScheduledTask = {
    AsyncUtil.schedule(delay, task)
  }
}