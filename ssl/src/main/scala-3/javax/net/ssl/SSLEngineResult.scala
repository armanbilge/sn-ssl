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

package javax.net.ssl

import scala.beans.BeanProperty

import SSLEngineResult._

class SSLEngineResult(
    @BeanProperty status: Status,
    @BeanProperty handshakeStatus: HandshakeStatus,
    val bytesConsumed: Int,
    val bytesProduced: Int,
    val sequenceNumber: Long
) {

  def this(
      status: Status,
      handshakeStatus: HandshakeStatus,
      bytesConsumed: Int,
      bytesProduced: Int
  ) = this(status, handshakeStatus, bytesConsumed, bytesProduced, -1)

}

object SSLEngineResult {

  enum HandshakeStatus extends Enum[HandshakeStatus] {
    case NOT_HANDSHAKING, FINISHED, NEED_TASK, NEED_WRAP, NEED_UNWRAP, NEED_UNWRAP_AGAIN
  }

  enum Status extends Enum[Status] {
    case BUFFER_UNDERFLOW, BUFFER_OVERFLOW, OK, CLOSED
  }

}
