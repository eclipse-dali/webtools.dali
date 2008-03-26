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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Corresponds to the javax.persistence.Entity annotation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
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
	 * Return the {@link TextRange} for the name element.  If the name element 
	 * does not exist return the {@link TextRange} for the Entity annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);

}
