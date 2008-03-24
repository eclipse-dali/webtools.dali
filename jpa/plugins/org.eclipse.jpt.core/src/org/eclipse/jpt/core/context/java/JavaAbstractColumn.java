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
import org.eclipse.jpt.core.context.AbstractColumn;
import org.eclipse.jpt.core.utility.TextRange;


public interface JavaAbstractColumn extends AbstractColumn, JavaNamedColumn
{

	/**
	 * Return the (best guess) text location of the column's table.
	 */
	TextRange tableTextRange(CompilationUnit astRoot);

	Owner owner();
	
	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner extends JavaNamedColumn.Owner, AbstractColumn.Owner
	{

	}
}