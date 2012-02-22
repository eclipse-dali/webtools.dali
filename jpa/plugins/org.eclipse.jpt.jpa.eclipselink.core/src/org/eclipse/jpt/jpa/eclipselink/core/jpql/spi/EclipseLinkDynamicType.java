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

import org.eclipse.jpt.jpa.core.jpql.spi.JpaTypeRepository;
import org.eclipse.jpt.jpa.core.jpql.spi.SimpleType;

/**
 * @version 3.2
 * @since 3.2
 * @author Pascal Filion
 */
public class EclipseLinkDynamicType extends SimpleType {

	/**
	 * Creates a new <code>EclipseLinkDynamicType</code>.
	 * @param typeRepository
	 * @param type
	 */
	public EclipseLinkDynamicType(JpaTypeRepository typeRepository, String typeName) {
		super(typeRepository, typeName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResolvable() {
		return true;
	}
}