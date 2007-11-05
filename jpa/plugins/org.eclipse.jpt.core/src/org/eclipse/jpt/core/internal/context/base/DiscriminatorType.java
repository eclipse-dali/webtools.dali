/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;


public enum DiscriminatorType {

	STRING,
	CHAR,
	INTEGER;
	

	public static DiscriminatorType fromJavaResourceModel(org.eclipse.jpt.core.internal.resource.java.DiscriminatorType javaDiscriminatorType) {
		if (javaDiscriminatorType == org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.STRING) {
			return STRING;
		}
		else if (javaDiscriminatorType == org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.CHAR) {
			return CHAR;
		}
		else if (javaDiscriminatorType == org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.INTEGER) {
			return INTEGER;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.internal.resource.java.DiscriminatorType toJavaResourceModel(DiscriminatorType discriminatorType) {
		if (discriminatorType == STRING)  {
			return org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.STRING;
		}
		else if (discriminatorType == CHAR) {
			return org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.CHAR;
		}
		else if (discriminatorType == INTEGER) {
			return org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.INTEGER;
		}
		return null;
	}
	

	public static DiscriminatorType fromXmlResourceModel(org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType ormDiscriminatorType) {
		if (ormDiscriminatorType == org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType.STRING) {
			return STRING;
		}
		else if (ormDiscriminatorType == org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType.CHAR) {
			return CHAR;
		}
		else if (ormDiscriminatorType == org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType.INTEGER) {
			return INTEGER;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType toXmlResourceModel(DiscriminatorType discriminatorType) {
		if (discriminatorType == STRING)  {
			return org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType.STRING;
		}
		else if (discriminatorType == CHAR) {
			return org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType.CHAR;
		}
		else if (discriminatorType == INTEGER) {
			return org.eclipse.jpt.core.internal.resource.orm.DiscriminatorType.INTEGER;
		}
		return null;
	}
}
