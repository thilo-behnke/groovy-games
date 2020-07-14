package engine.helper

interface Updateable {
    void update(Long timestamp, Long delta)
}