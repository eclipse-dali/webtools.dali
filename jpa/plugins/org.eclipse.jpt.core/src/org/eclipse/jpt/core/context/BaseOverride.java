/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;


public interface BaseOverride extends JpaContextNode
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
		/**
		 * Return the type mapping that this override is contained in
		 * @return
		 */
		TypeMapping typeMapping();

		/**
		 * Teturn whether the given override is virtual. virtual means that
		 * it is not specified, but defaulted in from the mapped superclass or
		 * embeddable.
		 */
		boolean isVirtual(BaseOverride override);
	}
}