/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

/**
 * Corresponds to the javax.persistence.DiscriminatorColumn annotation
 */
public interface DiscriminatorColumn extends NamedColumn
{
	/**
	 * Corresponds to the discriminatorType element of the javax.persistence.DiscriminatorColumn annotation.
	 * Returns null if the discriminatorType valuePair does not exist in the annotation
	 */
	DiscriminatorType getDiscriminatorType();
	
	/**
	 * Corresponds to the discriminatorType element of the javax.persistence.OrderBy annotation.
	 * Set the discriminatorType to null to remove the discriminatorType valuePair
	 */
	void setDiscriminatorType(DiscriminatorType discriminatorType);
	
	/**
	 * Corresponds to the length element of the javax.persistence.DiscriminatorColumn annotation.
	 * Returns -1 if the length valuePair does not exist in the annotation
	 */
	int getLength();
	
	/**
	 * Corresponds to the length element of the javax.persistence.OrderBy annotation.
	 * Set the length to -1 to remove the length valuePair
	 */
	void setLength(int length);

}
