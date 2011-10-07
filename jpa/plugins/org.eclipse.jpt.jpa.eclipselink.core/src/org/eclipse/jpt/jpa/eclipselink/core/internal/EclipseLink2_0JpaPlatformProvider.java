/*******************************************************************************
* Copyright (c) 2009, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.jpa.core.JpaPlatformProvider;

/**
 *  EclipseLink 2.0 platform config
 */
public class EclipseLink2_0JpaPlatformProvider
	extends AbstractEclipseLink2_0JpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLink2_0JpaPlatformProvider();


	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	protected EclipseLink2_0JpaPlatformProvider() {
		super();
	}
}
