/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * <code>orm.xml</code> persistent type
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
public interface OrmPersistentType
	extends PersistentType, PersistentType.Owner, XmlContextNode
{
	// ********** covariant overrides **********

	EntityMappings getParent();

	OrmTypeMapping getMapping();

	/**
	 * Return a combination of the persistent type's <em>specified</em> and
	 * <em>virtual</em> attributes. The <em>specified</em> attributes are those
	 * explicitly listed in the <code>orm.xml</code> file; while the
	 * <em>virtual</em> attributes are those derived from the corresponding
	 * Java persistent type.
	 */
	@SuppressWarnings("unchecked")
	ListIterator<OrmReadOnlyPersistentAttribute> attributes();

	OrmReadOnlyPersistentAttribute getAttributeNamed(String attributeName);


	// ********** specified attributes **********

	String SPECIFIED_ATTRIBUTES_LIST = "specifiedAttributes"; //$NON-NLS-1$

	/**
	 * Return the persistent type's specified attributes.
	 */
	ListIterator<OrmPersistentAttribute> specifiedAttributes();

	/**
	 * Return the number of the persistent type's specified attributes.
	 */
	int specifiedAttributesSize();

	// TODO this is currently only used by tests; remove it and change tests to use
	// OrmReadOnlyPersistenAttribute.convertToSpecified(String mappingKey)
	OrmPersistentAttribute addSpecifiedAttribute(String mappingKey, String attributeName);

// TODO bjv rename to 'defaultAttributes'
	// ********** default attributes **********

	String VIRTUAL_ATTRIBUTES_LIST = "virtualAttributes"; //$NON-NLS-1$

	/**
	 * Return virtual <code>orm.xml</code> persistent attributes. These
	 * are attributes that exist in the corresponding Java class, but are not
	 * specified in the <code>orm.xml</code>.
	 */
	ListIterator<OrmReadOnlyPersistentAttribute> virtualAttributes();

	/**
	 * Return the number of virtual <code>orm.xml</code> persistent attributes.
	 * @see #virtualAttributes()
	 */
	int virtualAttributesSize();

	/**
	 * Convert the specified attribute to a virtual attribute. Remove the
	 * attribute from the type's list of specified attributes
	 * and remove it from the <code>orm.xml</code> file. Return the new
	 * (virtual) attribute.
	 * Return <code>null</code> if the specified attribute does not correspond
	 * to an attribute in the Java persistent type.
	 * <p>
	 * Throw an {@link IllegalArgumentException} if the attribute is already
	 * virtual.
	 *
	 * @see OrmPersistentAttribute#convertToVirtual()
	 */
	OrmReadOnlyPersistentAttribute convertAttributeToVirtual(OrmPersistentAttribute specifiedAttribute);

	/**
	 * Add the specified persistent attribute to the <code>orm.xml</code>.
	 * The attribute will be added to the <code>orm.xml</code> and moved
	 * from the list of virtual attributes to the list
	 * of specified attributes. It will keep the same mapping it had, either
	 * specified in a Java annotation or the default.
	 * <p>
	 * Throw an {@link IllegalArgumentException} if the attribute is already
	 * specified.
	 *
	 * @see OrmPersistentAttribute#convertToSpecified()
	 */
	OrmPersistentAttribute convertAttributeToSpecified(OrmReadOnlyPersistentAttribute virtualAttribute);

	/**
	 * Add the specified persistent attribute to the <code>orm.xml</code> with
	 * the specified mapping. The attribute will be added to the
	 * <code>orm.xml</code> and moved from the list of virtual attributes to
	 * the list of specified attributes.
	 * <p>
	 * Throw an {@link IllegalArgumentException} if the attribute is already
	 * specified.
	 *
	 * @see OrmPersistentAttribute#convertToSpecified(String)
	 */
	OrmPersistentAttribute convertAttributeToSpecified(OrmReadOnlyPersistentAttribute virtualAttribute, String mappingKey);


	// ********** mapping morphing **********

	/**
	 * This is called whenever the specified persistent attribute's mapping is
	 * changed as specified.
	 */
	void changeMapping(OrmPersistentAttribute ormPersistentAttribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping);


	// ********** refactoring **********

	/**
	 * If this {@link OrmPersistentType#isFor(String)} the given IType, create a text 
	 * DeleteEdit for deleting the type mapping element and any text that precedes it.
	 * Otherwise return an EmptyIterable.
	 * Though this will contain 1 or 0 DeleteEdits, using an Iterable
	 * for ease of use with other createDeleteEdit API.
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


	// ********** misc **********

	boolean contains(int textOffset);

	/**
	 * This is called by the persistent type's mapping when its class
	 * (name) changes.
	 */
	void mappingClassChanged(String oldClass, String newClass);

	/**
	 * Return the Java persistent type that is referred to by the
	 * <code>orm.xml</code> persistent type.
	 * Return <code>null</code> if it is missing.
	 */
	JavaPersistentType getJavaPersistentType();
		String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentType"; //$NON-NLS-1$

	/**
	 * Return the persistent type's default package, as set in its entity
	 * mappings.
	 */
	String getDefaultPackage();
}
