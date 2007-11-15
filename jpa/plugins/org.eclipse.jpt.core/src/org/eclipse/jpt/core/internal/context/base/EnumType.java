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


public enum EnumType {

	ORDINAL,
	STRING;
	

	public static EnumType fromJavaResourceModel(org.eclipse.jpt.core.internal.resource.java.EnumType javaEnumType) {
		if (javaEnumType == org.eclipse.jpt.core.internal.resource.java.EnumType.ORDINAL) {
			return ORDINAL;
		}
		else if (javaEnumType == org.eclipse.jpt.core.internal.resource.java.EnumType.STRING) {
			return STRING;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.internal.resource.java.EnumType toJavaResourceModel(EnumType enumType) {
		if (enumType == ORDINAL)  {
			return org.eclipse.jpt.core.internal.resource.java.EnumType.ORDINAL;
		}
		else if (enumType == STRING) {
			return org.eclipse.jpt.core.internal.resource.java.EnumType.STRING;
		}
		return null;
	}
	

	public static EnumType fromXmlResourceModel(org.eclipse.jpt.core.internal.resource.orm.EnumType ormEnumType) {
		if (ormEnumType == org.eclipse.jpt.core.internal.resource.orm.EnumType.ORDINAL) {
			return ORDINAL;
		}
		else if (ormEnumType == org.eclipse.jpt.core.internal.resource.orm.EnumType.STRING) {
			return STRING;
		}
		return null;
	}
	
	public static org.eclipse.jpt.core.internal.resource.orm.EnumType toXmlResourceModel(EnumType enumType) {
		if (enumType == ORDINAL)  {
			return org.eclipse.jpt.core.internal.resource.orm.EnumType.ORDINAL;
		}
		else if (enumType == STRING) {
			return org.eclipse.jpt.core.internal.resource.orm.EnumType.STRING;
		}
		return null;
	}
}
