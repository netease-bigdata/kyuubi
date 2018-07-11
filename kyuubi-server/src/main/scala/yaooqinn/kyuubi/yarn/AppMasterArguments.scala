/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package yaooqinn.kyuubi.yarn

case class AppMasterArguments(args: Array[String]) {

  var kyuubiClass: String = _
  var propertiesFile: Option[String] = _

  init(args)

  private[this] def init(args: Array[String]): Unit = {
    var tmp = args.toList

    while (tmp.nonEmpty) {
      tmp match {
        case "--properties-file" :: value :: tail =>
          propertiesFile = Some(value)
          tmp = tail
        case _ =>
          printUsageAndExit(tmp)

      }
    }
  }

  def printUsageAndExit(unknownParam: Any = null, exitCode: Int = 1) {
    // scalastyle:off println
    if (unknownParam != null) {
      System.err.println("Unknown/unsupported param " + unknownParam)
    }
    System.err.println(
      """
        |Usage: yaooqinn.kyuubi.yarn.KyuubiAppMaster [options]
        |Options:
        |  --properties-file FILE Path to a custom Spark properties file.
      """.stripMargin)
    // scalastyle:on println
    System.exit(exitCode)
  }

}