/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.common;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaEObject extends EObject
{
	/**
	 * Return the resource that most directly contains this object
	 */
	IResource platformResource();
	
	/**
	 * Return the JpaXmlResource containing this object.
	 */
	JpaXmlResource resource();
	
	/**
	 * Return the root object of the model
	 */
	JpaEObject root();
	
	/**
	 * Return true if this object's text representation contains the text offset
	 */
	boolean containsOffset(int textOffset);
	
	/**
	 * Return the text range to be used for validation.  This is the source
	 * range that will be highlighted for a validation error.
	 */
	TextRange validationTextRange();
	
	/**
	 * Return the text range to be used for selection.  This is the source
	 * range that will be highlighted when selecting in the structure view.
	 */
	TextRange selectionTextRange();
	
	
	boolean isAllFeaturesUnset();
}
