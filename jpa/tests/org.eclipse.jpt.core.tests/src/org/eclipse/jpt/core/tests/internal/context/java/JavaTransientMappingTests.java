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
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.resource.java.Transient;
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
	
	public void testMorphToBasic() throws Exception {
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
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
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
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testMorphToVersion() throws Exception {
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
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbedded() throws Exception {
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
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testMorphToId() throws Exception {
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
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testTransientMapping() throws Exception {
		createTestEntityWithTransientMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		ITransientMapping transientMapping = (ITransientMapping) persistentAttribute.getSpecifiedMapping();

		assertNotNull(transientMapping);
	}

}
