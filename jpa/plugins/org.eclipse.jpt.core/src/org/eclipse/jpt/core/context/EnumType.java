/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum EnumType {

	ORDINAL,
	STRING;
	

	public static EnumType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.EnumType javaEnumType) {
		if (javaEnumType == null) {
			return null;
		}
		switch (javaEnumType) {
			case ORDINAL:
				return ORDINAL;
			case STRING:
				return STRING;
			default:
				throw new IllegalArgumentException("unknown enum type: " + javaEnumType); //$NON-NLS-1$
		}
	}
	
	public static org.eclipse.jpt.core.resource.java.EnumType toJavaResourceModel(EnumType enumType) {
		if (enumType == null) {
			return null;
		}
		switch (enumType) {
			case ORDINAL:
				return org.eclipse.jpt.core.resource.java.EnumType.ORDINAL;
			case STRING:
				return org.eclipse.jpt.core.resource.java.EnumType.STRING;
			default:
				throw new IllegalArgumentException("unknown enum type: " + enumType); //$NON-NLS-1$
		}
	}
	

	public static EnumType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.EnumType ormEnumType) {
		if (ormEnumType == null) {
			return null;
		}
		switch (ormEnumType) {
			case ORDINAL:
				return ORDINAL;
			case STRING:
				return STRING;
			default:
				throw new IllegalArgumentException("unknown enum type: " + ormEnumType); //$NON-NLS-1$
		}
	}
	
	public static org.eclipse.jpt.core.resource.orm.EnumType toOrmResourceModel(EnumType enumType) {
		if (enumType == null) {
			return null;
		}
		switch (enumType) {
			case ORDINAL:
				return org.eclipse.jpt.core.resource.orm.EnumType.ORDINAL;
			case STRING:
				return org.eclipse.jpt.core.resource.orm.EnumType.STRING;
			default:
				throw new IllegalArgumentException("unknown enum type: " + enumType); //$NON-NLS-1$
		}
	}
}
