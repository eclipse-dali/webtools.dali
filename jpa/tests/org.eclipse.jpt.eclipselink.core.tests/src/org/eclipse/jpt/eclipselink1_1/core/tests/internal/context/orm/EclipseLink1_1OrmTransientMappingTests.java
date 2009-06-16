/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink1_1OrmTransientMappingTests
	extends EclipseLink1_1OrmContextModelTestCase
{
	public EclipseLink1_1OrmTransientMappingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntityWithTransientMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TRANSIENT);
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
		
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "id");

		persistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(persistentAttribute.isVirtual());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getSpecifiedMapping().getKey());
	}

}
