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
 * A JPA platform has a JPA platform variation. The variation is used for
 * various JPA spec items that are either optional or undefined.
 * Each JPA platform implementation must determine whether it
 * supports each of these features.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see JpaPlatform#getJpaVariation()
 * @version 2.3
 * @since 2.3
 */
public interface JpaPlatformVariation {
	/**
	 * Return whether table-per-concrete-class is a supported
	 * inheritance strategy in the JPA platform.
	 * {@link Supported#MAYBE} means that it is defined in the JPA spec,
	 * but not portable or might not be supported by a particular
	 * JPA runtime implementation.
	 */
	Supported getTablePerConcreteClassInheritanceIsSupported();

	/**
	 * This is used to determine if a relationship mapping that uses a join table
	 * can be overridden with an association override.
	 */
	boolean isJoinTableOverridable();

	AccessType[] getSupportedAccessTypes(JptResourceType resourceType);

	AccessType[] GENERIC_SUPPORTED_ACCESS_TYPES = new AccessType[] {
		AccessType.FIELD,
		AccessType.PROPERTY
	};

	/**
	 * Various indications of whether a particular feature is supported by a
	 * JPA runtime implementation.
	 */
	public enum Supported {
		/**
		 * Fully supported by the JPA platform.
		 */
		YES,
		
		/**
		 * Not supported by the JPA platform.
		 */
		NO,
		
		/**
		 * Defined in the JPA spec, but might not be supported by
		 * a particular JPA runtime implementation. This setting should be
		 * returned only by the "generic" implementation, as it is tied to
		 * a specific runtime implementation and can only offer feedback as
		 * to whether a feature may be problematic at runtime, depending on
		 * the particular implementation. Implmentation-specific JPA platforms
		 * should be able to answer definitively whether the implementation
		 * supports a particular feature.
		 */
		MAYBE
	}
}
