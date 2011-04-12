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

import org.eclipse.persistence.jpa.jpql.spi.IType;

/**
 * An internal interface used by all 3 implementations of {@link IType}.
 *
 * @see JavaType
 * @see JpaType
 * @see SimpleType
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
interface IJpaType extends IType {

	/**
	 * Manually sets the declaration of this {@link IType}, which gives the information about type
	 * parameters, dimensionality, etc.
	 *
	 * @param typeDeclaration The external form of the type declaration
	 */
	void setTypeDeclaration(JpaTypeDeclaration typeDeclaration);
}