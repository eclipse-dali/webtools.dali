/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;


public interface IOverride extends IJpaContextNode
{

	String getName();
	void setName(String value);
		String NAME_PROPERTY = "nameProperty";
	
	/**
	 * Return true if override exists as specified on the owning object, or false
	 * if the override is "gotten for free" as a result of defaults calculation
	 */
	boolean isVirtual();

	interface Owner
	{
		ITypeMapping typeMapping();

		IAttributeMapping attributeMapping(String attributeName);

		boolean isVirtual(IOverride override);

		ITextRange validationTextRange(CompilationUnit astRoot);
	}
}