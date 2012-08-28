/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.jpa.core.internal.context.JptValidator;

/**
 * JPA attribute mapping converter.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.1
 */
public interface Converter
	extends JpaContextNode
{
	AttributeMapping getParent();

	/**
	 * Return the converter's type.
	 */
	Class<? extends Converter> getType();


	// ********** owner **********

	/**
	 * Interface allowing converters to be used in multiple places
	 * (e.g. basic mappings, collection mappings, etc).
	 */
	public interface Owner
	{
		/**
		 * 
		 */
		JptValidator buildValidator(Converter converter);
	}

}
