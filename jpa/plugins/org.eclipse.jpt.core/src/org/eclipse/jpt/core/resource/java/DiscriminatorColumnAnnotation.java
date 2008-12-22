/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

/**
 * Corresponds to the javax.persistence.DiscriminatorColumn annotation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface DiscriminatorColumnAnnotation extends NamedColumnAnnotation
{
	String ANNOTATION_NAME = JPA.DISCRIMINATOR_COLUMN;

	/**
	 * Corresponds to the discriminatorType element of the javax.persistence.DiscriminatorColumn annotation.
	 * Returns null if the discriminatorType valuePair does not exist in the annotation
	 */
	DiscriminatorType getDiscriminatorType();
	
	/**
	 * Corresponds to the discriminatorType element of the javax.persistence.DiscriminatorColumn annotation.
	 * Set the discriminatorType to null to remove the discriminatorType valuePair
	 */
	void setDiscriminatorType(DiscriminatorType discriminatorType);
		String DISCRIMINATOR_TYPE_PROPERTY = "discriminatorType"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the length element of the javax.persistence.DiscriminatorColumn annotation.
	 * Returns null if the length valuePair does not exist in the annotation
	 */
	Integer getLength();
	
	/**
	 * Corresponds to the length element of the javax.persistence.DiscriminatorColumn annotation.
	 * Set the length to null to remove the length valuePair
	 */
	void setLength(Integer length);
		String LENGTH_PROPERTY = "length"; //$NON-NLS-1$

}
