/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
	OrmSpecifiedPersistentAttribute getParent();

	OrmSpecifiedPersistentAttribute getPersistentAttribute();

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

	/**
	 * Called (on the old mapping) when converting one <code>orm.xml</code>
	 * mapping to another; so we can use <em>double-dispatching</em>. The old
	 * mapping calls the appropriate <code>initializeFrom___(Orm___Mapping)</code>
	 * method on the new mapping, passing itself to the new mapping.
	 * 
	 * @see OrmPersistentType#changeMapping(OrmSpecifiedPersistentAttribute, OrmAttributeMapping, OrmAttributeMapping)
	 * @see OrmSpecifiedPersistentAttribute#setMappingKey(String)
	 */
	void initializeOn(OrmAttributeMapping newMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmAttributeMapping(OrmAttributeMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmBasicMapping(OrmBasicMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmIdMapping(OrmIdMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmTransientMapping(OrmTransientMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmEmbeddedMapping(OrmEmbeddedMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmEmbeddedIdMapping(OrmEmbeddedIdMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmVersionMapping(OrmVersionMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmOneToManyMapping(OrmOneToManyMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmManyToOneMapping(OrmManyToOneMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmOneToOneMapping(OrmOneToOneMapping oldMapping);

	/**
	 * @see #initializeOn(OrmAttributeMapping)
	 */
	void initializeFromOrmManyToManyMapping(OrmManyToManyMapping oldMapping);
}
