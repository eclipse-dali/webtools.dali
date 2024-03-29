/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.xml;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.text.edits.DeleteEdit;

/**
 * Common Dali behavior for EMF objects.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.2
 */
public interface EBaseObject
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
	 * Return the text range to be used for validation. This is the source
	 * range that will be marked for a validation error.
	 */
	TextRange getValidationTextRange();

	/**
	 * Return the text range to be used for selection. This is the source
	 * range that will be highlighted when the node is selected by
	 * clicking in the structure view.
	 */
	TextRange getSelectionTextRange();

	/**
	 * Return the text range to be used for text selection.
	 * When the cursor is moved to within this source range in the text editor,
	 * the node will be selected in the structure view.
	 */
	TextRange getFullTextRange();


	// ********** refactoring **********

	/**
	 * Create a text DeleteEdit for deleting the entire IDOMNode and any text that precedes it.
	 */
	DeleteEdit createDeleteEdit();
}
