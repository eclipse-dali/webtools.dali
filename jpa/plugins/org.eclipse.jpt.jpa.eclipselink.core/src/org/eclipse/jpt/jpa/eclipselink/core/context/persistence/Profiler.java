/*******************************************************************************
* Copyright (c) 2008, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;

/**
 *  Profiler
 */
public enum Profiler implements PersistenceXmlEnumValue {

	performance_profiler("PerformanceProfiler", "org.eclipse.persistence.tools.profiler.PerformanceProfiler"),  //$NON-NLS-1$ //$NON-NLS-2$
	query_monitor("QueryMonitor", "org.eclipse.persistence.tools.profiler.QueryMonitor"), //$NON-NLS-1$ //$NON-NLS-2$
	no_profiler("NoProfiler", null); //$NON-NLS-1$

	/**
	 * EclipseLink property value
	 */
	private final String propertyValue;

	/**
	 * EclipseLink profiler class name
	 */
	private final String className;

	Profiler(String propertyValue, String className) {
		this.propertyValue = propertyValue;
		this.className = className;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}

	public String getClassName() {
		return this.className;
	}

	public static Profiler fromPropertyValue(String propertyValue) {
		for (Profiler profiler : Profiler.values()) {
			if (profiler.getPropertyValue().equals(propertyValue)) {
				return profiler;
			}
		}
		return null;
	}

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
		Profiler profiler = fromPropertyValue(profilerValue);
		return (profiler == null) ? null : profiler.getClassName();
	}
}
