package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ExtensionTestPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.jpt.core.tests.extension.resource";

	// The shared instance
	private static ExtensionTestPlugin plugin;
	
	/**
	 * The constructor
	 */
	public ExtensionTestPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ExtensionTestPlugin getDefault() {
		return plugin;
	}

}
