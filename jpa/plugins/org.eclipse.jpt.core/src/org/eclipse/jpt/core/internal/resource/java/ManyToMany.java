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


public interface ManyToMany extends RelationshipMappingAnnotation
{

	/**
	 * Corresponds to the mappedBy element of the ManyToMany annotation. 
	 * Returns null if the mappedBy element does not exist in java.
	 */
	String getMappedBy();
	
	/**
	 * Corresponds to the mappedBy element of the ManyToMany annotation. 
	 * Set to null to remove the mappedBy element.
	 */
	void setMappedBy(String mappedBy);
}
