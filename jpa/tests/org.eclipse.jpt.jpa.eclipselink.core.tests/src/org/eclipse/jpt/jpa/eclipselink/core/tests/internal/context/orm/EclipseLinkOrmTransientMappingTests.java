/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkOrmTransientMappingTests
	extends EclipseLinkContextModelTestCase
{
	public EclipseLinkOrmTransientMappingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntityWithTransientMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.TRANSIENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Transient").append(CR);
			}
		});
	}
	
	public void testCreateTransientMapping() throws Exception {
		createTestEntityWithTransientMapping();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmPersistentAttribute persistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertTrue(persistentAttribute.isVirtual());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		
		OrmSpecifiedPersistentAttribute persistentAttribute2 = persistentAttribute.addToXml(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertFalse(persistentAttribute2.isVirtual());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, persistentAttribute2.getMappingKey());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, persistentAttribute2.getMapping().getKey());
	}

}
