/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.core.context.IdClassReference;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.XmlIdClassContainer;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * <code>orm.xml</code> ID class reference
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.3
 */
public interface OrmIdClassReference
	extends IdClassReference, XmlContextNode
{
	// ********** refactoring **********

	/**
	 * Create ReplaceEdits for renaming any references to the originalType to the newName.
	 * The originalType has not yet been renamed, the newName is the new short name.
	 */
	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);

	/**
	 * Create ReplaceEdits for moving any references to the originalType to the newPackage.
	 * The originalType has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

	/**
	 * Create ReplaceEdits for renaming any references to the originalPackage to the newName.
	 * The originalPackage has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);


	// ********** owner **********

	interface Owner {
		XmlIdClassContainer getXmlIdClassContainer();
		JavaIdClassReference getJavaIdClassReferenceForDefaults();
	}
}
