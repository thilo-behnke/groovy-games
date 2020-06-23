package engine

import gameObject.GameObjectProvider

interface GameEngine {
    void start();

    void stop();
}

class DefaultGameEngine implements GameEngine {
    private isRunning = false;
    
    DefaultGameEngine(private GameObjectProvider gameObjectProvider) {
    }

    @Override
    void start() {

    }

    @Override
    void stop() {

    }
}
