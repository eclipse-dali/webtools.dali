/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

public enum AccessType {

	FIELD,
	PROPERTY;


	public static AccessType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.AccessType javaAccess) {
		if (javaAccess == org.eclipse.jpt.core.resource.java.AccessType.FIELD) {
			return FIELD;
		}
		else if (javaAccess == org.eclipse.jpt.core.resource.java.AccessType.PROPERTY) {
			return PROPERTY;
		}
		return null;
	}
	
	public static AccessType fromXmlResourceModel(org.eclipse.jpt.core.resource.orm.AccessType ormAccess) {
		if (ormAccess == org.eclipse.jpt.core.resource.orm.AccessType.FIELD) {
			return FIELD;
		}
		else if (ormAccess == org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY) {
			return PROPERTY;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.resource.orm.AccessType toXmlResourceModel(AccessType access) {
		if (access == FIELD)  {
			return org.eclipse.jpt.core.resource.orm.AccessType.FIELD;
		}
		else if (access == PROPERTY) {
			return org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY;
		}
		return null;
	}
}
