package org.tb.gg.global

interface DateProvider {
	long now()
}

class DefaultDateProvider implements DateProvider {
	long now() {
		return new Date().getTime()
	}
}