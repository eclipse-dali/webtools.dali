/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;



public interface IDiscriminatorColumn extends INamedColumn
{
	String DEFAULT_NAME = "DTYPE";

	DiscriminatorType getDiscriminatorType();

	DiscriminatorType getDefaultDiscriminatorType();
		String DEFAULT_DISCRIMINATOR_TYPE_PROPERTY = "defaultDiscriminatorTypeProperty";
		DiscriminatorType DEFAULT_DISCRIMINATOR_TYPE = DiscriminatorType.STRING;
		
	DiscriminatorType getSpecifiedDiscriminatorType();
	void setSpecifiedDiscriminatorType(DiscriminatorType newSpecifiedDiscriminatorType);
		String SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY = "specifiedDiscriminatorTypeProperty";


	Integer getLength();

	Integer getDefaultLength();
		Integer DEFAULT_LENGTH = Integer.valueOf(31);
		String DEFAULT_LENGTH_PROPERTY = "defaultLengthProperty";

	Integer getSpecifiedLength();
	void setSpecifiedLength(Integer value);
		String SPECIFIED_LENGTH_PROPERTY = "spcifiedLengthProperty";

}
