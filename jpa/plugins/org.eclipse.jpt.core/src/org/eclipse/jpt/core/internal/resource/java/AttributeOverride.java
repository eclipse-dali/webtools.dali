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
 * Corresponds to the javax.persistence.AttributeOverride annotation
 */
public interface AttributeOverride extends OverrideResource
{
	String ANNOTATION_NAME = JPA.ATTRIBUTE_OVERRIDE;
		
	/**
	 * Corresponds to the column element of the AttributeOverride annotation.
	 * Returns null if the column element does not exist in java.
	 */
	Column getColumn();
	
	/**
	 * Add the column element to the AttributeOverride annotation.
	 */
	Column addColumn();
	
	/**
	 * Remove the column element from the AttributeOverride annotation.
	 */
	void removeColumn();
	
	String COLUMN_PROPERTY = "columnProperty";

}
