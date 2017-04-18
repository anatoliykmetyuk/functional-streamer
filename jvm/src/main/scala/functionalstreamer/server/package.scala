package functionalstreamer

import com.sun.net.httpserver.{HttpHandler, HttpExchange}

package object server {
  type PartialHandler = PartialFunction[HttpExchange, Response]
  type TotalHandler   = HttpExchange => Response
  type Path           = String

  val defaultEncoding = "utf8"

  // Converters between the native API types and the overlay types we defined
  object -> {
    def unapply(exchange: HttpExchange): Option[(Method, Path)] =
      exchange.getRequestMethod.toMethod.map { _ -> exchange.getRequestURI.getPath }
  }

  implicit class MethodString(str: String) {
    def toMethod: Option[Method] = str.toLowerCase match {
      case "get"  => Some(GET)
      case "post" => Some(POST)
      case _      => None
    }
  }


  // Content Types
  object text {
    val plain = "text/plain"
    val html  = "text/html"
  }

  object application {
    val javascript = "application/javascript"
    val json       = "application/json"
  }
}
