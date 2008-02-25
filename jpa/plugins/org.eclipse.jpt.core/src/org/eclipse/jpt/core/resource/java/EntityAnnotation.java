/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;

/**
 * Corresponds to the javax.persistence.Entity annotation
 */
public interface EntityAnnotation extends JavaResourceNode
{
	String ANNOTATION_NAME = JPA.ENTITY;

	/**
	 * Corresponds to the name element of the Entity annotation.
	 * Returns null if the name element does not exist in java.
	 */
	String getName();
	
	/**
	 * Corresponds to the name element of the Entity annotation.
	 * Set to null to remove the name element.
	 */
	void setName(String name);
	
	String NAME_PROPERTY = "nameProperty";
	
	/**
	 * Return the ITextRange for the name element.  If the name element 
	 * does not exist return the ITextRange for the Entity annotation.
	 */
	TextRange nameTextRange(CompilationUnit astRoot);

}
