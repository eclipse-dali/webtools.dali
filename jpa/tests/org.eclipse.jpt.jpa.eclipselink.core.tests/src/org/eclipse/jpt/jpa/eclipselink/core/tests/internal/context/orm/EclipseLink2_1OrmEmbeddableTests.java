/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_1OrmEmbeddableTests
	extends EclipseLink2_1ContextModelTestCase
{
	public EclipseLink2_1OrmEmbeddableTests(String name) {
		super(name);
	}

	public void testUpdateSpecifiedParentClass() throws Exception {
		EclipseLinkOrmPersistentType persistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Employee");
		EclipseLinkOrmTypeMapping ormTypeMapping = persistentType.getMapping();
		XmlTypeMapping xmlTypeMapping = (XmlTypeMapping) ormTypeMapping.getXmlTypeMapping();
		assertNull(ormTypeMapping.getSpecifiedParentClass());
		assertNull(xmlTypeMapping.getParentClass());

		//set parentClass in the resource model, verify context model updated
		xmlTypeMapping.setParentClass("model.Parent");
		assertEquals("model.Parent", ormTypeMapping.getSpecifiedParentClass());
		assertEquals("model.Parent", xmlTypeMapping.getParentClass());
		
		//set parentClass to null in the resource model
		xmlTypeMapping.setParentClass(null);
		assertNull(ormTypeMapping.getSpecifiedParentClass());
		assertNull(xmlTypeMapping.getParentClass());
	}

	public void testModifySpecifiedParentClass() throws Exception {		
		EclipseLinkOrmPersistentType persistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Employee");
		EclipseLinkOrmTypeMapping ormTypeMapping = persistentType.getMapping();
		XmlTypeMapping xmlTypeMapping = (XmlTypeMapping) ormTypeMapping.getXmlTypeMapping();
		assertNull(ormTypeMapping.getSpecifiedParentClass());
		assertNull(xmlTypeMapping.getParentClass());
		
		//set parentClass in the context model, verify resource model modified
		ormTypeMapping.setSpecifiedParentClass("model.Parent");
		assertEquals("model.Parent", ormTypeMapping.getSpecifiedParentClass());
		assertEquals("model.Parent", xmlTypeMapping.getParentClass());
		
		//set parentClass to null in the context model
		ormTypeMapping.setSpecifiedParentClass(null);
		assertNull(ormTypeMapping.getSpecifiedParentClass());
		assertNull(xmlTypeMapping.getParentClass());
	}
}