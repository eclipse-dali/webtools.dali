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
public enum FetchType {

	EAGER,
	LAZY;
	

	public static FetchType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.FetchType javaFetchType) {
		if (javaFetchType == null) {
			return null;
		}
		switch (javaFetchType) {
			case EAGER:
				return EAGER;
			case LAZY:
				return LAZY;
			default:
				throw new IllegalArgumentException("unknown fetch type: " + javaFetchType);
		}
	}

	public static org.eclipse.jpt.core.resource.java.FetchType toJavaResourceModel(FetchType fetchType) {
		if (fetchType == null) {
			return null;
		}
		switch (fetchType) {
			case EAGER:
				return org.eclipse.jpt.core.resource.java.FetchType.EAGER;
			case LAZY:
				return org.eclipse.jpt.core.resource.java.FetchType.LAZY;
			default:
				throw new IllegalArgumentException("unknown fetch type: " + fetchType);
		}
	}
	

	public static FetchType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.FetchType ormFetchType) {
		if (ormFetchType == null) {
			return null;
		}
		switch (ormFetchType) {
			case EAGER:
				return EAGER;
			case LAZY:
				return LAZY;
			default:
				throw new IllegalArgumentException("unknown fetch type: " + ormFetchType);
		}
	}
	
	public static org.eclipse.jpt.core.resource.orm.FetchType toOrmResourceModel(FetchType fetchType) {
		if (fetchType == null) {
			return null;
		}
		switch (fetchType) {
			case EAGER:
				return org.eclipse.jpt.core.resource.orm.FetchType.EAGER;
			case LAZY:
				return org.eclipse.jpt.core.resource.orm.FetchType.LAZY;
			default:
				throw new IllegalArgumentException("unknown fetch type: " + fetchType);
		}
	}

}
