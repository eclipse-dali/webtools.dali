/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
package org.eclipse.jpt.jpa.core.jpql.spi;

import org.eclipse.persistence.jpa.jpql.tools.spi.IType;

/**
 * An internal interface used by all 3 implementations of {@link IType}.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see JavaType
 * @see JpaType
 * @see SimpleType
 *
 * @version 3.1
 * @since 3.0
 * @author Pascal Filion
 */
public interface IJpaType extends IType {

	/**
	 * Manually sets the declaration of this {@link IType}, which gives the information about type
	 * parameters, dimensionality, etc.
	 *
	 * @param typeDeclaration The external form of the type declaration
	 */
	void setTypeDeclaration(JpaTypeDeclaration typeDeclaration);
}