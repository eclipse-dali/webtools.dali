/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.options;

/**
 *  TargetServer
 */
public enum TargetServer {
			none,
			oc4j_10_1_3,
			oc4j_11_1_1,
			sunas9;

	// EclipseLink value string
	static final String NONE = "None";
	static final String OC4J_10_1_3 = "OC4J_10_1_3";
	static final String OC4J_11_1_1 = "OC4J_11_1_1";
	static final String SUNAS9 = "SunAS9";

}
