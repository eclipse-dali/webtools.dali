/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Embedded;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedId;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.ManyToMany;
import org.eclipse.jpt.core.internal.resource.java.ManyToOne;
import org.eclipse.jpt.core.internal.resource.java.OneToMany;
import org.eclipse.jpt.core.internal.resource.java.OneToOne;
import org.eclipse.jpt.core.internal.resource.java.Transient;
import org.eclipse.jpt.core.internal.resource.java.Version;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaTransientMappingTests extends ContextModelTestCase
{
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createTransientAnnotation() throws Exception{
		this.createAnnotationAndMembers("Transient", "");		
	}

	private IType createTestEntityWithTransientMapping() throws Exception {
		createEntityAnnotation();
		createTransientAnnotation();
	
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
		
	public JavaTransientMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IBasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IBasicMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IVersionMapping);
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IIdMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Id.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedIdMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToOneMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToManyMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToOneMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToOne.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToManyMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
	}
	
	public void testTransientMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getSpecifiedMapping();

		assertNotNull(transientMapping);
	}

}
