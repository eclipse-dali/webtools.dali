/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence.customization;

/**
 *  Profiler
 */
public enum Profiler {
	performance_profiler, 
	query_monitor,
	no_profiler;

	// EclipseLink value string
	public static final String PERFORMANCE_PROFILER = "PerformanceProfiler"; //$NON-NLS-1$
	public static final String QUERY_MONITOR = "QueryMonitor"; //$NON-NLS-1$
	public static final String NO_PROFILER = "NoProfiler"; //$NON-NLS-1$

	// EclipseLink profiler class names
	public static final String PERFORMANCE_PROFILER_CLASS_NAME = "org.eclipse.persistence.tools.profiler.PerformanceProfiler"; //$NON-NLS-1$
	public static final String QUERY_MONITOR_CLASS_NAME = "org.eclipse.persistence.tools.profiler.QueryMonitor"; //$NON-NLS-1$

	/**
	 * Return the Profiler value corresponding to the given literal.
	 */
	public static Profiler getProfilerFor(String literal) {
		
		for( Profiler profiler : Profiler.values()) {
			if(profiler.toString().equals(literal)) {
				return profiler;
			}
		}
		return null;
	}
	
	
	public static String getProfilerClassName(String profilerValue) {
		if (profilerValue == PERFORMANCE_PROFILER) {
			return PERFORMANCE_PROFILER_CLASS_NAME;
		}
		if (profilerValue == QUERY_MONITOR) {
			return QUERY_MONITOR_CLASS_NAME;
		}
		return profilerValue;
	}
}
