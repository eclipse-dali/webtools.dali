/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;

/**
 * JPA 2.0 Java persistent attribute (field or property)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaPersistentAttribute2_0
	extends JavaPersistentAttribute, PersistentAttribute2_0
{
	@SuppressWarnings("nls")
	Iterable<String> STANDARD_STATIC_METAMODEL_FIELD_MODIFIERS =
		new ArrayIterable<String>(new String[] { "public", "static", "volatile" });

}
