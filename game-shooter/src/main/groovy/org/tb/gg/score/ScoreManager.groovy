package org.tb.gg.score


import org.tb.gg.di.definition.Singleton

class ScoreManager implements Singleton {
    private int score = 0

    void plus(int score) {
        this.score = this.score + score
    }

    void minus(int score) {
        this.score = this.score - score
    }

    int getScore() {
        return this.score
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
