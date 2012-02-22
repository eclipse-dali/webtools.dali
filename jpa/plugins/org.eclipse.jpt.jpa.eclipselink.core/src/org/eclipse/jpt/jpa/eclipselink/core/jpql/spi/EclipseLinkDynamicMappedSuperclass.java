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

import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeProvider;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeVisitor;
import org.eclipse.persistence.jpa.jpql.spi.IMappedSuperclass;

/**
 * This implementation of an {@link IMappedSuperclass} that represents a dynamic mapped superclass.
 *
 * @version 3.2
 * @since 3.2
 * @author Pascal Filion
 */
public class EclipseLinkDynamicMappedSuperclass extends EclipseLinkDynamicManagedType
                                                implements IMappedSuperclass {

	/**
	 * Creates a new <code>EclipseLinkDynamicMappedSuperclass</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param delegate The default implementation of {@link IManagedType}
	 */
	public EclipseLinkDynamicMappedSuperclass(JpaManagedTypeProvider provider,
	                                          JpaMappedSuperclass delegate) {

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
	public JpaMappedSuperclass getDelegate() {
		return (JpaMappedSuperclass) super.getDelegate();
	}
}