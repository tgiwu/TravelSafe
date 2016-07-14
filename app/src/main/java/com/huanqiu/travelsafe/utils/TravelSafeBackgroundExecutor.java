package com.huanqiu.travelsafe.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import com.google.common.base.Preconditions;
import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.net.BackgroundCallRunnable;
import com.huanqiu.travelsafe.net.NetworkCallRunnable;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by Administrator on 2016/7/13.
 */
public class TravelSafeBackgroundExecutor implements BackgroundExecutor {

    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    ExecutorService mExecutorService;

    public TravelSafeBackgroundExecutor(ExecutorService executorService) {
        mExecutorService = Preconditions.checkNotNull(executorService,
                "executorService cannot be null");
        App.getInstance().getGraph().inject(this);
    }

    @Override
    public <R> void execute(NetworkCallRunnable<R> runnable) {
        mExecutorService.execute(new NetworkCallRunner<>(runnable));
    }

    @Override
    public <R> void execute(BackgroundCallRunnable<R> runnable) {
        mExecutorService.execute(new BackgroundCallRunner<>(runnable));
    }

    private class BackgroundCallRunner<R> implements Runnable {
        private final BackgroundCallRunnable<R> mBackgroundCallRunnable;

        BackgroundCallRunner(BackgroundCallRunnable<R> runnable) {
            this.mBackgroundCallRunnable = runnable;
        }

        @Override
        public void run() {
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mBackgroundCallRunnable.preExecute();
                }
            });

            R result = mBackgroundCallRunnable.runAsync();
            sHandler.post(new ResultCallback(result));
        }

        private class ResultCallback implements Runnable {

            private final R mResult;

            private ResultCallback(R result) {
                this.mResult = result;
            }
            @Override
            public void run() {
                mBackgroundCallRunnable.postExecute(mResult);
            }
        }
    }

    class NetworkCallRunner<R> implements Runnable{
        private final NetworkCallRunnable<R> mBackgroundRunnable;

        NetworkCallRunner(NetworkCallRunnable<R> runnable) {
            mBackgroundRunnable = runnable;
        }
        @Override
        public void run() {
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            sHandler.post(new Runnable() {

                @Override
                public void run() {
                    mBackgroundRunnable.onPre();
                }
            });
            mBackgroundRunnable.doBackgroundCall()
                    .subscribe(new Observer<R>() {
                        @Override
                        public void onCompleted() {
                            mBackgroundRunnable.onFinish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mBackgroundRunnable.onError(e);
                        }

                        @Override
                        public void onNext(R r) {
                            mBackgroundRunnable.onSuccess(r);
                        }
                    });
        }
    }
}
