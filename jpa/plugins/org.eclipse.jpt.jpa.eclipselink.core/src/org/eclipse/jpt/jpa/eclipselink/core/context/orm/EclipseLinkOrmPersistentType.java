/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.orm;

import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessMethodsHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping;

/**
 * <code>eclipselink-orm.xml</code> persistent type:<ul>
 * <li>mapping
 * <li>access
 * <li>access-methods
 * <li>attributes
 * <li>super persistent type
 * <li>Java persistent type
 * </ul>
 */
public interface EclipseLinkOrmPersistentType
	extends OrmPersistentType, PersistentType2_0, EclipseLinkAccessMethodsHolder
{

	//*************** dynamic *****************

	/**
	 * The dynamic state is only based on the JavaResourceType being null.
	 * The access type should be VIRTUAL, but we will use validation to check this
	 */
	boolean isDynamic();
		String DYNAMIC_PROPERTY = "dynamic"; //$NON-NLS-1$
	Predicate<EclipseLinkOrmPersistentType> IS_DYNAMIC = new IsDynamic();
	class IsDynamic
		extends FilterAdapter<EclipseLinkOrmPersistentType>
	{
		@Override
		public boolean evaluate(EclipseLinkOrmPersistentType pType) {
			return pType.isDynamic();
		}
	}

	/**
	 * Add a virtual attribute with the given attribute name and mapping key.
	 * 'attributeType' and 'targetType' could be null depending on the mapping type.
	 * 
	 * @see XmlAttributeMapping#setVirtualAttributeTypes(String, String)
	 * @see OrmAttributeMappingDefinition#isSingleRelationshipMapping()
	 * @see OrmAttributeMappingDefinition#isCollectionMapping()
	*/
	OrmPersistentAttribute addVirtualAttribute(String attributeName, String mappingKey, String attributeType, String targetType);

	//*************** covariant overrides *****************

	EclipseLinkOrmTypeMapping getMapping();
}
