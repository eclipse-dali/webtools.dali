/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.NamedColumn;


public interface JavaNamedColumn extends NamedColumn, JavaJpaContextNode
{

	Owner owner();
	
	/**
	 * Return the (best guess) text location of the column's name.
	 */
	TextRange nameTextRange(CompilationUnit astRoot);

	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner extends NamedColumn.Owner
	{
		/**
		 * Return the column owner's text range. This can be returned by the
		 * column when its annotation is not present.
		 */
		TextRange validationTextRange(CompilationUnit astRoot);

	}
}