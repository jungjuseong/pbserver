package com.clbee.pbcms.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value =HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
public class AuthenticationException extends RuntimeException {

}
