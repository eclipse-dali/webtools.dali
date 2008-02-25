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


public enum FetchType {

	EAGER,
	LAZY;
	

	public static FetchType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.FetchType javaFetchType) {
		if (javaFetchType == org.eclipse.jpt.core.resource.java.FetchType.EAGER) {
			return EAGER;
		}
		else if (javaFetchType == org.eclipse.jpt.core.resource.java.FetchType.LAZY) {
			return LAZY;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.resource.java.FetchType toJavaResourceModel(FetchType fetchType) {
		if (fetchType == EAGER)  {
			return org.eclipse.jpt.core.resource.java.FetchType.EAGER;
		}
		else if (fetchType == LAZY) {
			return org.eclipse.jpt.core.resource.java.FetchType.LAZY;
		}
		return null;
	}
	

	public static FetchType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.FetchType ormFetchType) {
		if (ormFetchType == org.eclipse.jpt.core.resource.orm.FetchType.EAGER) {
			return EAGER;
		}
		else if (ormFetchType == org.eclipse.jpt.core.resource.orm.FetchType.LAZY) {
			return LAZY;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.resource.orm.FetchType toOrmResourceModel(FetchType fetchType) {
		if (fetchType == EAGER)  {
			return org.eclipse.jpt.core.resource.orm.FetchType.EAGER;
		}
		else if (fetchType == LAZY) {
			return org.eclipse.jpt.core.resource.orm.FetchType.LAZY;
		}
		return null;
	}
}
