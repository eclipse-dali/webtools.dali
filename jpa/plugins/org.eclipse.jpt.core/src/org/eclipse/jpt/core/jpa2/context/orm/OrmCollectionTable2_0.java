/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmReferenceTable;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.jpa2.context.CollectionTable2_0;

/**
 * orm.xml collection table
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmCollectionTable2_0
	extends CollectionTable2_0, OrmReferenceTable
{
	void update();
		
	void initializeFrom(CollectionTable2_0 oldCollectionTable);

	// ********** covariant overrides **********
	
	ListIterator<OrmJoinColumn> joinColumns();

	OrmJoinColumn getDefaultJoinColumn();
	
	ListIterator<OrmJoinColumn> specifiedJoinColumns();

	OrmJoinColumn addSpecifiedJoinColumn(int index);
	
	ListIterator<OrmUniqueConstraint> uniqueConstraints();
	
	OrmUniqueConstraint addUniqueConstraint(int index);

}
