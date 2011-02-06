/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlNamedColumn;


/**
 * <code>orm.xml</code>
 * <ul>
 * <li>column
 * <li>join column
 * <li>primary key join column
 * <li>discriminator column
 * <li>order column
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface OrmNamedColumn
	extends NamedColumn, XmlContextNode
{
	AbstractXmlNamedColumn getXmlColumn();

	/**
	 * Return the (best guess) text location of the column's name.
	 */
	TextRange getNameTextRange();


	// ********** owner **********

	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner
		extends NamedColumn.Owner
	{
		/**
		 * Return the column owner's text range. This can be returned by the
		 * column when its annotation is not present.
		 */
		TextRange getValidationTextRange();
	}
}
