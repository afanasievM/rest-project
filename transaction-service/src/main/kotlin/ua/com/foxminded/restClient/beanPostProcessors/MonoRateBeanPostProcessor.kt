package ua.com.foxminded.restClient.beanPostProcessors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ua.com.foxminded.restClient.service.RateService

@Component
class MonoRateBeanPostProcessor : BeanPostProcessor {

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        if (bean is RateService) {
            bean.rates()
        }
        return bean
    }
}
