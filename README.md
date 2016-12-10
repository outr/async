# async
============

[![Build Status](https://travis-ci.org/outr/async.svg?branch=master)](https://travis-ci.org/outr/async)
[![Stories in Ready](https://badge.waffle.io/outr/async.png?label=ready&title=Ready)](https://waffle.io/outr/async)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/outr/async)
[![Maven Central](https://img.shields.io/maven-central/v/com.outr/async_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/com.outr/async_2.12)
[![Latest version](https://index.scala-lang.org/com.outr/async/async/latest.svg)](https://index.scala-lang.org/com.outr/async/asyncZZ)

Scala and Scala.js framework to execute and schedule asynchronous tasks

## Setup

async is published to Sonatype OSS and Maven Central supporting Scala and Scala.js on 2.11 and 2.12.

Configuring the dependency in SBT is as follows:

```
libraryDependencies += "com.outr" %% "async" % "1.0.0"
```

or for Scala.js:

```
libraryDependencies += "com.outr" %%% "async" % "1.0.0"
```

## Concepts

The goal of this framework is to provide a very simple abstraction for scheduled tasks in a consistent way between Scala
and Scala.js. We currently rely on Akka's `Scheduler` on the JVM and JavaScript's `window.setInterval` on JS.

## Using

### Imports

Async is intended to be a minimalistic framework. As such, all the functionality you'll need access to is easily
available in one import:

```
import com.outr.async._
```

### Scheduling Tasks

TODO: Write

### Features for 1.0.0 (In-Progress)

* [X] Basic Scheduling of Tasks
* [ ] Easy access to one-time scheduling
* [ ] Convenience functionality to schedule for a specific time daily
* [ ] Convenience functionality to schedule for a specific date and time
* [ ] Complete code coverage
* [ ] Fully documented