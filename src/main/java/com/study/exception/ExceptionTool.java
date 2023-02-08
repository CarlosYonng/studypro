package com.study.exception;

public class ExceptionTool {

    /**
     * Callable表示的任务可以抛出受检查的或未受检查的异常，并且任何代码都可能抛出一个Error。无论代码抛出什么异常
     * 都会被封装到一个ExecutionException中，并在Future中重新抛出，这将使get的调用代码变得复杂，
     * 当future.get方法抛出ExecutionException时，可能是以下三种情况：Callable抛出的受检查异常，
     * RuntimeException以及Error，所以要对每种情况单独处理，所以封装在launderThrowable中。
     * **/
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }
}
