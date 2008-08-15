/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.resource.orm.XmlEntity;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmTable extends Table, OrmJpaContextNode
{
	void initialize(XmlEntity resourceEntity);

	/**
	 * Update the OrmTable context model object to match the XmlEntity 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlEntity resourceEntity);

	
	//****************** covariant overrides *******************

	@SuppressWarnings("unchecked")
	ListIterator<OrmUniqueConstraint> uniqueConstraints();
	
	OrmUniqueConstraint addUniqueConstraint(int index);
}