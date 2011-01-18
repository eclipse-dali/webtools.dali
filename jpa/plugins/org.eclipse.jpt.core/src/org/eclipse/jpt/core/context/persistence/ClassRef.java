/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Context model corresponding to the XML resource model
 * {@link XmlJavaClassRef}, which corresponds to the <code>class<code> element
 * in the <code>persistence.xml</code> file.
 * This is also used for "implied" class refs; i.e. class refs that are not
 * explicitly listed in the <code>persistence.xml</code> file.
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
public interface ClassRef
	extends XmlContextNode, JpaStructureNode, PersistentType.Owner
{
	/**
	 * Return whether the class ref is a reference to the specified type.
	 */
	boolean isFor(String typeName);

	/**
	 * Return <code>true</code> if the mapping file ref is <em>virtual</em>;
	 * return <code>false</code> if the mapping file ref is represented by an
	 * entry in the <code>persistence.xml</code> file.
	 */
	boolean isVirtual();

	/**
	 * Return the class ref's corresponding resource class ref.
	 * This is <code>null</code> for <em>implied</em> class refs.
	 */
	XmlJavaClassRef getXmlClassRef();

	/**
	 * Return whether the text representation of this persistence unit contains
	 * the given text offset
	 */
	boolean containsOffset(int textOffset);


	// ********** class name **********

	/**
	 * String constant associated with changes to the class name
	 */
	final static String CLASS_NAME_PROPERTY = "className"; //$NON-NLS-1$

	/**
	 * Return the class name of the class ref.
	 */
	String getClassName();

	/**
	 * Set the class name of the class ref.
	 */
	void setClassName(String className);


	// ********** java persistent type **********

	/**
	 * String constant associated with changes to the java persistent type
	 */
	final static String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentType"; //$NON-NLS-1$

	/**
	 * Return the JavaPersistentType that corresponds to this IClassRef.
	 * This can be null.
	 * This is not settable by users of this API.
	 */
	JavaPersistentType getJavaPersistentType();


	// ********** refactoring **********

	/**
	 * If this {@link #isFor(String)} the given IType, create a text 
	 * DeleteEdit for deleting the class element and any text that precedes it.
	 * Otherwise return an EmptyIterable.
	 * Though this will contain 1 or 0 DeleteEdits, using an Iterable
	 * for ease of use with other createDeleteEdit API.
	 */
	Iterable<DeleteEdit> createDeleteTypeEdits(IType type);

	/**
	 * Create ReplaceEdits for renaming the class element to the newName.
	 * The originalType has not yet been renamed, the newName is the new short name.
	 * If this ClassRef does not match the original type, then return an empty Iterable.
	 */
	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);

	/**
	 * Create ReplaceEdits for moving any references to the originalType to the newPackage.
	 * The originalType has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

	/**
	 * Create ReplaceEdits for renaming the class's package to the newName.
	 * The originalPackage has not yet been renamed.
	 * If this class is not a part of the original package, then return an empty Iterable.
	 */
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);
}
