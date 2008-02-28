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
public enum TemporalType {

	DATE,
	TIME,
	TIMESTAMP;
	

	public static TemporalType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.TemporalType javaTemporalType) {
		switch (javaTemporalType) {
			case DATE:
				return DATE;
			case TIME:
				return TIME;
			case TIMESTAMP:
				return TIMESTAMP;
			default:
				throw new IllegalArgumentException("unknown temporal type: " + javaTemporalType);
		}
	}
	
	public static org.eclipse.jpt.core.resource.java.TemporalType toJavaResourceModel(TemporalType temporalType) {
		switch (temporalType) {
			case DATE:
				return org.eclipse.jpt.core.resource.java.TemporalType.DATE;
			case TIME:
				return org.eclipse.jpt.core.resource.java.TemporalType.TIME;
			case TIMESTAMP:
				return org.eclipse.jpt.core.resource.java.TemporalType.TIMESTAMP;
			default:
				throw new IllegalArgumentException("unknown temporal type: " + temporalType);
		}
	}
	

	public static TemporalType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.TemporalType ormTemporalType) {
		if (ormTemporalType == null) {
			return null;
		}
		switch (ormTemporalType) {
			case DATE:
				return DATE;
			case TIME:
				return TIME;
			case TIMESTAMP:
				return TIMESTAMP;
			default:
				throw new IllegalArgumentException("unknown temporal type: " + ormTemporalType);
		}
	}
	
	public static org.eclipse.jpt.core.resource.orm.TemporalType toOrmResourceModel(TemporalType temporalType) {
		switch (temporalType) {
			case DATE:
				return org.eclipse.jpt.core.resource.orm.TemporalType.DATE;
			case TIME:
				return org.eclipse.jpt.core.resource.orm.TemporalType.TIME;
			case TIMESTAMP:
				return org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP;
			default:
				throw new IllegalArgumentException("unknown temporal type: " + temporalType);
		}
	}

}
