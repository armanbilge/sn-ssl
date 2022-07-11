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

import org.junit.Test

import java.nio.ByteBuffer
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class SSLEngineEndToEndTest {

  @Test
  def helloWorld() = {
    val ctx = {
      val keyStore = KeyStore.getInstance(KeyStore.getDefaultType)
      keyStore.load(getClass.getResourceAsStream("/keystore.jks"), "password".toCharArray)
      val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm)
      kmf.init(keyStore, "password".toCharArray)
      val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm)
      tmf.init(keyStore)
      val ctx = SSLContext.getInstance("TLS")
      ctx.init(kmf.getKeyManagers, tmf.getTrustManagers, null)
      ctx
    }

    val server = ctx.createSSLEngine()
    server.setUseClientMode(false)

    val client = ctx.createSSLEngine()
    client.setUseClientMode(true)

    val clientTextIn = ByteBuffer.wrap("helloworld".getBytes)
    val clientTextOut = ByteBuffer.allocate(10)
    val clientEncryptedOut = ByteBuffer.allocate(1024 * 1024)
    val serverEncryptedIn = clientEncryptedOut.asReadOnlyBuffer()
    val serverTextOut = ByteBuffer.allocate(10)
    val serverTextIn = serverTextOut.asReadOnlyBuffer()
    val serverEncryptedOut = ByteBuffer.allocate(1024 * 1024)
    val clientEncryptedIn = serverEncryptedOut.asReadOnlyBuffer()

    println(client.wrap(clientTextIn, clientEncryptedOut))
    println(server.unwrap(serverEncryptedIn, serverTextOut))
    server.getDelegatedTask().run()
    println(server.getHandshakeStatus())
    println(server.wrap(serverTextIn, serverEncryptedOut))
    println(server.wrap(serverTextIn, serverEncryptedOut))
    println(server.wrap(serverTextIn, serverEncryptedOut))
    println(client.unwrap(clientEncryptedIn, clientTextOut))
    client.getDelegatedTask().run()
    println(client.getHandshakeStatus())
    println(client.wrap(clientTextIn, clientEncryptedOut))
    println(client.unwrap(clientEncryptedIn, clientTextOut))
    println(client.wrap(clientTextIn, clientEncryptedOut))
    println(server.unwrap(serverEncryptedIn, serverTextOut))
    println(
      (
        clientTextIn,
        clientEncryptedOut,
        serverEncryptedIn,
        serverEncryptedOut,
        clientEncryptedIn))
  }

}
