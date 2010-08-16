package org.eclipse.jpt.eclipselink.core.platform;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.core.platform.JpaPlatformGroupDescription;

/**
 * Constants pertaining to the EclipseLink JPA platforms and their group
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public class EclipseLinkPlatform {
	
	public static final JpaPlatformGroupDescription GROUP
			= JptCorePlugin.getJpaPlatformManager().getJpaPlatformGroup("eclipselink"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_1_0
			= JptCorePlugin.getJpaPlatformManager().getJpaPlatform("org.eclipse.eclipselink.platform"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_1_1
			= JptCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink1_1"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_1_2
			= JptCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink1_2"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_2_0
			= JptCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink2_0"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_2_1
			= JptCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink2_1"); //$NON-NLS-1$
	
	/**
	 * Not for instantiation
	 */
	private EclipseLinkPlatform() {}
}
