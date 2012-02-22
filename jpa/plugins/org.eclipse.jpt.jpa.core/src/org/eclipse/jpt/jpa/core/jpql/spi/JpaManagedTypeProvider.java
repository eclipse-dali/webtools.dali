/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.persistence.PersistentTypeContainer;
import org.eclipse.persistence.jpa.jpql.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.spi.IMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IMappingBuilder;
import org.eclipse.persistence.jpa.jpql.spi.IType;
import org.eclipse.persistence.jpa.jpql.util.iterator.CloneIterator;
import org.eclipse.persistence.jpa.jpql.util.iterator.IterableIterator;

/**
 * An implementation of {@link IManagedTypeProvider} that is wrapping the design-time representation
 * of a provider of managed types.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.2
 * @since 3.0
 * @author Pascal Filion
 */
public class JpaManagedTypeProvider implements IManagedTypeProvider {

	/**
	 * The filtered collection of managed types that are {@link IEmbeddable}.
	 */
	private Collection<IEmbeddable> embeddables;

	/**
	 * The filtered collection of managed types that are the entities.
	 */
	private Collection<IEntity> entities;

	/**
	 * The project that gives access to the application's metadata.
	 */
	private final JpaProject jpaProject;

	/**
	 * The builder that is responsible to create the {@link IManagedType} wrapping a type mapping.
	 */
	private IManagedTypeBuilder managedTypeBuilder;

	/**
	 * The cached {@link IManagedType managed types}.
	 */
	private Map<String, IManagedType> managedTypes;

	/**
	 * The filtered collection of managed types that are {@link IMappedSuperclass}.
	 */
	private Collection<IMappedSuperclass> mappedSuperclasses;

	/**
	 * The builder that is responsible to create the {@link IMapping} wrapping a persistent attribute
	 * or property.
	 */
	private IMappingBuilder<AttributeMapping> mappingBuilder;

	/**
	 * The design-time provider of managed types.
	 */
	private final PersistentTypeContainer persistentTypeContainer;

	/**
	 * The external form of a type repository.
	 */
	private JpaTypeRepository typeRepository;

	/**
	 * Creates a new <code>JpaManagedTypeProvider</code>.
	 *
	 * @param jpaProject The project that gives access to the application's metadata
	 * @param persistentTypeContainer The design-time provider of managed types
	 * @param managedTypeBuilder The builder that is responsible to create the {@link IManagedType}
	 * wrapping a type mapping
	 * @param mappingBuilder The builder that is responsible to create the {@link IMapping} wrapping
	 * a persistent attribute or property
	 */
	public JpaManagedTypeProvider(JpaProject jpaProject,
	                              PersistentTypeContainer persistentTypeContainer,
	                              IManagedTypeBuilder managedTypeBuilder,
	                              IMappingBuilder<AttributeMapping> mappingBuilder) {

		super();
		this.jpaProject = jpaProject;
		this.mappingBuilder = mappingBuilder;
		this.managedTypeBuilder = managedTypeBuilder;
		this.persistentTypeContainer = persistentTypeContainer;
	}

	/**
	 * Creates a new <code>JpaManagedTypeProvider</code>.
	 *
	 * @param jpaProject The project that gives access to the application's metadata
	 * @param persistentTypeContainer The design-time provider of managed types
	 * @param mappingBuilder The builder that is responsible to create the {@link IMapping} wrapping
	 * a persistent attribute or property
	 */
	public JpaManagedTypeProvider(JpaProject jpaProject,
	                              PersistentTypeContainer persistentTypeContainer,
	                              IMappingBuilder<AttributeMapping> mappingBuilder) {

		this(jpaProject, persistentTypeContainer, new JpaManagedTypeBuilder(), mappingBuilder);
	}

	protected IEmbeddable buildEmbeddable(Embeddable mappedClass) {
		return managedTypeBuilder.buildEmbeddable(this, mappedClass, mappingBuilder);
	}

	protected IEntity buildEntity(Entity mappedClass) {
		return managedTypeBuilder.buildEntity(this, mappedClass, mappingBuilder);
	}

