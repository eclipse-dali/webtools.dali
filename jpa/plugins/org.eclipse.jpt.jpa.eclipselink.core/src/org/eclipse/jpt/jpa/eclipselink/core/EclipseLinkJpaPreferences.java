/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.jpa.eclipselink.core.internal.plugin.JptJpaEclipseLinkCorePlugin;


/**
 * Public access to the Dali JPA EclipseLink preferences.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public final class EclipseLinkJpaPreferences {

	// ********** project static weaving **********

	public static String getStaticWeavingSourceLocation(IProject project, String defaultValue) {
		return getPlugin().getPreference(project, STATIC_WEAVING_SOURCE_LOCATION, defaultValue);
	}

	public static void setStaticWeavingSourceLocation(IProject project, String location) {
		getPlugin().setPreference(project, STATIC_WEAVING_SOURCE_LOCATION, location);
	}

	public static String getStaticWeavingTargetLocation(IProject project, String defaultValue) {
		return getPlugin().getPreference(project, STATIC_WEAVING_TARGET_LOCATION, defaultValue);
	}

	public static void setStaticWeavingTargetLocation(IProject project, String location) {
		getPlugin().setPreference(project, STATIC_WEAVING_TARGET_LOCATION, location);
	}

	/**
	 * @see org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLoggingLevel#getPropertyValue()
	 */
	public static String getStaticWeavingLogLevel(IProject project, String defaultValue) {
		return getPlugin().getPreference(project, STATIC_WEAVING_LOG_LEVEL, defaultValue);
	}

	/**
	 * @see org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLoggingLevel#getPropertyValue()
	 */
	public static void setStaticWeavingLogLevel(IProject project, String logLevel) {
		getPlugin().setPreference(project, STATIC_WEAVING_LOG_LEVEL, logLevel);
	}

	public static String getStaticWeavingPersistenceInfo(IProject project, String defaultValue) {
		return getPlugin().getPreference(project, STATIC_WEAVING_PERSISTENCE_INFO, defaultValue);
	}

	public static void setStaticWeavingPersistenceInfo(IProject project, String info) {
		getPlugin().setPreference(project, STATIC_WEAVING_PERSISTENCE_INFO, info);
	}

	private static final String STATIC_WEAVING = "staticWeaving"; //$NON-NLS-1$
	private static final String STATIC_WEAVING_ = STATIC_WEAVING + '.';

	private static final String STATIC_WEAVING_SOURCE_LOCATION = STATIC_WEAVING_ + "SOURCE"; //$NON-NLS-1$
	private static final String STATIC_WEAVING_TARGET_LOCATION = STATIC_WEAVING_ + "TARGET"; //$NON-NLS-1$
	private static final String STATIC_WEAVING_LOG_LEVEL = STATIC_WEAVING_ + "LOG_LEVEL"; //$NON-NLS-1$
	private static final String STATIC_WEAVING_PERSISTENCE_INFO = STATIC_WEAVING_ + "PERSISTENCE_INFO"; //$NON-NLS-1$


	// ********** misc **********

	/**
	 * Remove both the project's settings and the project-specific workspace
	 * settings.
	 */
	public static void removePreferences(IProject project) {
		getPlugin().removePreferences(project);
		getPlugin().removePersistentProperties(project);
	}

	public static void removePreferences() {
		getPlugin().removePreferences();
	}

	private static JptJpaEclipseLinkCorePlugin getPlugin() {
		return JptJpaEclipseLinkCorePlugin.instance();
	}

	private EclipseLinkJpaPreferences() {
		throw new UnsupportedOperationException();
	}
}
