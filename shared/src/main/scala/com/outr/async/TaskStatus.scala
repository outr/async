package com.outr.async

sealed abstract class TaskStatus(val cancellable: Boolean)

object TaskStatus {
  case object New extends TaskStatus(true)
  case object Scheduled extends TaskStatus(true)
  case object Running extends TaskStatus(true)
  case object Completed extends TaskStatus(true)
  case object Successful extends TaskStatus(false)
  case class Failure(t: Throwable) extends TaskStatus(false)
  case object Cancelled extends TaskStatus(false)
}