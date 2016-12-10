package com.outr.async

import com.outr.props.{Val, Var}

class ScheduledTask(val task: Task) {
  protected val _status: Var[TaskStatus] = Var(TaskStatus.New)
  val status: Val[TaskStatus] = _status.toVal()

  private var lastTime = System.currentTimeMillis()

  protected[async] def scheduled(): Unit = _status := TaskStatus.Scheduled

  protected[async] def call(terminate: () => Unit): Unit = {
    val time = System.currentTimeMillis()
    val delta = (time - lastTime) / 1000.0
    if (status() == TaskStatus.Cancelled) {
      terminate()
    } else {
      try {
        _status := TaskStatus.Running
        task.call(delta) match {
          case TaskResponse.Continue => {
            _status := TaskStatus.Completed
            _status := TaskStatus.Scheduled
          }
          case TaskResponse.Finished => {
            terminate()
            _status := TaskStatus.Completed
            _status := TaskStatus.Successful
          }
        }
      } catch {
        case t: Throwable => {
          terminate()
          _status := TaskStatus.Failure(t)
        }
      }
    }
    lastTime = time
  }

  def cancel(): Unit = if (_status().cancellable) {
    _status := TaskStatus.Cancelled
  }

  def onCompleted(f: => Unit): Unit = {
    status.attachAndFire {
      case TaskStatus.Completed => f
      case _ => // Ignore others
    }
  }

  def onCancel(f: => Unit): Unit = {
    status.attachAndFire {
      case TaskStatus.Cancelled => f
      case _ => // Ignore others
    }
  }

  def onCompletedOnce(f: => Unit): Unit = {
    var listener: Option[(TaskStatus) => Unit] = None
    listener = Some(status.attachAndFire {
      case TaskStatus.Completed => {
        f
        status.detach(listener.get)
      }
      case _ => // Ignore others
    })
  }

  def onSuccess(f: => Unit): Unit = {
    status.attachAndFire {
      case TaskStatus.Successful => f
      case _ => // Ignore others
    }
  }

  def onFailure(f: Throwable => Unit): Unit = {
    status.attachAndFire {
      case TaskStatus.Failure(throwable) => f(throwable)
      case _ => // Ignore others
    }
  }
}
