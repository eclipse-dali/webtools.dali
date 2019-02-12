/*******************************************************************************
* Copyright (c) 2008, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;

/**
 *  Profiler
 */
public enum EclipseLinkProfiler implements PersistenceXmlEnumValue {

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

	EclipseLinkProfiler(String propertyValue, String className) {
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

	public static EclipseLinkProfiler fromPropertyValue(String propertyValue) {
		for (EclipseLinkProfiler profiler : EclipseLinkProfiler.values()) {
			if (profiler.getPropertyValue().equals(propertyValue)) {
				return profiler;
			}
		}
		return null;
	}

	/**
	 * Return the Profiler value corresponding to the given literal.
	 */
	public static EclipseLinkProfiler getProfilerFor(String literal) {
		for( EclipseLinkProfiler profiler : EclipseLinkProfiler.values()) {
			if(profiler.toString().equals(literal)) {
				return profiler;
			}
		}
		return null;
	}

	public static String getProfilerClassName(String profilerValue) {
		EclipseLinkProfiler profiler = fromPropertyValue(profilerValue);
		return (profiler == null) ? profilerValue : profiler.getClassName();
	}
}
