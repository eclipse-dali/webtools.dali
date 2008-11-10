/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization;

/**
 *  Profiler
 */
public enum Profiler {
	dms_performance_profiler, 
	performance_profiler, 
	query_monitor,
	no_profiler;

	// EclipseLink value string
	public static final String DMS_PERFORMANCE_PROFILER = "DMSPerformanceProfiler";
	public static final String PERFORMANCE_PROFILER = "PerformanceProfiler";
	public static final String QUERY_MONITOR = "QueryMonitor";
	public static final String NO_PROFILER = "NoProfiler";

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
}
