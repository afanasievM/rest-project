package com.ajaxsystems.infrastructure.beanPostProcessors

import com.ajaxsystems.application.service.RateServiceOutPort
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class MonoRateBeanPostProcessor : BeanPostProcessor {

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        if (bean is RateServiceOutPort) {
            bean.rates()
        }
        return bean
    }
}
