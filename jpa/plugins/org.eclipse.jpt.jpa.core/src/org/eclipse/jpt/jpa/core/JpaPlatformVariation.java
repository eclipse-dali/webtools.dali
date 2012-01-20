/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.AccessType;

/**
 * A JpaPlatform contains a JpaPlatformVariation.  This is used for various jpa spec
 * items that are either optional or only supported by certain version of the spec.
 * Each platform implementation must determine if it supports these things.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JpaPlatformVariation
{
	/**
	 * Return whether table-per-concrete-class is a supported
	 * inheritance strategy in the JPA platform.
	 * Supported.MAYBE means that it is in the JPA spec, but not portable
	 * or might not be supported by a particular provider. 
	 * @return
	 */
	Supported getTablePerConcreteClassInheritanceIsSupported();

	/**
	 * This is used to determine if a relationship mapping that uses a join table
	 * can be overridden with an association override.
	 */
	boolean isJoinTableOverridable();

	AccessType[] getSupportedAccessTypes(JptResourceType resourceType);

	AccessType[] GENERIC_SUPPORTED_ACCESS_TYPES = new AccessType[] {AccessType.FIELD, AccessType.PROPERTY};

	public enum Supported {
		/**
		 * fully supported by the platform
		 */
		YES,
		
		/**
		 * not supported by the platform
		 */
		NO,
		
		/**
		 * in the JPA spec, might not supported be supported by a particular provider
		 */
		MAYBE,
	}
}
