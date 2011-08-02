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
package org.eclipse.jpt.jpa.core.internal.jpql;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.persistence.jpa.jpql.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.spi.IType;

/**
 * The abstract definition of {@link IManagedType} defined for wrapping the design-time mapped class
 * object.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
abstract class JpaManagedType implements IManagedType {

	/**
	 * The design-time model object wrapped by this class.
	 */
	private final TypeMapping managedType;

	/**
	 * The cached collection of {@link IMapping mappings} that prevent rebuilding them every time one
	 * is requested.
	 */
	private Map<String, IMapping> mappings;

	/**
	 * The provider of JPA managed types.
	 */
	private final JpaManagedTypeProvider provider;

	/**
	 * The cached type of this managed type.
	 */
	private IType type;

	/**
	 * Creates a new <code>JDeveloperManagedType</code>.
	 *
	 * @param managedType The provider of JPA managed types
	 * @param mappedClass The design-time model object wrapped by this class
	 */
	JpaManagedType(JpaManagedTypeProvider provider, TypeMapping managedType) {
		super();
		this.provider    = provider;
		this.managedType = managedType;
	}

	private IMapping buildMapping(AttributeMapping mapping) {
		return new JpaMapping(this, mapping);
	}

	private Map<String, IMapping> buildMappings() {
		Map<String, IMapping> mappings = new HashMap<String, IMapping>();
		for (AttributeMapping mapping  : managedType.getAllAttributeMappings()) {
			mappings.put(mapping.getName(), buildMapping(mapping));
		}
		return mappings;
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(IManagedType managedType) {
		return getType().getName().compareTo(managedType.getType().getName());
	}

	/**
	 * Returns the encapsulated model object.
	 *
	 * @return The managed type wrapped by this external form
	 */
	TypeMapping getManagedType() {
		return managedType;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IMapping getMappingNamed(String name) {
		initializeMappings();
		return mappings.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public final JpaManagedTypeProvider getProvider() {
		return provider;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IType getType() {
		if (type == null) {
			type = provider.getTypeRepository().getType(managedType.getPersistentType().getName());
		}
		return type;
	}

	private void initializeMappings() {
		if (mappings == null) {
			mappings = buildMappings();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final Iterable<IMapping> mappings() {
		initializeMappings();
		return Collections.unmodifiableCollection(mappings.values());
	}
}