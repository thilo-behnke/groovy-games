package org.tb.gg.global

interface Observer<T> {
	void receive(T update)
}