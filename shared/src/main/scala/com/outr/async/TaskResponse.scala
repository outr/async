package com.outr.async

sealed trait TaskResponse

object TaskResponse {
  case object Continue extends TaskResponse
  case object Finished extends TaskResponse
}