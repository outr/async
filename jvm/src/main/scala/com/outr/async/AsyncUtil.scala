package com.outr.async

import akka.actor.{ActorSystem, Cancellable, Scheduler}

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

object AsyncUtil {
  private lazy val system: ActorSystem = ActorSystem("async")
  private lazy val scheduler: Scheduler = system.scheduler

  private[async] def schedule(delay: FiniteDuration,
                              task: Task): ScheduledTask = {
    val scheduledTask = new ScheduledTask(task)

    var cancellableOption: Option[Cancellable] = None
    val terminate = () => {
      cancellableOption.foreach(_.cancel())
    }
    val cancellable = scheduler.schedule(0.seconds, delay) {
      scheduledTask.call(terminate)
    }
    scheduledTask.onCancel {
      cancellable.cancel()
    }
    cancellableOption = Some(cancellable)
    scheduledTask.scheduled()
    scheduledTask
  }

  def shutdown(): Unit = system.terminate()
}