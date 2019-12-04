package com.jext.upgrade

import com.atlassian.plugin.osgi.util.OsgiHeaderUtil
import org.osgi.framework.Bundle
import org.osgi.framework.FrameworkUtil

abstract class AbstractUpgradeTask {
    String getPluginKey() {
        Bundle bundle = FrameworkUtil.getBundle(AbstractUpgradeTask)
        OsgiHeaderUtil.getPluginKey(bundle)
    }
}
