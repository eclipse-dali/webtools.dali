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

import org.eclipse.jpt.jpa.core.jpql.spi.JpaEmbeddable;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedTypeVisitor;

/**
 * This implementation of an {@link IEmbeddable} that represents a dynamic embeddable.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
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