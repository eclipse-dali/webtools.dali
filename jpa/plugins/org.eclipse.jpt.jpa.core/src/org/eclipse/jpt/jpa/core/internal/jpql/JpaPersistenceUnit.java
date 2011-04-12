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

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

/**
 * The concrete implementation that is wrapping the design-time representation of a persistence unit.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
public final class JpaPersistenceUnit extends JpaManagedTypeProvider {

	/**
	 * Creates a new <code>JpaPersistenceUnit</code>.
	 *
	 * @param jpaProject The project that gives access to the application's metadata
	 * @param persistentUnit The design-time persistence unit
	 */
	public JpaPersistenceUnit(JpaProject jpaProject, PersistenceUnit persistentUnit) {
		super(jpaProject, persistentUnit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	JpaEntity buildEntity(TypeMapping mappedClass) {
		return new JpaPersistenceUnitEntity(this, (Entity) mappedClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	PersistenceUnit getPersistentTypeContainer() {
		return (PersistenceUnit) super.getPersistentTypeContainer();
	}

	@SuppressWarnings("unchecked")
	private Iterator<ClassRef> javaClassRefs() {
		return new CompositeIterator<ClassRef>(
			getPersistentTypeContainer().specifiedClassRefs(),
			getPersistentTypeContainer().impliedClassRefs()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	Iterator<? extends PersistentType> persistenceTypes() {
		return new TransformationIterator<ClassRef, PersistentType>(javaClassRefs()) {
			@Override
			protected PersistentType transform(ClassRef classRef) {
				return classRef.getJavaPersistentType();
			}
		};
	}
}