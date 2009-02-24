/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface DiscriminatorColumn extends NamedColumn
{
	String DEFAULT_NAME = "DTYPE"; //$NON-NLS-1$

	DiscriminatorType getDiscriminatorType();

	DiscriminatorType getDefaultDiscriminatorType();
		String DEFAULT_DISCRIMINATOR_TYPE_PROPERTY = "defaultDiscriminatorType"; //$NON-NLS-1$
		DiscriminatorType DEFAULT_DISCRIMINATOR_TYPE = DiscriminatorType.STRING;
		
	DiscriminatorType getSpecifiedDiscriminatorType();
	void setSpecifiedDiscriminatorType(DiscriminatorType newSpecifiedDiscriminatorType);
		String SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY = "specifiedDiscriminatorType"; //$NON-NLS-1$


	int getLength();

	int getDefaultLength();
		int DEFAULT_LENGTH = 31;
		String DEFAULT_LENGTH_PROPERTY = "defaultLength"; //$NON-NLS-1$

	Integer getSpecifiedLength();
	void setSpecifiedLength(Integer value);
		String SPECIFIED_LENGTH_PROPERTY = "specifiedLength"; //$NON-NLS-1$

		
	/**
	 * interface allowing columns to be used in multiple places
	 */
	interface Owner extends NamedColumn.Owner {
		
		/**
		 * Return the default discriminator column length
		 */
		int getDefaultLength();
		
		/**
		 * Return the default discriminator column type
		 */
		DiscriminatorType getDefaultDiscriminatorType();
	}
}
