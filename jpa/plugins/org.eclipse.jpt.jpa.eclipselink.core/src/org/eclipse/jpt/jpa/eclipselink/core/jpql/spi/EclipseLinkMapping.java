/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.jpql.spi;

import org.eclipse.jpt.jpa.core.jpql.spi.JpaMapping;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.persistence.jpa.jpql.spi.IManagedType;

import static org.eclipse.persistence.jpa.jpql.spi.IEclipseLinkMappingType.*;
import static org.eclipse.persistence.jpa.jpql.spi.IMappingType.*;

/**
 * The EclipseLink implementation of Hermes' {@link IMapping}, which adds EclipseLink specific
 * mapping support.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.1
 * @since 3.1
 * @author Pascal Filion
 */
@SuppressWarnings("unused" /* For the extra import statement, see bug 330740 */)
public class EclipseLinkMapping extends JpaMapping {

	/**
	 * Creates a new <code>EclipseLinkMapping</code>.
	 *
	 * @param parent The parent of this mapping
	 * @param mapping The design-time {@link AttributeMapping} wrapped by this class
	 */
	public EclipseLinkMapping(IManagedType parent, AttributeMapping mapping) {
		super(parent, mapping);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int calculateMappingType() {

		String type = getMapping().getKey();

		// Basic Collection
		if (type == EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY) {
			return BASIC_COLLECTION;
		}

		// Basic Map
		if (type == EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY) {
			return BASIC_MAP;
		}

		// Transformation
		if (type == EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY) {
			return TRANSFORMATION;
		}

		// Variable 1:1
		if (type == EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return VARIABLE_ONE_TO_ONE;
		}

		return super.calculateMappingType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCollection() {
		switch (getMappingType()) {
			case BASIC_COLLECTION:
			case BASIC_MAP:
			case ELEMENT_COLLECTION:
			case MANY_TO_MANY:
			case ONE_TO_MANY: return true;
			default:          return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRelationship() {
		switch (getMappingType()) {
			case ELEMENT_COLLECTION:
			case EMBEDDED_ID:
			case MANY_TO_MANY:
			case MANY_TO_ONE:
			case ONE_TO_MANY:
			case ONE_TO_ONE:
			case VARIABLE_ONE_TO_ONE: return true;
			default:                  return false;
		}
	}
}