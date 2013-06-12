/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;

/**
 * <em>Specified</em> <code>orm.xml</code> attribute override
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface OrmSpecifiedAttributeOverride
	extends SpecifiedAttributeOverride, OrmSpecifiedOverride
{
	/**
	 * Called when a default override is converted into a specified override.
	 * @see org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer
	 * AttributeOverrideContainer for a list of clients
	 */
	void initializeFrom(OrmVirtualAttributeOverride oldOverride);

	/**
	 * @see OrmAttributeOverrideContainer#initializeFrom(OrmAttributeOverrideContainer)
	 */
	void initializeFrom(OrmSpecifiedAttributeOverride oldOverride);

	OrmVirtualAttributeOverride convertToVirtual();

	XmlAttributeOverride getXmlOverride();

	OrmSpecifiedColumn getColumn();
}