	protected IManagedType buildManagedType(PersistentType persistentType) {

		TypeMapping mappedClass = persistentType.getMapping();

		if (mappedClass instanceof Entity) {
			IEntity entity = buildEntity((Entity) mappedClass);
			entities.add(entity);
			return entity;
		}

		if (mappedClass instanceof MappedSuperclass) {
			IMappedSuperclass mappedSuperclass = buildMappedSuperclass((MappedSuperclass) mappedClass);
			mappedSuperclasses.add(mappedSuperclass);
			return mappedSuperclass;
		}

		if (mappedClass instanceof Embeddable) {
			IEmbeddable embeddable = buildEmbeddable((Embeddable) mappedClass);
			embeddables.add(embeddable);
			return embeddable;
		}

		return new JpaNullManagedType(this, mappedClass);
	}

	protected Map<String, IManagedType> buildManagedTypes() {

		Map<String, IManagedType> managedTypes = new HashMap<String, IManagedType>();

		for (PersistentType persistentType : getPersistentTypeContainer().getPersistentTypes()) {

			if (persistentType != null) {
				String name = persistentType.getMapping().getName();

				// If the persistent type is the overridden (annotation) one and the ORM
				// one is already added, then don't override the ORM one
				if (managedTypes.containsKey(name) && persistentType.getOverriddenPersistentType() == null) {
					continue;
				}

				managedTypes.put(name, buildManagedType(persistentType));
			}
		}

		return managedTypes;
	}

	protected IMappedSuperclass buildMappedSuperclass(MappedSuperclass mappedClass) {
		return managedTypeBuilder.buildMappedSuperclass(this, mappedClass, mappingBuilder);
	}

	/**
	 * {@inheritDoc}
	 */
	public IterableIterator<IEntity> entities() {
		initializeManagedTypes();
		return new CloneIterator<IEntity>(entities);
	}

	/**
	 * {@inheritDoc}
	 */
	public IEmbeddable getEmbeddable(IType type) {
		return getEmbeddable(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public IEmbeddable getEmbeddable(String typeName) {
		return getManagedType(embeddables, typeName);
	}

	/**
	 * {@inheritDoc}
	 */
	public IEntity getEntity(IType type) {
		return getEntity(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public IEntity getEntity(String typeName) {
		return getManagedType(entities, typeName);
	}

	/**
	 * {@inheritDoc}
	 */
	public IEntity getEntityNamed(String entityName) {

		initializeManagedTypes();

		for (IEntity entity : entities) {
			if (entity.getName().equals(entityName)) {
				return entity;
			}
		}

		return null;
	}

	protected <T extends IManagedType> T getManagedType(Collection<T> managedTypes,
	                                                    String typeName) {

		initializeManagedTypes();

		for (T managedType : managedTypes) {
			if (managedType.getType().getName().equals(typeName)) {
				return managedType;
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public IManagedType getManagedType(IType type) {
		return getManagedType(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public IManagedType getManagedType(String typeName) {
		return getManagedType(managedTypes.values(), typeName);
	}

	/**
	 * {@inheritDoc}
	 */
	public IMappedSuperclass getMappedSuperclass(IType type) {
		return getMappedSuperclass(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public IMappedSuperclass getMappedSuperclass(String typeName) {
		return getManagedType(mappedSuperclasses, typeName);
	}

	/**
	 * Returns the container of managed types.
	 *
	 * @return The container of managed types
	 */
	public PersistentTypeContainer getPersistentTypeContainer() {
		return persistentTypeContainer;
	}

	/**
	 * Returns the encapsulated {@link PersistentType}, which is the actual object.
	 *
	 * @return The design-time representation of a managed type provider
	 */
	protected PersistentTypeContainer getProvider() {
		return persistentTypeContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	public JpaTypeRepository getTypeRepository() {
		if (typeRepository == null) {
			typeRepository = new JpaTypeRepository(jpaProject.getJavaProject());
		}
		return typeRepository;
	}

	protected void initializeManagedTypes() {
		if (managedTypes == null) {
			this.entities           = new LinkedList<IEntity>();
			this.embeddables        = new LinkedList<IEmbeddable>();
			this.mappedSuperclasses = new LinkedList<IMappedSuperclass>();
			this.managedTypes       = buildManagedTypes();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public IterableIterator<IManagedType> managedTypes() {
		initializeManagedTypes();
		return new CloneIterator<IManagedType>(managedTypes.values());
	}
}