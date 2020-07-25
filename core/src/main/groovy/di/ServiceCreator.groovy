package di

interface ServiceCreator {
    List<Service> createServices(List<Class<? extends Service>> serviceClasses)
}