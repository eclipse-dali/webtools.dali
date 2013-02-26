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

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;

/**
 * <code>orm.xml</code> attribute mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 2.3
 */
public interface OrmAttributeMapping
	extends AttributeMapping, TypeRefactoringParticipant
{
	OrmModifiablePersistentAttribute getParent();

	OrmModifiablePersistentAttribute getPersistentAttribute();

	XmlAttributeMapping getXmlAttributeMapping();

	OrmTypeMapping getTypeMapping();

	void setName(String name);
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Return either the (possibly unqualified) type specified here or, if unspecified, the 
	 * (qualified) default type.
	 */
	String getAttributeType();

	String getFullyQualifiedAttributeType();
		String FULLY_QUALIFIED_ATTRIBUTE_TYPE_PROPERTY = "fullyQualifiedAttributeType"; //$NON-NLS-1$

	String getSpecifiedAttributeType();
	void setSpecifiedAttributeType(String attributeType);
		String SPECIFIED_ATTRIBUTE_TYPE_PROPERTY = "specifiedAttributeType"; //$NON-NLS-1$

	String getDefaultAttributeType();
		String DEFAULT_ATTRIBUTE_TYPE_PROPERTY = "defaultAttributeType"; //$NON-NLS-1$

	/**
	 * Attributes are a sequence in the <code>orm.xml</code> schema. We must keep
	 * the list of attributes in the appropriate order so the WTP XML
	 * translators will write them to the XML document in that order and they
	 * will adhere to the schema.
	 * <p>
	 * Each implementation must implement this
	 * method and return a number that matches its order in the schema.
	 */
	int getXmlSequence();

	void addXmlAttributeMappingTo(Attributes resourceAttributes);

	void removeXmlAttributeMappingFrom(Attributes resourceAttributes);

	TextRange getSelectionTextRange();

	TextRange getNameTextRange();


	// ********** morphing mappings **********

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
}
