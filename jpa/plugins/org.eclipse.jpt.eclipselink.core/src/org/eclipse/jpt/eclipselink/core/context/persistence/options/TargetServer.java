/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence.options;

/**
 *  TargetServer
 */
public enum TargetServer {
			none,
			oc4j,
			sunas9,
			websphere,
			websphere_6_1,
			weblogic,
			weblogic_9,
			weblogic_10,
			jboss;

	// EclipseLink value string
	static final String NONE = "None";
	static final String OC4J = "OC4J";
	static final String SUNAS9 = "SunAS9";
	static final String WEBSPHERE = "WebSphere";
	static final String WEBSPHERE_6_1 = "WebSphere_6_1";
	static final String WEBLOGIC = "WebLogic";
	static final String WEBLOGIC_9 = "WebLogic_9";
	static final String WEBLOGIC_10 = "WebLogic_10";
	static final String JBOSS = "JBoss";

	/**
	 * Return the TargetServer value corresponding to the given literal.
	 */
	public static TargetServer getTargetServerFor(String literal) {
		
		for( TargetServer targetServer : TargetServer.values()) {
			if(targetServer.toString().equals(literal)) {
				return targetServer;
			}
		}
		return null;
	}
}