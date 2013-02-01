/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.DeleteTypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;

/**
 * <code>orm.xml</code> persistent type
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 2.0
 */
public interface OrmPersistentType
	extends PersistentType, PersistentType.Owner, DeleteTypeRefactoringParticipant, TypeRefactoringParticipant
{
	// ********** covariant overrides **********

	EntityMappings getParent();

	OrmTypeMapping getMapping();

	/**
	 * Return a combination of the persistent type's <em>specified</em> and
	 * <em>default</em> attributes. The <em>specified</em> attributes are those
	 * explicitly listed in the <code>orm.xml</code> file; while the
	 * <em>default</em> attributes are those derived from the corresponding
	 * Java persistent type.
	 */
	ListIterable<OrmReadOnlyPersistentAttribute> getAttributes();

	OrmReadOnlyPersistentAttribute getAttributeNamed(String attributeName);


	// ********** specified attributes **********

	String SPECIFIED_ATTRIBUTES_LIST = "specifiedAttributes"; //$NON-NLS-1$

	/**
	 * Return the persistent type's specified attributes.
	 */
	ListIterable<OrmPersistentAttribute> getSpecifiedAttributes();

	/**
	 * Return the number of the persistent type's specified attributes.
	 */
	int getSpecifiedAttributesSize();


	// ********** default attributes **********

	String DEFAULT_ATTRIBUTES_LIST = "defaultAttributes"; //$NON-NLS-1$

	/**
	 * Return default <code>orm.xml</code> persistent attributes. These
	 * are attributes that exist in the corresponding Java class, but are not
	 * specified in the <code>orm.xml</code>.
	 */
	ListIterable<OrmReadOnlyPersistentAttribute> getDefaultAttributes();

	/**
	 * Return the number of default <code>orm.xml</code> persistent attributes.
	 * @see #getDefaultAttributes()
	 */
	int getDefaultAttributesSize();

	/**
	 * Remove attribute from the type's list of specified attributes
	 * and remove it from the <code>orm.xml</code> file. 
	 * Return the new (virtual) attribute, if it exists.
	 * Return <code>null</code> if the specified attribute does not correspond
	 * to an attribute in the Java persistent type.
	 * <p>
	 * Throw an {@link IllegalArgumentException} if the attribute is virtual
	 *
	 * @see OrmPersistentAttribute#removeFromXml()
	 * @see PersistentAttribute#isVirtual()
	 */
	OrmReadOnlyPersistentAttribute removeAttributeFromXml(OrmPersistentAttribute specifiedAttribute);

	/**
	 * Add the specified persistent attribute to the <code>orm.xml</code>.
	 * The attribute will be added to the <code>orm.xml</code> and moved
	 * from the list of default attributes to the list
	 * of specified attributes. It will keep the same mapping it had, either
	 * specified in a Java annotation or the default.
	 * <p>
	 * Throw an {@link IllegalArgumentException} if the attribute is already
	 * specified.
	 *
	 * @see OrmPersistentAttribute#addToXml()
	 */
	OrmPersistentAttribute addAttributeToXml(OrmReadOnlyPersistentAttribute virtualAttribute);

	/**
	 * Add the specified persistent attribute to the <code>orm.xml</code> with
	 * the specified mapping. The attribute will be added to the
	 * <code>orm.xml</code> and moved from the list of default attributes to
	 * the list of specified attributes.
	 * <p>
	 * Throw an {@link IllegalArgumentException} if the attribute is already
	 * specified.
	 *
	 * @see OrmPersistentAttribute#addToXml(String)
	 */
	OrmPersistentAttribute addAttributeToXml(OrmReadOnlyPersistentAttribute virtualAttribute, String mappingKey);


	// ********** mapping morphing **********

	/**
	 * This is called whenever the specified persistent attribute's mapping is
	 * changed as specified.
	 */
	void changeMapping(OrmPersistentAttribute ormPersistentAttribute, OrmAttributeMapping oldMapping, OrmAttributeMapping newMapping);


	// ********** misc **********

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
