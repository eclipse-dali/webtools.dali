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
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.EclipseLink2_0ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0JavaManyToOneMappingTests
	extends EclipseLink2_0ContextModelTestCase
{
	public EclipseLink2_0JavaManyToOneMappingTests(String name) {
		super(name);
	}
	
	
	private void createTestEntity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private Address address;");
				sb.append(CR);
				sb.append(CR);
				sb.append("    @Id");			
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithDerivedId() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne @Id").append(CR);				
				sb.append("    private " + TYPE_NAME + " manyToOne;").append(CR);
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
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		JavaManyToOneMapping2_0 contextMapping = (JavaManyToOneMapping2_0) contextAttribute.getMapping();
		
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
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		JavaManyToOneMapping2_0 contextMapping = (JavaManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedId().getValue());
		
		contextMapping.getDerivedId().setValue(false);
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(contextMapping.getDerivedId().getValue());
		
		contextMapping.getDerivedId().setValue(true);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedId().getValue());
	}
	
	public void testUpdateMapsId() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("address");
		JavaManyToOneMapping2_0 contextMapping = (JavaManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getMapsId().getValue());
		
		MapsId2_0Annotation annotation = 
				(MapsId2_0Annotation) resourceAttribute.addAnnotation(JPA2_0.MAPS_ID);
		annotation.setValue("foo");
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", contextMapping.getMapsId().getValue());
		
		annotation.setValue("bar");
		assertEquals("bar", annotation.getValue());
		assertEquals("bar", contextMapping.getMapsId().getValue());
		
		resourceAttribute.removeAnnotation(JPA2_0.MAPS_ID);
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getMapsId().getValue());
	}
	
	public void testSetMapsId() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("address");
		JavaManyToOneMapping2_0 contextMapping = (JavaManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue("foo");
		MapsId2_0Annotation annotation = 
				(MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID);
		assertNotNull(annotation);
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue("bar");
		assertEquals("bar", annotation.getValue());
		assertEquals("bar", contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue(null);
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getMapsId().getValue());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithDerivedId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		
		((MapsId2_0Annotation) resourceAttribute.addAnnotation(JPA2_0.MAPS_ID)).setValue("foo");
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertTrue(((JavaManyToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertTrue(((JavaOneToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertTrue(contextAttribute.getMapping() instanceof JavaIdMapping);
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(((JavaManyToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextAttribute.getMapping() instanceof JavaBasicMapping);
	}
}
