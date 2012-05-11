/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.core.resource.xml;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Common Dali behavior for EMF objects.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public interface JpaEObject
	extends EObject
{
	/**
	 * Return whether all the object's EMF features are "unset".
	 */
	boolean isUnset();
	
	/**
	 * Return true if this object's text representation contains the text offset
	 */
	boolean containsOffset(int textOffset);

	/**
	 * Return the text range to be used for validation.  This is the source
	 * range that will be highlighted for a validation error.
	 */
	TextRange getValidationTextRange();

	/**
	 * Return the text range to be used for selection.  This is the source
	 * range that will be highlighted when selecting in the structure view.
	 */
	TextRange getSelectionTextRange();
}
