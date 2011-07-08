/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;

/**
 * Java read-only join column or primary key join column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaReadOnlyBaseJoinColumn
	extends ReadOnlyBaseJoinColumn, JavaReadOnlyNamedColumn
{
	/**
	 * Return the (best guess) text location of the join column's
	 * referenced column name.
	 */
	TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot);


	// ********** owner **********

	/**
	 * interface allowing join columns to be used in multiple places
	 * (e.g. 1:1 mappings and join tables)
	 */
	interface Owner
		extends ReadOnlyBaseJoinColumn.Owner, JavaReadOnlyNamedColumn.Owner
	{
		// combine interfaces
	}
}
