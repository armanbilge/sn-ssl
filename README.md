# sn-ssl (archived)

This project was the beginnings of a [Scala Native](https://github.com/scala-native/scala-native) implementation of the [Java Secure Socket Extension (JSSE)](https://en.wikipedia.org/wiki/Java_Secure_Socket_Extension) with [OpenSSL](https://www.openssl.org/). It was inspired by [wildfly-openssl](https://github.com/wildfly-security/wildfly-openssl) and related projects.

My original goal was to use this project to port the [TLS APIs](https://fs2.io/#/io?id=tls) in the [FS2](https://github.com/typelevel/fs2) project to Scala Native. I have since accomplished that by directly implementing those APIs with [s2n-tls](https://github.com/aws/s2n-tls), bypassing the JSSE entirely. 

I would be happy to see this project live on in a fork but at this time I have no further plans to work on it.

Further reading:
- [s2n-tls and the JSSE](https://github.com/armanbilge/sn-ssl/discussions/3)
- https://github.com/armanbilge/fs2/pull/2
