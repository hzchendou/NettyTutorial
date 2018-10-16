package bio.async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 时间服务器处理逻辑线程池.
 *
 * @author hzchendou
 * @date 18-10-16
 * @since 1.0
 */
public class TimeServerHandlerExecutePool {

    /**
     * 执行器
     */
    private ExecutorService executor;

    /**
     * 构造函数
     *
     * @param maxPoolSize
     * @param queueSize
     */
    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor =
                new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(queueSize));
    }

    /**
     * 任务调度
     *
     * @param task
     */
    public void execute(Runnable task) {
        executor.execute(task);
    }
}
