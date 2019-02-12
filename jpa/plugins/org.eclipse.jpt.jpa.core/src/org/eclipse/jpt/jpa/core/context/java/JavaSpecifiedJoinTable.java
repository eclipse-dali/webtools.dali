/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

/**
 * Java join table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface JavaSpecifiedJoinTable
	extends SpecifiedJoinTable, JavaSpecifiedReferenceTable
{
	JavaSpecifiedJoinTableRelationshipStrategy getParent();

	JoinTableAnnotation getTableAnnotation();


	// ********** inverse join columns **********

	ListIterable<JavaSpecifiedJoinColumn> getInverseJoinColumns();

	ListIterable<JavaSpecifiedJoinColumn> getSpecifiedInverseJoinColumns();
	JavaSpecifiedJoinColumn getSpecifiedInverseJoinColumn(int index);
	JavaSpecifiedJoinColumn addSpecifiedInverseJoinColumn();
	JavaSpecifiedJoinColumn addSpecifiedInverseJoinColumn(int index);

	JavaSpecifiedJoinColumn getDefaultInverseJoinColumn();


	// ********** parent adapter **********

	interface ParentAdapter
		extends Table.ParentAdapter<JavaSpecifiedJoinTableRelationshipStrategy>
	{
		// specify generic argument
	}
}
