/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * common interface for discriminator columns
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public interface ReadOnlyNamedDiscriminatorColumn
	extends NamedColumn
{

	// ********** discriminator type **********

	/**
	 * Return the specified discriminator type if present,
	 * otherwise return the default discriminator type.
	 */
	DiscriminatorType getDiscriminatorType();
	DiscriminatorType getSpecifiedDiscriminatorType();
		String SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY = "specifiedDiscriminatorType"; //$NON-NLS-1$
	DiscriminatorType getDefaultDiscriminatorType();
		String DEFAULT_DISCRIMINATOR_TYPE_PROPERTY = "defaultDiscriminatorType"; //$NON-NLS-1$
		DiscriminatorType DEFAULT_DISCRIMINATOR_TYPE = DiscriminatorType.STRING;


	// ********** length **********

	/**
	 * Return the specified length if present,
	 * otherwise return the default length.
	 */
	int getLength();
	Integer getSpecifiedLength();
		String SPECIFIED_LENGTH_PROPERTY = "specifiedLength"; //$NON-NLS-1$
	int getDefaultLength();
		String DEFAULT_LENGTH_PROPERTY = "defaultLength"; //$NON-NLS-1$
		int DEFAULT_LENGTH = 31;
		

	// ********** owner **********

	/**
	 * interface allowing discriminator columns to be used in multiple places
	 * (but pretty much just entities)
	 */
	interface Owner
		extends NamedColumn.Owner
	{
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
