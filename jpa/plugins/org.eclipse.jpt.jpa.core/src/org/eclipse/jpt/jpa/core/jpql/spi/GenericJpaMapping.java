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
package org.eclipse.jpt.jpa.core.jpql.spi;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.persistence.jpa.jpql.spi.IManagedType;

/**
 * A default implementation of Hermes' {@link IMapping}.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.1
 * @since 3.1
 * @author Pascal Filion
 */
public class GenericJpaMapping extends JpaMapping {

	/**
	 * Creates a new <code>GenericJpaMapping</code>.
	 *
	 * @param parent The parent of this mapping
	 * @param mapping The design-time {@link AttributeMapping} wrapped by this class
	 */
	public GenericJpaMapping(IManagedType parent, AttributeMapping mapping) {
		super(parent, mapping);
	}
}