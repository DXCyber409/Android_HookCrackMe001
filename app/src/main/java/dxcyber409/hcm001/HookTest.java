package dxcyber409.hcm001;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTest implements IXposedHookLoadPackage {
    private static final String TAG = "[dxcyber409.hcm001]: ";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log(TAG + "pkgName: " + loadPackageParam.packageName);
        if (!loadPackageParam.packageName.equals("com.dxcyber409.crackme001")) {
            return;
        }
        XposedBridge.log(TAG + "Found package! " + loadPackageParam.packageName);

        /**
         * Hook the register button to make all inputs always right for this register.
         *
         * The key param needed:
         * Lcom/dxcyber409/crackme001/MainActivity;
         * .method private isLeagleAccount(Ljava/lang/String;Ljava/lang/String;)Z
         */
        XposedHelpers.findAndHookMethod(
                "com.dxcyber409.crackme001.MainActivity",
                loadPackageParam.classLoader,
                "isLeagleAccount",
                String.class,
                String.class,
                new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log(TAG + "Before hook param0: " + param.args[0]);
                XposedBridge.log(TAG + "Before hook param1: " + param.args[1]);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = true;
                XposedBridge.log(TAG + "After hook result: " + result);
                param.setResult(result);
            }
        });

        /**
         * Similar to the above method, and hook native method.
         */
        XposedHelpers.findAndHookMethod(
                "com.dxcyber409.crackme001.MainActivity",
                loadPackageParam.classLoader,
                "stringFromJNI",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult("(dont show it, was hooked.)");
                    }
                }
        );
    }
}
