package org.tb.gg.score

import groovy.util.logging.Log4j
import org.tb.gg.di.definition.Singleton

@Log4j
class ScoreManager implements Singleton {
    private int score = 0

    void plus(int score) {
        this.score = this.score + score
        log.info("Score updated: ${this.score}".toString())
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
