/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.jpql.spi.JpaEntity;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeVisitor;
import org.eclipse.persistence.jpa.jpql.spi.IQuery;

/**
 * This implementation of an {@link IEntity} that represents a dynamic entity.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.2
 * @since 3.2
 * @author Pascal Filion
 */
public class EclipseLinkDynamicEntity extends EclipseLinkDynamicManagedType
                                      implements IEntity {

	/**
	 * Creates a new <code>EclipseLinkDynamicEmbeddable</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param delegate The default implementation of {@link IManagedType}
	 */
	public EclipseLinkDynamicEntity(JpaManagedTypeProvider provider, JpaEntity delegate) {
		super(provider, delegate);
	}

	/**
	 * {@inheritDoc}
	 */
	public void accept(IManagedTypeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JpaEntity getDelegate() {
		return (JpaEntity) super.getDelegate();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return getDelegate().getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public IQuery getNamedQuery(String queryName) {
		return getDelegate().getNamedQuery(queryName);
	}
}