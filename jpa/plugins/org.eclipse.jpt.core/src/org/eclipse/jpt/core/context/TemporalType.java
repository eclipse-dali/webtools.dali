/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;


public enum TemporalType {

	DATE,
	TIME,
	TIMESTAMP;
	

	public static TemporalType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.TemporalType javaTemporalType) {
		if (javaTemporalType == org.eclipse.jpt.core.resource.java.TemporalType.DATE) {
			return DATE;
		}
		else if (javaTemporalType == org.eclipse.jpt.core.resource.java.TemporalType.TIME) {
			return TIME;
		}
		else if (javaTemporalType == org.eclipse.jpt.core.resource.java.TemporalType.TIMESTAMP) {
			return TIMESTAMP;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.resource.java.TemporalType toJavaResourceModel(TemporalType temporalType) {
		if (temporalType == DATE)  {
			return org.eclipse.jpt.core.resource.java.TemporalType.DATE;
		}
		else if (temporalType == TIME) {
			return org.eclipse.jpt.core.resource.java.TemporalType.TIME;
		}
		else if (temporalType == TIMESTAMP) {
			return org.eclipse.jpt.core.resource.java.TemporalType.TIMESTAMP;
		}
		return null;
	}
	

	public static TemporalType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.TemporalType ormTemporalType) {
		if (ormTemporalType == org.eclipse.jpt.core.resource.orm.TemporalType.DATE) {
			return DATE;
		}
		else if (ormTemporalType == org.eclipse.jpt.core.resource.orm.TemporalType.TIME) {
			return TIME;
		}
		else if (ormTemporalType == org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP) {
			return TIMESTAMP;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.resource.orm.TemporalType toOrmResourceModel(TemporalType temporalType) {
		if (temporalType == DATE)  {
			return org.eclipse.jpt.core.resource.orm.TemporalType.DATE;
		}
		else if (temporalType == TIME) {
			return org.eclipse.jpt.core.resource.orm.TemporalType.TIME;
		}
		else if (temporalType == TIMESTAMP) {
			return org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP;
		}
		return null;
	}
}
