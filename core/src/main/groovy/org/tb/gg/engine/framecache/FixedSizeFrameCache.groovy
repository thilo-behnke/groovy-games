package org.tb.gg.engine.framecache

class FixedSizeFrameCache implements FrameCache {
    private LinkedList<FrameState> list = new LinkedList<>()
    // TODO: Add properties to dependency injection.
    private static final FRAME_LIMIT = 5

    @Override
    void add(FrameState frame) {
        if (list.size() >= FRAME_LIMIT) {
            list.removeFirst()
        }
        list.add(frame)
    }

    @Override
    List<FrameState> getLastFrames(int n) {
        if (list.size() == 0 || n <= 0) {
            return []
        }
        def maxIndex = list.size() - 1
        def from = list.size() > n ? list.size() - n : 0
        list[from..maxIndex]
    }

    @Override
    void clear() {
        list.clear()
    }

    @Override
    void init() {
    }

    @Override
    void destroy() {
        list.clear()
    }
}
