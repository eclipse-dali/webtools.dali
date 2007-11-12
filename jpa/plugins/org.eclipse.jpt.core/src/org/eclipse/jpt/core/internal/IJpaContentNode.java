/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IJpa Content Node</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaContentNode()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJpaContentNode extends IJpaSourceObject
{
	/**
	 * Return a unique identifier for all of this class of content nodes
	 */
	Object getId();
	
	/**
	 * Return the root content node containing this node
	 */
	IJpaRootContentNode getRoot();
	
	/**
	 * Return the text range to be used for selection. This is the text you want selected
	 * when selecting the object in the editor. StructureView uses this for selection
	 * from the structure to the source editor.
	 */
	ITextRange selectionTextRange();
}
