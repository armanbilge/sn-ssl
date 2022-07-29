/*
 * Copyright 2022 Arman Bilge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scalanative.ssl

import java.util.Enumeration
import java.util.HashMap
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSessionContext
import scala.collection.immutable.ArraySeq

abstract class OpenSSLSessionContext extends SSLSessionContext {

  private[this] val sessions = new HashMap[ArraySeq.ofByte, SSLSession]

  override def getSession(sessionId: Array[Byte]): SSLSession =
    sessions.get(ArraySeq.unsafeWrapArray(sessionId))

  override def getIds(): Enumeration[Array[Byte]] = {
    val iter = sessions.keySet().iterator()
    new Enumeration[Array[Byte]] {
      override def hasMoreElements(): Boolean = iter.hasNext()
      override def nextElement(): Array[Byte] =
        iter.next().unsafeArray
    }
  }

  override def setSessionTimeout(seconds: Int): Unit = ???

  override def getSessionTimeout(): Int = ???

  override def setSessionCacheSize(size: Int): Unit = ???

  override def getSessionCacheSize(): Int = ???

}
