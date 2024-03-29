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

import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedNamedColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumnAnnotation2_0;

/**
 * Java specified order column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JavaSpecifiedOrderColumn2_0
	extends SpecifiedOrderColumn2_0, JavaSpecifiedNamedColumn
{
	OrderColumnAnnotation2_0 getColumnAnnotation();


	// ********** parent adapter **********

	/**
	 * interface allowing order columns to be used in multiple places
	 */
	interface ParentAdapter
		extends NamedColumn.ParentAdapter
	{
		OrderColumnAnnotation2_0 getColumnAnnotation();
		void removeColumnAnnotation();
	}
}
