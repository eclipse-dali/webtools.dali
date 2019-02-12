/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.Options2_0;

/**
 * JPA 2.1 <code>persistence.xml</code> factory
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistenceXmlContextModelFactory2_0
	extends PersistenceXmlContextModelFactory
{
	Connection2_0 buildConnection(PersistenceUnit parent);

	Options2_0 buildOptions(PersistenceUnit parent);
}
