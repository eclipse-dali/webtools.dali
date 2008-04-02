/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.customization;

/**
 *  Weaving
 */
public enum Weaving {
	true_,
	false_, 
	static_;

	// EclipseLink value string
	public static final String TRUE_ = "true";
	public static final String FALSE_ = "false";
	public static final String STATIC_ = "static";
}
