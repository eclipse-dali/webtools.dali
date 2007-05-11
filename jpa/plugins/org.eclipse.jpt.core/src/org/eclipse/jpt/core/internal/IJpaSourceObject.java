/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved.  This program and 
 *  the accompanying materials are made available under the terms of the 
 *  Eclipse Public License v1.0 which accompanies this distribution, and is 
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal;

/**
 * A JPA object that can be mapped to a source location, contained within a 
 * JPA file.
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaSourceObject()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJpaSourceObject extends IJpaEObject
{
	/**
	 * Return the JPA file containing this object.
	 */
	IJpaFile getJpaFile();

	/**
	 * Return the text range to be used for validation.  This is the source
	 * range that will be highlighted for a validation error.
	 */
	ITextRange validationTextRange();
}
