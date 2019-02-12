/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;

/**
 * JPA 2.0 Java ordering
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface JavaOrderable2_0
	extends Orderable2_0
{
	JavaResourceAttribute getResourceAttribute();

	JavaSpecifiedOrderColumn2_0 getOrderColumn();


	// ************ parent adapter ************

	/**
	 * interface allowing ordering in multiple places
	 * (i.e. multi-value relationship and element collection mappings)
	 */
	interface ParentAdapter
		extends Orderable2_0.ParentAdapter<JavaAttributeMapping>
	{
		// specify generic argument
		class Null
			extends Orderable2_0.ParentAdapter.Null<JavaAttributeMapping>
			implements ParentAdapter
		{
			public Null(JavaAttributeMapping parent) {
				super(parent);
			}
		}

	}
}
