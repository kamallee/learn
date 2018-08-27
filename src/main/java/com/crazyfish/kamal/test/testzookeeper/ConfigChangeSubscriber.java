package com.crazyfish.kamal.test.testzookeeper;

import java.util.List;

/**
 * Created by kamal on 15/8/12.
 */
public interface ConfigChangeSubscriber {
    String getInitValue(String key);
    void subscribe(String key);
    List<String> listKeys();
}
