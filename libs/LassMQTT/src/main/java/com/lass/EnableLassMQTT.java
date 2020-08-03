package com.lass;

import com.lass.mqtt.MQTTClientWrapper;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MQTTClientWrapper.class})
public @interface EnableLassMQTT {
}
