package com.outr.async

trait Task {
  def call(delta: Double): TaskResponse
}
