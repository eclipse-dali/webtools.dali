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

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

/**
 * Read-only context <code>orm.xml</code> persistent <em>attribute</em>
 * (field or property).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmReadOnlyPersistentAttribute
	extends ReadOnlyPersistentAttribute, XmlContextNode
{
	/**
	 * A <em>specified</em> <code>orm.xml</code> attribute will return an
	 * <code>orm.xml</code> mapping, while a <em>virtual</em> attribute will
	 * return a Java mapping. If the attribute is <em>virtual</em>, the returned
	 * (Java) mapping is also <em>virtual</em>; i.e. it is read-only and is not to
	 * be modified (nor are any of its parts). The modifiable Java
	 * mapping can be retrieved via the modifiable Java attribute:
	 * {@link #resolveJavaPersistentAttribute()}.
	 */
	AttributeMapping getMapping();

	OrmPersistentType getOwningPersistentType();

	OrmTypeMapping getOwningTypeMapping();

	/**
	 * If the <code>orm.xml</code> attribute is <em>virtual</em>, the returned Java
	 * attribute is also <em>virtual</em>; i.e. it is read-only and is not to
	 * be modified (nor are any of its parts [e.g. column]). The modifiable Java
	 * attribute can be retrieved via {@link #resolveJavaPersistentAttribute()}.
	 */
	JavaPersistentAttribute getJavaPersistentAttribute();
		String JAVA_PERSISTENT_ATTRIBUTE_PROPERTY = "javaPersistentAttribute"; //$NON-NLS-1$

	/**
	 * Return the <code>orm.xml</code> attribute's Java attribute; which, in the
	 * case of <em>virtual</em> attributes, is not the same Java attribute as
	 * returned by {@link #getJavaPersistentAttribute()}.
	 * <p>
	 * This is probably useful only to tests; since nothing else will want to
	 * modify the returned Java attribute.
	 */
	JavaPersistentAttribute resolveJavaPersistentAttribute();

	JavaResourcePersistentAttribute getJavaResourcePersistentAttribute();

	boolean contains(int textOffset);


	// ********** virtual -> specified **********

	/**
	 * Convert the (currently virtual) attribute to a specified
	 * attribute. The attribute will be added to the list of specified
	 * attributes and added to the <code>orm.xml</code> file. The mapping will
	 * remain the same. Return the new attribute.
	 * @see #isVirtual()
	 * @throw IllegalStateException if the attribute is already specified
	 */
	OrmPersistentAttribute convertToSpecified();

	/**
	 * Convert the (currently virtual) persistent attribute to a specified
	 * attribute. The attribute will be added to the list of specified
	 * attributes and added to the <code>orm.xml</code> file. The mapping will
	 * be changed according to the specified mapping key.
	 * Return the new attribute.
	 * @see #isVirtual()
	 * @throw IllegalStateException if the attribute is already specified
	 */
	OrmPersistentAttribute convertToSpecified(String mappingKey);
}
