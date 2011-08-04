/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.EnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.LobAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaBasicMapping2_0Tests extends Generic2_0ContextModelTestCase
{

	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
			}
		});
	}
		
	public GenericJavaBasicMapping2_0Tests(String name) {
		super(name);
	}
		
	public void testBasicMorphToDefaultBasic() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setConverter(EnumeratedConverter.class);
		((EnumeratedConverter) basicMapping.getConverter()).setSpecifiedEnumType(EnumType.STRING);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(EnumType.STRING, ((EnumeratedConverter) ((BasicMapping) persistentAttribute.getMapping()).getConverter()).getEnumType());
		
		assertNull(((BasicMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
		assertNull(((BasicMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToId() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) basicMapping.getConverter()).setTemporalType(TemporalType.TIME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IdMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((IdMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToVersion() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) basicMapping.getConverter()).setTemporalType(TemporalType.TIME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((VersionMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((VersionMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToEmbedded() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToEmbeddedId() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}

	public void testBasicMorphToTransient() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToOneToOne() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
//TODO	assertEquals(FetchType.EAGER, ((OneToOneMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertEquals(Boolean.FALSE, ((OneToOneMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNotNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}

	public void testBasicMorphToOneToMany() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
//TODO	assertEquals(FetchType.EAGER, ((OneToManyMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	public void testBasicMorphToManyToOne() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
//TODO	assertEquals(FetchType.EAGER, ((ManyToOneMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertEquals(Boolean.FALSE, ((ManyToOneMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToManyToMany() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
//		assertEquals(FetchType.EAGER, ((ManyToManyMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToElementCollection() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(LobAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ElementCollectionMapping2_0);
		
//		assertEquals(FetchType.EAGER, ((ElementCollectionMapping2_0) persistentAttribute.getMapping()).getSpecifiedFetch());
		assertNotNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}

}
