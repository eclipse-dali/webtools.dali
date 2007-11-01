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
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.ITextRange;

public interface IJptEObject extends EObject
{
	
	/**
	 * Return the resource that most directly contains this object
	 */
	IResource platformResource();
	
	/**
	 * Return the JPA file containing this object.
	 */
	IJpaFile jpaFile();
	
	/**
	 * Return the IResourceModel containing this object.
	 */
	JptXmlResourceModel resourceModel();
	
	/**
	 * Return the root object of the model
	 */
	IJptEObject root();
	
	/**
	 * Return the text range to be used for validation.  This is the source
	 * range that will be highlighted for a validation error.
	 */
	ITextRange validationTextRange();
}
