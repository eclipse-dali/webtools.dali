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
public enum InheritanceType {


	SINGLE_TABLE,
	JOINED,
	TABLE_PER_CLASS;


	public static InheritanceType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.InheritanceType javaInheritanceType) {
		switch (javaInheritanceType) {
			case SINGLE_TABLE:
				return SINGLE_TABLE;
			case JOINED:
				return JOINED;
			case TABLE_PER_CLASS:
				return TABLE_PER_CLASS;
			default:
				throw new IllegalArgumentException("unknown inheritance type: " + javaInheritanceType);
		}
	}
	
	public static org.eclipse.jpt.core.resource.java.InheritanceType toJavaResourceModel(InheritanceType inheritanceType) {
		switch (inheritanceType) {
			case SINGLE_TABLE:
				return org.eclipse.jpt.core.resource.java.InheritanceType.SINGLE_TABLE;
			case JOINED:
				return org.eclipse.jpt.core.resource.java.InheritanceType.JOINED;
			case TABLE_PER_CLASS:
				return org.eclipse.jpt.core.resource.java.InheritanceType.TABLE_PER_CLASS;
			default:
				throw new IllegalArgumentException("unknown inheritance type: " + inheritanceType);
		}
	}
	

	public static InheritanceType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.InheritanceType ormInheritanceType) {
		if (ormInheritanceType == null) {
			return null;
		}
		switch (ormInheritanceType) {
			case SINGLE_TABLE:
				return SINGLE_TABLE;
			case JOINED:
				return JOINED;
			case TABLE_PER_CLASS:
				return TABLE_PER_CLASS;
			default:
				throw new IllegalArgumentException("unknown inheritance type: " + ormInheritanceType);
		}
	}
	
	public static org.eclipse.jpt.core.resource.orm.InheritanceType toOrmResourceModel(InheritanceType inheritanceType) {
		switch (inheritanceType) {
			case SINGLE_TABLE:
				return org.eclipse.jpt.core.resource.orm.InheritanceType.SINGLE_TABLE;
			case JOINED:
				return org.eclipse.jpt.core.resource.orm.InheritanceType.JOINED;
			case TABLE_PER_CLASS:
				return org.eclipse.jpt.core.resource.orm.InheritanceType.TABLE_PER_CLASS;
			default:
				throw new IllegalArgumentException("unknown inheritance type: " + inheritanceType);
		}
	}

}
