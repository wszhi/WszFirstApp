
package com.example.szwang.wszfirstapp;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;

import java.util.Properties;


import java.io.File;

public class WszTestRunner extends RobolectricTestRunner {

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */


    public WszTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

        String buildVariant = (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE;
        String intermediatesPath = BuildConfig.class.getResource("").toString().replace("file:", "");
        intermediatesPath = intermediatesPath.substring(0, intermediatesPath.indexOf("/classes"));

        System.setProperty("android.package", "com.example.szwang.wszfirstapp");
        System.setProperty("android.manifest", intermediatesPath + "/manifests/full/" + buildVariant + "/AndroidManifest.xml");
        System.setProperty("android.resources", intermediatesPath + "/res/merged/" + buildVariant);
//        System.setProperty("android.assets", intermediatesPath + "/assets/" + buildVariant);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String path = "src/main/AndroidManifest.xml";
        if (!new File(path).exists()) {
            path = "app/" + path;
        }

        config = overwriteConfig(config, "manifest", path);
        return super.getAppManifest(config);
    }
    protected Config.Implementation overwriteConfig(
            Config config, String key, String value) {
        Properties properties = new Properties();
        properties.setProperty(key, value);
        return new Config.Implementation(config,
                Config.Implementation.fromProperties(properties));
    }


    @Override
    protected int pickSdkVersion(Config config, AndroidManifest manifest) {
        config = overwriteConfig(config, "emulateSdk", "21");
        return super.pickSdkVersion(config, manifest);
    }
}
