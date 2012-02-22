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

import org.eclipse.jpt.jpa.core.jpql.spi.JpaEmbeddable;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeVisitor;

/**
 * This implementation of an {@link IEmbeddable} that represents a dynamic embeddable.
 *
 * @version 2.4
 * @since 2.4
 * @author Pascal Filion
 */
public class EclipseLinkDynamicEmbeddable extends EclipseLinkDynamicManagedType
                                          implements IEmbeddable {

	/**
	 * Creates a new <code>EclipseLinkDynamicEmbeddable</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param delegate The default implementation of {@link IManagedType}
	 */
	public EclipseLinkDynamicEmbeddable(JpaManagedTypeProvider provider,
	                                    JpaEmbeddable delegate) {

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
	public JpaEmbeddable getDelegate() {
		return (JpaEmbeddable) super.getDelegate();
	}
}