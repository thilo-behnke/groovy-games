package env

import di.Singleton

class EnvironmentService implements Singleton {
    private EnvironmentSettings environment

    private final static defaultGraphics = Graphics.SWING

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }

    void setEnvironment(EnvironmentSettings environment) {
        if (this.environment) {
            throw new IllegalStateException("Can't redefine the environment once it was set!")
        }
        EnvironmentSettings environmentWithDefaults = setDefaultsWhereNecessary(environment)
        this.environment = environmentWithDefaults
    }

    EnvironmentSettings getEnvironment() {
        if (this.environment == null) {
            throw new IllegalStateException("Tried to access environment before it was set!")
        }
        return environment
    }

    private setDefaultsWhereNecessary(EnvironmentSettings environment) {
        if (!environment.graphics) {
            return new EnvironmentSettings(graphics: defaultGraphics)
        }
        return environment
    }
}
