/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmAttributeMapping extends AttributeMapping, OrmJpaContextNode
{
	OrmPersistentAttribute getPersistentAttribute();

	String getName();
	void setName(String newName);
	String NAME_PROPERTY = "name"; //$NON-NLS-1$


	JavaPersistentAttribute getJavaPersistentAttribute();
	String JAVA_PERSISTENT_ATTRIBUTE_PROPERTY = "javaPersistentAttribute"; //$NON-NLS-1$
	
	/**
	 * Attributes are a sequence in the orm schema. We must keep
	 * the list of attributes in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.  
	 * 
	 * Each concrete subclass of XmlAttributeMapping must implement this
	 * method and return an int that matches it's order in the schema
	 * @return
	 */
	int getXmlSequence();

	void removeFromResourceModel(AbstractXmlTypeMapping typeMapping);
	
	XmlAttributeMapping addToResourceModel(AbstractXmlTypeMapping typeMapping);


	void initializeOn(OrmAttributeMapping newMapping);

	void initializeFromOrmAttributeMapping(OrmAttributeMapping oldMapping);

	void initializeFromOrmBasicMapping(OrmBasicMapping oldMapping);

	void initializeFromOrmIdMapping(OrmIdMapping oldMapping);

	void initializeFromOrmTransientMapping(OrmTransientMapping oldMapping);

	void initializeFromOrmEmbeddedMapping(OrmEmbeddedMapping oldMapping);

	void initializeFromOrmEmbeddedIdMapping(OrmEmbeddedIdMapping oldMapping);

	void initializeFromOrmVersionMapping(OrmVersionMapping oldMapping);

	void initializeFromOrmOneToManyMapping(OrmOneToManyMapping oldMapping);

	void initializeFromOrmManyToOneMapping(OrmManyToOneMapping oldMapping);

	void initializeFromOrmOneToOneMapping(OrmOneToOneMapping oldMapping);

	void initializeFromOrmManyToManyMapping(OrmManyToManyMapping oldMapping);

	boolean contains(int textOffset);

	TextRange getSelectionTextRange();
}
