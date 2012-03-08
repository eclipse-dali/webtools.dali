/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.resource.orm.EnumType;

/**
 * <code>orm.xml</code> enumerated/map key enumerated converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmBaseEnumeratedConverter
	extends BaseEnumeratedConverter, OrmConverter
{
	// ********** owner **********

	/**
	 * 
	 */
	public interface Owner
		extends OrmConverter.Owner
	{
		EnumType getXmlEnumType();

		void setXmlEnumType(EnumType enumType);

		TextRange getEnumTextRange();
	}
}
