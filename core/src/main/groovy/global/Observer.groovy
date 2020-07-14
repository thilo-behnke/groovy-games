package global

interface Observer<T> {
	void receive(T update)
}