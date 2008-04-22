/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlVersion;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmPersistentAttribute extends PersistentAttribute, OrmJpaContextNode
{
	
	/**
	 * Overriden to return {@link OrmAttributeMapping}s
	 */
	OrmAttributeMapping getMapping();
	
	/**
	 * Overriden to return {@link OrmAttributeMapping}s
	 */
	OrmAttributeMapping getSpecifiedMapping();
	
	/**
	 * Overriden to return {@link OrmTypeMapping}s
	 */
	OrmTypeMapping getTypeMapping();
	
	/**
	 * Overriden to return {@link OrmPersistentType}s
	 */
	OrmPersistentType getPersistentType();
	
	boolean contains(int textOffset);

	/**
	 * Make the persistent attribute virtual. The attribute will be removed
	 * from the list of specified persistent attributes on the {@link OrmPersistentType} 
	 * and removed from the orm.xml file.
	 * 
	 * If the persistent attribute is already virtual, an IllegalStateException is thrown
	 * @see PersistentAttribute#isVirtual()
	 */
	void makeVirtual();
	
	/**
	 * Take a virtual persistent attribute and specify it.
	 * The attribute will be added to the list of specified persistent attributes
	 * and added to the orm.xml file. The mappingKey will remain the same.
	 * 
	 * If the persistent attribute is already specified, an IllegalStateException is thrown
	 * @see PersistentAttribute#isVirtual()
	 */
	void makeSpecified();
	
	/**
	 * Take a virtual persistent attribute and specify it.
	 * The attribute will be added to the list of specified persistent attributes
	 * and added to the orm.xml file. The mappingKey will determine the type of mapping added
	 * instead of the mappingKey already on the persistent attribute
	 * 
	 * If the persistent attribute is already specified, an IllegalStateException is thrown
	 * @see PersistentAttribute#isVirtual()
	 */
	void makeSpecified(String mappingKey);
	
	void nameChanged(String oldName, String newName);

	
	//******************* initialization/updating *******************

	void initialize(XmlBasic basic);
	
	void initialize(XmlEmbedded embedded);
	
	void initialize(XmlVersion version);
	
	void initialize(XmlManyToOne manyToOne);
	
	void initialize(XmlOneToMany oneToMany);
	
	void initialize(XmlOneToOne oneToOne);
	
	void initialize(XmlManyToMany manyToMany);
	
	void initialize(XmlId id);
	
	void initialize(XmlEmbeddedId embeddedId);
	
	void initialize(XmlTransient transientResource);
	
	void initialize(XmlNullAttributeMapping xmlNullAttributeMapping);
	
	void update(XmlId id);
	
	void update(XmlEmbeddedId embeddedId);

	void update(XmlBasic basic);
	
	void update(XmlVersion version);
	
	void update(XmlManyToOne manyToOne);
	
	void update(XmlOneToMany oneToMany);
	
	void update(XmlOneToOne oneToOne);
	
	void update(XmlManyToMany manyToMany);

	void update(XmlEmbedded embedded);
	
	void update(XmlTransient transientResource);
	
	void update(XmlNullAttributeMapping xmlNullAttributeMapping);

}