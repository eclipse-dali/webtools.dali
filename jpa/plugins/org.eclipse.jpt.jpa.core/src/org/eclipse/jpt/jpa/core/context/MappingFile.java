/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistentTypeContainer;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * JPA mapping file (typically <code>orm.xml</code>).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.1
 */
public interface MappingFile
	extends XmlFile, PersistentTypeContainer
{
	/**
	 * Covariant override.
	 */
	MappingFileRef getParent();

	/**
	 * Return the mapping file's root.
	 * This can be <code>null</code>.
	 */
	MappingFileRoot getRoot();

	/**
	 * Return whether the mapping file exists in the specified folder.
	 */
	boolean isIn(IFolder folder);


	// ********** refactoring **********

	/**
	 * Create DeleteEdits for deleting references (if any) to the type about to be deleted.
	 * Return an EmptyIterable if there are not any references to the given type.
	 */
	Iterable<DeleteEdit> createDeleteTypeEdits(IType type);

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
}
