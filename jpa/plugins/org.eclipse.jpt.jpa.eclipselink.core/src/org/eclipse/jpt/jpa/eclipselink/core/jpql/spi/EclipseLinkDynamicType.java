/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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