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


public enum GenerationType {

	TABLE,
	SEQUENCE,
	IDENTITY,
	AUTO;
	

	public static GenerationType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.GenerationType javaGenerationType) {
		if (javaGenerationType == org.eclipse.jpt.core.resource.java.GenerationType.TABLE) {
			return TABLE;
		}
		else if (javaGenerationType == org.eclipse.jpt.core.resource.java.GenerationType.SEQUENCE) {
			return SEQUENCE;
		}
		else if (javaGenerationType == org.eclipse.jpt.core.resource.java.GenerationType.IDENTITY) {
			return IDENTITY;
		}
		else if (javaGenerationType == org.eclipse.jpt.core.resource.java.GenerationType.AUTO) {
			return AUTO;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.resource.java.GenerationType toJavaResourceModel(GenerationType generationType) {
		if (generationType == TABLE)  {
			return org.eclipse.jpt.core.resource.java.GenerationType.TABLE;
		}
		else if (generationType == SEQUENCE) {
			return org.eclipse.jpt.core.resource.java.GenerationType.SEQUENCE;
		}
		else if (generationType == IDENTITY) {
			return org.eclipse.jpt.core.resource.java.GenerationType.IDENTITY;
		}
		else if (generationType == AUTO) {
			return org.eclipse.jpt.core.resource.java.GenerationType.AUTO;
		}
		return null;
	}
	

	public static GenerationType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.GenerationType ormGenerationType) {
		if (ormGenerationType == org.eclipse.jpt.core.resource.orm.GenerationType.TABLE) {
			return TABLE;
		}
		else if (ormGenerationType == org.eclipse.jpt.core.resource.orm.GenerationType.SEQUENCE) {
			return SEQUENCE;
		}
		else if (ormGenerationType == org.eclipse.jpt.core.resource.orm.GenerationType.IDENTITY) {
			return IDENTITY;
		}
		else if (ormGenerationType == org.eclipse.jpt.core.resource.orm.GenerationType.AUTO) {
			return AUTO;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.resource.orm.GenerationType toOrmResourceModel(GenerationType generationType) {
		if (generationType == TABLE)  {
			return org.eclipse.jpt.core.resource.orm.GenerationType.TABLE;
		}
		else if (generationType == SEQUENCE) {
			return org.eclipse.jpt.core.resource.orm.GenerationType.SEQUENCE;
		}
		else if (generationType == IDENTITY) {
			return org.eclipse.jpt.core.resource.orm.GenerationType.IDENTITY;
		}
		else if (generationType == AUTO) {
			return org.eclipse.jpt.core.resource.orm.GenerationType.AUTO;
		}
		return null;
	}
}
