/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;

public interface JavaBaseJoinColumn extends BaseJoinColumn, JavaNamedColumn
{

	/**
	 * Return the (best guess) text location of the referenced column name
	 */
	TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot);

	
	Owner getOwner();
	/**
	 * interface allowing join columns to be used in multiple places
	 * (e.g. 1:1 mappings and join tables)
	 */
	interface Owner extends BaseJoinColumn.Owner, JavaNamedColumn.Owner
	{
		// nothing?
	}
}