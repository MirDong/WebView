package com.dzk.base.autoservice;

import java.util.ServiceLoader;

/**
 * @author jackie
 * @date 2020/8/12
 */
public final class WebViewServiceLoader {
    public WebViewServiceLoader() {
    }

    public static <S> S loadService(Class<S> service){
        try {
            return ServiceLoader.load(service).iterator().next();
        }catch (Exception e){
            return null;
        }
    }
}
