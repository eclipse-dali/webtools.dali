/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Context <code>orm.xml</code> persistent <em>attribute</em>
 * (field or property).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.0
 * @since 2.0
 */
public interface OrmPersistentAttribute
	extends PersistentAttribute, OrmReadOnlyPersistentAttribute
{
	/**
	 * Return an <code>orm.xml</code> mapping since the attribute is
	 * <em>specified</em>.
	 */
	OrmAttributeMapping getMapping();

	/**
	 * Remove the attribute from the <code>orm.xml</code> file and the
	 * list of specified attributes. The attribute must currently be specified 
	 * (return false from isVirtual()).
	 * Return the new virtual attribute, if it exists.
	 * Return <code>null</code> if the attribute does not correspond to an
	 * attribute in the Java persistent type.
	 * <p>
	 * @see #isVirtual()
	 * @see #setMappingKey(String)
	 */
	OrmReadOnlyPersistentAttribute removeFromXml();

	OrmAttributeMapping setMappingKey(String key);

	/**
	 * Called by the attribute's mapping when it's name changes,
	 * effectively changing the attribute's name.
	 */
	void nameChanged(String oldName, String newName);


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
}
