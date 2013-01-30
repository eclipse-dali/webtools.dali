/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedType;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.spi.IMappingBuilder;
import org.eclipse.persistence.jpa.jpql.spi.IType;

/**
 * The abstract implementation of a {@link IManagedType} that supports a dynamic managed type.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 3.2
 * @author Pascal Filion
 */
public abstract class EclipseLinkDynamicManagedType implements IManagedType {

	/**
	 * The default implementation of {@link IManagedType}.
	 */
	private JpaManagedType delegate;

	/**
	 * The cached collection of {@link IMapping mappings} that prevent rebuilding them every time one
	 * is requested.
	 */
	private Map<String, EclipseLinkDynamicMapping> mappings;

	/**
	 * The provider of JPA managed types.
	 */
	private JpaManagedTypeProvider provider;

	/**
	 * The cached {@link IType} representing the managed type.
	 */
	private EclipseLinkDynamicType type;

	/**
	 * Creates a new <code>EclipseLinkDynamicManagedType</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param delegate The default implementation of {@link IManagedType}
	 */
	public EclipseLinkDynamicManagedType(JpaManagedTypeProvider provider,
	                                     JpaManagedType delegate) {

		super();
		this.provider = provider;
		this.delegate = delegate;
	}

	/**
	 * Creates a {@link IType} that supports dynamic type, which means the type cannot be found in
	 * the project but is only in memory.
	 *
	 * @return A new {@link EclipseLinkDynamicType}
	 */
	protected EclipseLinkDynamicType buildDynamicType() {
		return new EclipseLinkDynamicType(
			delegate.getProvider().getTypeRepository(),
			delegate.getManagedType().getPersistentType().getName()
		);
	}

	/**
	 * Creates the external form that needs to wrap the given {@link AttributeMapping}. By default,
	 * the call is delegated to {@link IMappingBuilder}.
	 *
	 * @param mapping The mapping to wrap with a {@link IMapping}
	 * @return A new concrete instance of {@link IMapping}
	 */
	protected EclipseLinkDynamicMapping buildMapping(EclipseLinkMapping mapping) {
		return new EclipseLinkDynamicMapping(this, mapping);
	}

	/**
	 * Creates an external form for each {@link AttributeMapping} and stores them by using their
	 * mapping name.
	 *
	 * @return The mappings mapped by their name
	 */
	protected Map<String, EclipseLinkDynamicMapping> buildMappings() {

		Map<String, EclipseLinkDynamicMapping> mappings = new HashMap<String, EclipseLinkDynamicMapping>();

		for (IMapping mapping : delegate.mappings()) {
			mappings.put(mapping.getName(), buildMapping((EclipseLinkMapping) mapping));
		}

		return mappings;
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(IManagedType managedType) {
		return delegate.compareTo(managedType);
	}

	/**
	 * Returns the default implementation of {@link IManagedType} that is wrapped by this one.
	 *
	 * @return The actual {@link JpaManagedType}
	 */
	public JpaManagedType getDelegate() {
		return delegate;
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
			type = buildDynamicType();
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
		return IterableTools.<IMapping>cloneSnapshot(mappings.values());
	}
}