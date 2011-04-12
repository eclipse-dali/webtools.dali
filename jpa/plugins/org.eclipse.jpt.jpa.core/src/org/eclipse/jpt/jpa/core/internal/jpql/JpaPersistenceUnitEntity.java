/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpql;

import org.eclipse.jpt.jpa.core.context.Entity;

/**
 * The concrete implementation of {@link IEntity} that is wrapping the design-time representation
 * of a JPA entity defined in a persistence unit.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class JpaPersistenceUnitEntity extends JpaEntity {

	/**
	 * Creates a new <code>JpaPersistenceUnitEntity</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param entity The design-time model object wrapped by this class
	 */
	JpaPersistenceUnitEntity(JpaManagedTypeProvider provider, Entity entity) {
		super(provider, entity);
	}
}