package com.mwl.framework.context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author mawenlong
 * @date 2019/05/06
 */
public class MyBeanDefinitionParser {
    private Properties config = new Properties();
    private List<String> registyClassNames = new ArrayList<String>();
    // 扫描包的key
    private static final String SCAN_PACKAGE = "scanPackage";

    public MyBeanDefinitionParser(String location) {
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream(location.replace("classpath:", ""));
        try {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String packageName) {
        URL classPath = getClass().getClassLoader().getResource("");
        String packagePath = packageName.replaceAll("\\.", "/");
        File scanDir = new File(classPath.getPath() + packagePath);
        if (scanDir == null) {
            return;
        }
        for (File file : scanDir.listFiles()) {
            if (file.isFile()) {
                registyClassNames.add(packageName + '.' + file.getName().replace(".class", ""));
            } else {
                doScanner(packageName + '.' + file.getName());
            }
        }
    }

    public Properties getConfig() {
        return config;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }

    public List<String> getRegistyClassNames() {
        return registyClassNames;
    }

    public void setRegistyClassNames(List<String> registyClassNames) {
        this.registyClassNames = registyClassNames;
    }
}
