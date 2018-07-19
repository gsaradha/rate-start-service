package com.ratestart.integrator.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode

class ExceptionUtils {
    static ObjectNode getJsonError(String message) {
        ObjectMapper mapper = new ObjectMapper()
        ObjectNode   messageNode = mapper.createObjectNode()
        messageNode.put("message", message)
        messageNode
    }
}
