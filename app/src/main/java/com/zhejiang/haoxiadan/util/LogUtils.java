package com.zhejiang.haoxiadan.util;

import android.util.Log;

/**
 * Logcat管理工具
 * Created by wqd on 2016-03-15.
 */
public class LogUtils {
    //Log等级 低等级日志不展示
    private static int curLogLevel = Log.VERBOSE;
    //是否显示日志
    public static final boolean DEBUG = true;

    private static StackTraceElement[] currentThread;
    private static String tagName;
    private static String msgT;
    private static String msgC;
    private static String callTraceStack;

    public static int getCurLogLevel() {
        return curLogLevel;
    }

    public static void setCurLogLevel(int curLogLevel) {
        LogUtils.curLogLevel = curLogLevel;
    }

    public synchronized static void initTrace(String msg, int... isPrintStack) {
        int isPrintStackOne = isPrintStack.length > 0 ? isPrintStack[0] : 10;
        currentThread = Thread.currentThread().getStackTrace();
        // vm调用栈中此方法所在index：2：VMStack.java:-2:getThreadStackTrace()<--Thread.java:737:getStackTrace()<--
        int curentIndex = 4;
        String className = currentThread[curentIndex].getFileName();
        if(className != null){
            int endIndex = className.lastIndexOf(".");
            tagName = endIndex < 0 ? className : className.substring(0, endIndex);
            msgT = "[" + className + ":" + currentThread[curentIndex].getLineNumber() + ":"
                    + currentThread[curentIndex].getMethodName() + "()]---";
        }else{
            Log.d("K_K","className : null");
        }
        msgC = "msg:[" + msg + "]";
        if (isPrintStackOne > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("callTraceStack:[");
            for (int i = curentIndex; i < curentIndex + isPrintStackOne && i < currentThread.length; i++) {
                sb.append(currentThread[i].getFileName() + ":" + currentThread[i].getLineNumber() + ":"
                        + currentThread[i].getMethodName() + "()" + "<--");
            }
            sb.append("]");
            callTraceStack = sb.toString();
            msgC += callTraceStack;
        }
    }

    public static void e(String msg, boolean printStack) {
        e(msg, printStack ? 105 : 0);
    }

    public static void w(String msg, boolean printStackNum) {
        w(msg, printStackNum ? 105 : 0);
    }

    public static void d(String msg, boolean printStackNum) {
        d(msg, printStackNum ? 105 : 0);
    }

    public static void v(String msg, boolean printStackNum) {
        v(msg, printStackNum ? 105 : 0);
    }

    public static void i(String msg, boolean printStackNum) {
        i(msg, printStackNum ? 105 : 0);
    }

    public static void e(String msg, int... printStackNum) {
        if (curLogLevel > Log.ERROR || !DEBUG) {
            return;
        }
        initTrace(msg, printStackNum.length > 0 ? printStackNum[0] : 0);
        Log.e(tagName, msgT + msgC);
    }

    public static void w(String msg, int... printStackNum) {
        if (curLogLevel > Log.WARN || !DEBUG) {
            return;
        }
        initTrace(msg, printStackNum.length > 0 ? printStackNum[0] : 0);
        Log.w(tagName, msgT + msgC);
    }

    public static void d(String msg, int... printStackNum) {
        if (curLogLevel > Log.DEBUG || !DEBUG) {
            return;
        }
        initTrace(msg, printStackNum.length > 0 ? printStackNum[0] : 0);
        Log.d(tagName, msgT + msgC);
    }

    public static void v(String msg, int... printStackNum) {
        if (curLogLevel > Log.VERBOSE || !DEBUG) {
            return;
        }
        initTrace(msg, printStackNum.length > 0 ? printStackNum[0] : 0);
        Log.v(tagName, msgT + msgC);
    }

    public static void i(String msg, int... printStackNum) {
        if (curLogLevel > Log.INFO || !DEBUG) {
            return;
        }
        initTrace(msg, printStackNum.length > 0 ? printStackNum[0] : 0);
        Log.i(tagName, msgT + msgC);
    }
}
