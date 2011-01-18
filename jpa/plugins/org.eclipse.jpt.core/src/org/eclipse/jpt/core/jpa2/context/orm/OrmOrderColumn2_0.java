/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;

/**
 * <code>orm.xml</code> order column
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
public interface OrmOrderColumn2_0
	extends OrderColumn2_0, OrmNamedColumn
{
	XmlOrderColumn getXmlColumn();


	// ********** owner **********

	/**
	 * interface allowing order columns to be used in multiple places
	 */
	interface Owner
		extends OrmNamedColumn.Owner
	{
		XmlOrderColumn getXmlColumn();
		
		XmlOrderColumn buildXmlColumn();
		
		void removeXmlColumn();
	}
}
