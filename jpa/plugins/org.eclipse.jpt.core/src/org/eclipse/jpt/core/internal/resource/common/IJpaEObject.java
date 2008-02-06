/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.internal.resource.common;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.ITextRange;

public interface IJpaEObject extends EObject
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
	IJpaEObject root();
	
	/**
	 * Return the text range to be used for validation.  This is the source
	 * range that will be highlighted for a validation error.
	 */
	ITextRange validationTextRange();
	
	/**
	 * Return the text range to be used for selection.  This is the source
	 * range that will be highlighted when selecting in the structure view.
	 */
	ITextRange selectionTextRange();
	
	boolean isAllFeaturesUnset();

	/**
	 * Return whether the xml object contains the given offset into the text file.
	 */
	boolean contains(int offset);

}
