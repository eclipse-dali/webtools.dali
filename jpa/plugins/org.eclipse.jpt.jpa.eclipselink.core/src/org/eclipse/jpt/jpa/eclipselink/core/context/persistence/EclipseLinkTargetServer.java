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
 *  TargetServer
 */
public enum EclipseLinkTargetServer implements PersistenceXmlEnumValue {
	none("None"), //$NON-NLS-1$
	oc4j("OC4J"), //$NON-NLS-1$
	sunas9("SunAS9"), //$NON-NLS-1$
	websphere("WebSphere"), //$NON-NLS-1$
	websphere_6_1("WebSphere_6_1"), //$NON-NLS-1$
	websphere_7("WebSphere_7"), //$NON-NLS-1$
	weblogic("WebLogic"), //$NON-NLS-1$
	weblogic_9("WebLogic_9"), //$NON-NLS-1$
	weblogic_10("WebLogic_10"), //$NON-NLS-1$
	jboss("JBoss"), //$NON-NLS-1$
	netweaver_7_1("NetWeaver_7_1"); //$NON-NLS-1$

	private final String propertyValue;

	EclipseLinkTargetServer(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * The string used as the property value in the persistence.xml
	 */
	public String getPropertyValue() {
		return this.propertyValue;
	}

	/**
	 * Return the TargetServer value corresponding to the given literal.
	 */
	public static EclipseLinkTargetServer getTargetServerFor(String literal) {
		for (EclipseLinkTargetServer targetServer : EclipseLinkTargetServer.values()) {
			if (targetServer.toString().equals(literal)) {
				return targetServer;
			}
		}
		return null;
	}
}