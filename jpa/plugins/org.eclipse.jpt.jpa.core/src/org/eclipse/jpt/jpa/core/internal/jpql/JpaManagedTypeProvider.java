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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.persistence.PersistentTypeContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaProject;
import org.eclipse.persistence.jpa.jpql.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.spi.IJPAVersion;
import org.eclipse.persistence.jpa.jpql.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeVisitor;
import org.eclipse.persistence.jpa.jpql.spi.IMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IPlatform;
import org.eclipse.persistence.jpa.jpql.spi.IType;

/**
 * The abstract implementation of {@link IManagedTypeProvider} that is wrapping the design-time
 * representation of a provider of managed types.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
abstract class JpaManagedTypeProvider implements IManagedTypeProvider {

	/**
	 * The filtered collection of managed types that are the abstract schema types.
	 */
	private Collection<IEntity> abstractSchemaTypes;

	/**
	 * The project that gives access to the application's metadata.
	 */
	private final JpaProject jpaProject;

	/**
	 * The cached {@link IManagedType managed types}.
	 */
	private Map<String, IManagedType> managedTypes;

	/**
	 * The design-time provider of managed types.
	 */
	private final PersistentTypeContainer persistentTypeContainer;

	/**
	 * The external form of a type repository.
	 */
	private JpaTypeRepository typeRepository;

	/**
	 * The version of the Java Persistence this entity for which it was defined.
	 */
	private IJPAVersion version;

	/**
	 * Creates a new <code>JpaManagedTypeProvider</code>.
	 *
	 * @param jpaProject The project that gives access to the application's metadata
	 * @param persistentTypeContainer The design-time provider of managed types
	 */
	JpaManagedTypeProvider(JpaProject jpaProject, PersistentTypeContainer persistentTypeContainer) {

		super();
		this.jpaProject = jpaProject;
		this.persistentTypeContainer = persistentTypeContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IEntity> abstractSchemaTypes() {
		if (abstractSchemaTypes == null) {
			initializeManagedTypes();
			EntityCollector visitor = new EntityCollector();
			for (IManagedType managedType : managedTypes.values()) {
				managedType.accept(visitor);
			}
			abstractSchemaTypes = visitor.entities;
		}
		return Collections.unmodifiableCollection(abstractSchemaTypes);
	}

	abstract JpaEntity buildEntity(TypeMapping mappedClass);

	private IManagedType buildManagedType(PersistentType persistentType) {

		TypeMapping mappedClass = persistentType.getMapping();

		if (mappedClass instanceof Entity) {
			return buildEntity(mappedClass);
		}

		if (mappedClass instanceof MappedSuperclass) {
			return new JpaMappedSuperclass(this, (MappedSuperclass) mappedClass);
		}

		if (mappedClass instanceof Embeddable) {
			return new JpaEmbeddable(this, (Embeddable) mappedClass);
		}

		return new JpaNullManagedType(this, mappedClass);
	}

	private Map<String, IManagedType> buildManagedTypes() {
		Map<String, IManagedType> managedTypes = new HashMap<String, IManagedType>();
		for (Iterator<? extends PersistentType> iter = persistenceTypes(); iter.hasNext(); ) {
			PersistentType persistentType = iter.next();
			if (persistentType != null) {
				managedTypes.put(persistentType.getMapping().getName(), buildManagedType(persistentType));
			}
		}
		return managedTypes;
	}

	private IJPAVersion convert(JpaPlatform.Version version) {

		String jpaVersion = version.getJpaVersion();

		if (JpaFacet.VERSION_1_0.getVersionString().equals(jpaVersion)) {
			return IJPAVersion.VERSION_1_0;
		}

		return IJPAVersion.VERSION_2_0;
	}

	/**
	 * {@inheritDoc}
	 */
	public IManagedType getManagedType(IType type) {

		initializeManagedTypes();

		for (IManagedType managedType : managedTypes.values()) {
			if (managedType.getType() == type) {
				return managedType;
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public IManagedType getManagedType(String abstractSchemaName) {
		initializeManagedTypes();
		return managedTypes.get(abstractSchemaName);
	}

	/**
	 * Returns the container of managed types.
	 *
	 * @return The container of managed types
	 */
	PersistentTypeContainer getPersistentTypeContainer() {
		return persistentTypeContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPlatform getPlatform() {
		return (jpaProject instanceof GenericJpaProject) ? IPlatform.JAVA  : IPlatform.ECLIPSE_LINK;
	}

	/**
	 * Returns the encapsulated {@link PersistentType}, which is the actual object.
	 *
	 * @return The design-time representation of a managed type provider
	 */
	PersistentTypeContainer getProvider() {
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

	/**
	 * {@inheritDoc}
	 */
	public IJPAVersion getVersion() {
		if (version == null) {
			version = convert(jpaProject.getJpaPlatform().getJpaVersion());
		}
		return version;
	}

	private void initializeManagedTypes() {
		if (managedTypes == null) {
			managedTypes = buildManagedTypes();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IManagedType> managedTypes() {
		initializeManagedTypes();
		return Collections.unmodifiableCollection(managedTypes.values());
	}

	/**
	 * Retrieves the managed types from the design-time provider.
	 *
	 * @return The managed types that are defined only in the provider
	 */
	abstract Iterator<? extends PersistentType> persistenceTypes();

	private static class EntityCollector implements IManagedTypeVisitor {

		/**
		 * The collection of {@link IEntity entities} that got visited.
		 */
		private final Collection<IEntity> entities;

		/**
		 * Creates a new <code>EntityCollector</code>.
		 */
		EntityCollector() {
			super();
			entities = new ArrayList<IEntity>();
		}

		/**
		 * {@inheritDoc}
		 */
		public void visit(IEmbeddable embeddable) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void visit(IEntity entity) {
			entities.add(entity);
		}

		/**
		 * {@inheritDoc}
		 */
		public void visit(IMappedSuperclass mappedSuperclass) {
		}
	}
}