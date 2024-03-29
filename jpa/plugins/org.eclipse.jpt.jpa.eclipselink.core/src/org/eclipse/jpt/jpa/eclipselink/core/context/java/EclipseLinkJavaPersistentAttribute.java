/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.java;

/**
 * EclipseLink Java persistent attribute
 */
public interface EclipseLinkJavaPersistentAttribute
{

	/**
	 * Return whether the attribute's type is a subclass of
	 * <code>java.util.Date</code> or <code>java.util.Calendar</code>.
	 */
	boolean typeIsDateOrCalendar();

	String DATE_TYPE_NAME = java.util.Date.class.getName();
	String CALENDAR_TYPE_NAME = java.util.Calendar.class.getName();

	boolean typeIsSerializable();

	boolean typeIsValidForVariableOneToOne();
}
