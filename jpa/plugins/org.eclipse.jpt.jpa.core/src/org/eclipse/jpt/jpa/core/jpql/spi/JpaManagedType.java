/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
package org.eclipse.jpt.jpa.core.jpql.spi;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.persistence.jpa.jpql.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.spi.IMappingBuilder;
import org.eclipse.persistence.jpa.jpql.spi.IType;

/**
 * The abstract definition of {@link IManagedType} defined for wrapping the design-time mapped class
 * object.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.1
 * @since 3.0
 * @author Pascal Filion
 */
public abstract class JpaManagedType implements IManagedType {

	/**
	 * The design-time model object wrapped by this class.
	 */
	private final TypeMapping managedType;

	/**
	 * The builder that is responsible to create the {@link IMapping} wrapping a persistent attribute
	 * or property.
	 */
	private IMappingBuilder<AttributeMapping> mappingBuilder;

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
	 * @param mappingBuilder The builder that is responsible to create the {@link IMapping} wrapping
	 * a persistent attribute or property
	 */
	protected JpaManagedType(JpaManagedTypeProvider provider,
	                         TypeMapping managedType,
	                         IMappingBuilder<AttributeMapping> mappingBuilder) {

		super();
		this.provider       = provider;
		this.managedType    = managedType;
		this.mappingBuilder = mappingBuilder;
	}

	/**
	 * Creates the external form that needs to wrap the given {@link AttributeMapping}. By default,
	 * the call is delegated to {@link IMappingBuilder}.
	 *
	 * @param mapping The mapping to wrap with a {@link IMapping}
	 * @return A new concrete instance of {@link IMapping}
	 */
	protected IMapping buildMapping(AttributeMapping mapping) {
		return mappingBuilder.buildMapping(this, mapping);
	}

	/**
	 * Creates an external form for each {@link AttributeMapping} and stores them by using their
	 * mapping name.
	 *
	 * @return The mappings mapped by their name
	 */
	protected Map<String, IMapping> buildMappings() {
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
	public TypeMapping getManagedType() {
		return managedType;
	}

	/**
	 * Returns the builder that is responsible to create the {@link IMapping} wrapping a persistent
	 * attribute or property.
	 *
	 * @return The concrete implementation of {@link IMappingBuilder}
	 */
	protected IMappingBuilder<AttributeMapping> getMappingBuilder() {
		return mappingBuilder;
	}

	/**
	 * {@inheritDoc}
	 */
	public IMapping getMappingNamed(String name) {
		initializeMappings();
		return mappings.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public JpaManagedTypeProvider getProvider() {
		return provider;
	}

	/**
	 * {@inheritDoc}
	 */
	public IType getType() {
		if (type == null) {
			type = provider.getTypeRepository().getType(managedType.getPersistentType().getName());
		}
		return type;
	}

	/**
	 * Initializes this managed type.
	 */
	protected void initializeMappings() {
		if (mappings == null) {
			mappings = buildMappings();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IMapping> mappings() {
		initializeMappings();
		return IterableTools.cloneSnapshot(mappings.values());
	}
}