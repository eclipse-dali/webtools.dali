/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionMapping2_0;

/**
 * <code>orm.xml</code> collection mapping (e.g. 1:m, m:m, element collection)
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
public interface OrmCollectionMapping2_0
	extends CollectionMapping2_0, OrmConvertibleKeyMapping2_0
{
	OrmAttributeOverrideContainer getMapKeyAttributeOverrideContainer();

	ListIterable<? extends OrmSpecifiedJoinColumn> getSpecifiedMapKeyJoinColumns();
	OrmSpecifiedJoinColumn getSpecifiedMapKeyJoinColumn(int index);
	OrmSpecifiedJoinColumn addSpecifiedMapKeyJoinColumn();
	OrmSpecifiedJoinColumn addSpecifiedMapKeyJoinColumn(int index);

	OrmSpecifiedJoinColumn getDefaultMapKeyJoinColumn();
}
