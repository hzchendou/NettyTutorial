package buffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer学习.
 *
 * @author hzchendou
 * @date 18-10-16
 * @since 1.0
 */
public class ByteBufferLearn {

    /**
     * byteBuffer实质上是一个数组.它是一个字节数组,同时提供乐对数据的结构化访问以及维护读写位置的信息
     *
     * 主要包含一下几个核心参数：
     *    position-当前读取位置,
     *    limit 读写的上限, capability >= limit
     *    capability- 初始化容量,buffer是一个固定大小数组,初始化之后不允许修改容量,
     *    mark-为某一度过的位置做标记,便于有时候回退到该位置
     * 主要包含以下几个方法：
     *    flip()-方法写完数据需要开始读的时候，将position复位到0，并将limit设为当前position,
     *    public final Buffer flip() {
     *         limit = position;
     *         position = 0;
     *         mark = -1;
     *         return this;
     *     }
     *
     *    clear()-方法是将position置为0，并不清除buffer内容,
     *    public final Buffer clear() {
     *         position = 0;
     *         limit = capacity;
     *         mark = -1;
     *         return this;
     *     }
     *
     *    mark()-方法是标记，
     *    public final Buffer mark() {
     *         mark = position;
     *         return this;
     *     }
     *
     *    reset()-方法是回到标记
     *    public final Buffer reset() {
     *         int m = mark;
     *         if (m < 0)
     *             throw new InvalidMarkException();
     *         position = m;
     *         return this;
     *     }
     *
     *     rewind()-在开始写之前调用该方法
     *     public final Buffer rewind() {
     *         position = 0;
     *         mark = -1;
     *         return this;
     *     }
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    }
}
