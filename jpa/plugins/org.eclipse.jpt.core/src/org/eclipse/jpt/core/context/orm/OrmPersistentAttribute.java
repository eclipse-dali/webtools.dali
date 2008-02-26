/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlVersion;

public interface OrmPersistentAttribute extends PersistentAttribute
{
	
	/**
	 * Overriden to return {@link AbstractOrmAttributeMapping}s
	 */
	AbstractOrmAttributeMapping<?> getMapping();
	
	/**
	 * Overriden to return {@link AbstractOrmAttributeMapping}s
	 */
	AbstractOrmAttributeMapping<?> getSpecifiedMapping();
	
	/**
	 * Overriden to return {@link OrmTypeMapping}s
	 */
	OrmTypeMapping typeMapping();
	
	/**
	 * Overriden to return {@link OrmPersistentType}s
	 */
	OrmPersistentType persistentType();
	
	boolean contains(int textOffset);

	/**
	 * Set the persistent attribute to virtual. If true, the attribute will
	 * be removed from the list of specified persistent attributes on {@link OrmPersistentType} 
	 * and removed from the orm.xml file.
	 * If false, the attribute will be added to the list of specified persistent attributes.
	 */
	void setVirtual(boolean virtual);

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
}