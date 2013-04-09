package org.eclipse.jpt.jpa.annotate.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptJpaAnnotatePlugin extends JptPlugin 
{
	// ********** singleton **********

	private static volatile JptJpaAnnotatePlugin INSTANCE;

	/**
	 * Return the singleton Dali common core plug-in.
	 */
	public static JptJpaAnnotatePlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaAnnotatePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaAnnotatePlugin) plugin;
	}

}
