/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentType2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class GenericJavaOneToOneMapping2_0Tests
	extends Generic2_0ContextModelTestCase
{
	public GenericJavaOneToOneMapping2_0Tests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestEntityWithDerivedId() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne @Id").append(CR);				
				sb.append("    private " + TYPE_NAME + " oneToOne;").append(CR);
				sb.append(CR);				
			}
		});
	}
	
	public void testUpdateDerivedId() throws Exception {
		createTestEntityWithDerivedId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType2_0 contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		JavaOneToOneMapping2_0 contextMapping = (JavaOneToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedId().getValue());
		
		resourceAttribute.removeAnnotation(JPA.ID);
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(contextMapping.getDerivedId().getValue());
		
		resourceAttribute.addAnnotation(JPA.ID);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedId().getValue());
	}
	
	public void testSetDerivedId() throws Exception {
		createTestEntityWithDerivedId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType2_0 contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		JavaOneToOneMapping2_0 contextMapping = (JavaOneToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedId().getValue());
		
		contextMapping.getDerivedId().setValue(false);
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(contextMapping.getDerivedId().getValue());
		
		contextMapping.getDerivedId().setValue(true);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedId().getValue());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithDerivedId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType2_0 contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(((JavaOneToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(((JavaManyToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextAttribute.getMapping() instanceof JavaIdMapping);
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(((JavaOneToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextAttribute.getMapping() instanceof JavaBasicMapping);
	}
}
