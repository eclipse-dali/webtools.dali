/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;

/**
 * <code>orm.xml</code> specified persistent <em>attribute</em>
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
public interface OrmSpecifiedPersistentAttribute
	extends SpecifiedPersistentAttribute, OrmPersistentAttribute, TypeRefactoringParticipant
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
	OrmPersistentAttribute removeFromXml();

	OrmAttributeMapping setMappingKey(String key);

	/**
	 * Called by the attribute's mapping when it's name changes,
	 * effectively changing the attribute's name.
	 */
	void nameChanged(String oldName, String newName);
}
