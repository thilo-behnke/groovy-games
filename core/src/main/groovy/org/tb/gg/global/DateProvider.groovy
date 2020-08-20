package org.tb.gg.global

import org.tb.gg.di.definition.Singleton

interface DateProvider extends Singleton {
	long now()
}

class DefaultDateProvider implements DateProvider {
	long now() {
		return new Date().getTime()
	}

	@Override
	void init() {

	}

	@Override
	void destroy() {

	}
}