package com.ratestart.integrator.builders

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.BeanDefinition

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
class AdSetResponseBuilder {

	String adSetId

	AdSetResponseBuilder using(Optional<String> adSetOperationResult) {

		if (adSetOperationResult.isPresent()) {
			this.withAdSetId(adSetOperationResult.get())
		}
	}

	AdSetResponseBuilder withAdSetId(String adSetId) {
		this.adSetId = adSetId
		this
	}
}
