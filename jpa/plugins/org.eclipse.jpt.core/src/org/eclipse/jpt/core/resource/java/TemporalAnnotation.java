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
 * Corresponds to the javax.persistence.Temporal annotation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TemporalAnnotation extends JavaResourceNode
{
	String ANNOTATION_NAME = JPA.TEMPORAL;

	/**
	 * Corresponds to the value element of the Temporal annotation.
	 * Returns null if the value element does not exist in java.
	 */
	TemporalType getValue();
	
	/**
	 * Corresponds to the value element of the Temporal annotation.
	 * Set to null to remove the value element.
	 */
	void setValue(TemporalType value);
		String VALUE_PROPERTY = "valueProperty";
	
	/**
	 * Return the {@link TextRange} for the value element.  If the value element 
	 * does not exist return the {@link TextRange} for the Temporal annotation.
	 */
	TextRange getValueTextRange(CompilationUnit astRoot);

}
