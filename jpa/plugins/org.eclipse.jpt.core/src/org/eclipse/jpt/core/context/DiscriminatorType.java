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
public enum DiscriminatorType {

	STRING,
	CHAR,
	INTEGER;
	

	public static DiscriminatorType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.DiscriminatorType javaDiscriminatorType) {
		if (javaDiscriminatorType == null) {
			return null;
		}
		switch (javaDiscriminatorType) {
			case STRING:
				return STRING;
			case CHAR:
				return CHAR;
			case INTEGER:
				return INTEGER;
			default:
				throw new IllegalArgumentException("unknown discriminator type: " + javaDiscriminatorType); //$NON-NLS-1$
		}
	}
	
	public static org.eclipse.jpt.core.resource.java.DiscriminatorType toJavaResourceModel(DiscriminatorType discriminatorType) {
		if (discriminatorType == null) {
			return null;
		}
		switch (discriminatorType) {
			case STRING:
				return org.eclipse.jpt.core.resource.java.DiscriminatorType.STRING;
			case CHAR:
				return org.eclipse.jpt.core.resource.java.DiscriminatorType.CHAR;
			case INTEGER:
				return org.eclipse.jpt.core.resource.java.DiscriminatorType.INTEGER;
			default:
				throw new IllegalArgumentException("unknown discriminator type: " + discriminatorType); //$NON-NLS-1$
		}
	}

	public static DiscriminatorType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.DiscriminatorType ormDiscriminatorType) {
		if (ormDiscriminatorType == null) {
			return null;
		}
		switch (ormDiscriminatorType) {
			case STRING:
				return STRING;
			case CHAR:
				return CHAR;
			case INTEGER:
				return INTEGER;
			default:
				throw new IllegalArgumentException("unknown discriminator type: " + ormDiscriminatorType); //$NON-NLS-1$
		}
	}
	
	public static org.eclipse.jpt.core.resource.orm.DiscriminatorType toOrmResourceModel(DiscriminatorType discriminatorType) {
		if (discriminatorType == null) {
			return null;
		}
		switch (discriminatorType) {
			case STRING:
				return org.eclipse.jpt.core.resource.orm.DiscriminatorType.STRING;
			case CHAR:
				return org.eclipse.jpt.core.resource.orm.DiscriminatorType.CHAR;
			case INTEGER:
				return org.eclipse.jpt.core.resource.orm.DiscriminatorType.INTEGER;
			default:
				throw new IllegalArgumentException("unknown discriminator type: " + discriminatorType); //$NON-NLS-1$
		}
	}

}
