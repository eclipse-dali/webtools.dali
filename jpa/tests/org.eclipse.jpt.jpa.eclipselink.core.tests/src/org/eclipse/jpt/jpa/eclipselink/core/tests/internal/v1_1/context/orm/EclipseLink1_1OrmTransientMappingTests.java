/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v1_1.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase.DefaultAnnotationWriter;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

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
		
		OrmReadOnlyPersistentAttribute persistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertTrue(persistentAttribute.isVirtual());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		
		ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "id");

		persistentAttribute = ormPersistentType.getAttributeNamed("id");
		assertFalse(persistentAttribute.isVirtual());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMapping().getKey());
	}

}
