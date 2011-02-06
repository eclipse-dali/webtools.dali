/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;

/**
 * Read-only context persistent <em>attribute</em> (field or property).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyPersistentAttribute
	extends JpaContextNode, JpaStructureNode, ReadOnlyAccessHolder
{
	// ********** name **********

	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$


	// ********** mapping **********

	/**
	 * Return the attribute's mapping. This is never <code>null</code>
	 * (although, it may be a <em>null</em> mapping).
	 * Set the mapping via {@link PersistentAttribute#setMappingKey(String)}.
	 */
	AttributeMapping getMapping();
		String MAPPING_PROPERTY = "mapping"; //$NON-NLS-1$

	/**
	 * Return the attribute's mapping key.
	 */
	String getMappingKey();

	/**
	 * Return the key for the attribute's default mapping.
	 * This can be <code>null</code> (e.g. for <em>specified</em>
	 * <code>orm.xml</code> attributes).
	 * @see AttributeMapping#isDefault()
	 */
	String getDefaultMappingKey();
		String DEFAULT_MAPPING_KEY_PROPERTY = "defaultMappingKey"; //$NON-NLS-1$


	// ********** misc **********

	/**
	 * Return the persistent type that owns (declares) the attribute.
	 */
	PersistentType getOwningPersistentType();
	
	/**
	 * Return the attribute's owning persistent type's mapping (as opposed to
	 * the attribute's target type's mapping).
	 */
	TypeMapping getOwningTypeMapping();
	
	/**
	 * Return the resolved, qualified name of the attribute's type
	 * (e.g. <code>"java.util.Collection"</code> or <code>"byte</code>[]").
	 * Return <code>null</code> if the attribute's type can not be resolved.
	 * If the type is an array, this name will include the appropriate number
	 * of bracket pairs.
	 * This name will not include the type's generic type arguments
	 * (e.g. <code>"java.util.Collection<java.lang.String>"</code> will only return
	 * <code>"java.util.Collection"</code>).
	 */
	String getTypeName();
	
	/**
	 * If the attribute is mapped to a primary key column, return the
	 * column's name, otherwise return <code>null</code>.
	 */
	String getPrimaryKeyColumnName();
	
	/**
	 * Return whether the attribute has a textual representation
	 * in its underlying resource.
	 */
	boolean isVirtual();

	JavaPersistentAttribute getJavaPersistentAttribute();
}
