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
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.resource.java.Version;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaVersionMappingTests extends ContextModelTestCase
{
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createVersionAnnotation() throws Exception{
		this.createAnnotationAndMembers("Version", "");		
	}
	
	private void createTemporalAnnotation() throws Exception{
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");		
	}

	private IType createTestEntityWithVersionMapping() throws Exception {
		createEntityAnnotation();
		createVersionAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.VERSION);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
			}
		});
	}

	private IType createTestEntityWithTemporal() throws Exception {
		createEntityAnnotation();
		createTemporalAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.VERSION, JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
				sb.append("@Temporal(TemporalType.TIMESTAMP)").append(CR);
			}
		});
	}
		
	public JavaVersionMappingTests(String name) {
		super(name);
	}
		
	public void testMorphToBasic() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IBasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((IBasicMapping) persistentAttribute.getMapping()).getTemporal());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IBasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((IBasicMapping) persistentAttribute.getMapping()).getTemporal());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testMorphToId() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IIdMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((IIdMapping) persistentAttribute.getMapping()).getTemporal());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbedded() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
	public void testMorphToTransient() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());

		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ITransientMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}

	
	public void testGetTemporal() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(versionMapping.getTemporal());
	}
	
	public void testGetTemporal2() throws Exception {
		createTestEntityWithTemporal();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(TemporalType.TIMESTAMP, versionMapping.getTemporal());
	}

	public void testSetTemporal() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(versionMapping.getTemporal());
		
		versionMapping.setTemporal(TemporalType.TIME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Temporal temporal = (Temporal) attributeResource.annotation(Temporal.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.TemporalType.TIME, temporal.getValue());
		
		versionMapping.setTemporal(null);
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
	}
	
	public void testGetTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(versionMapping.getTemporal());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Temporal temporal = (Temporal) attributeResource.addAnnotation(Temporal.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.core.internal.resource.java.TemporalType.DATE);
		
		assertEquals(TemporalType.DATE, versionMapping.getTemporal());
		
		attributeResource.removeAnnotation(Temporal.ANNOTATION_NAME);
		
		assertNull(versionMapping.getTemporal());
		assertFalse(versionMapping.isDefault());
		assertSame(versionMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetColumn() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IVersionMapping versionMapping = (IVersionMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(versionMapping.getColumn().getSpecifiedName());
		assertEquals("id", versionMapping.getColumn().getName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Column column = (Column) attributeResource.addAnnotation(JPA.COLUMN);
		column.setName("foo");
		
		assertEquals("foo", versionMapping.getColumn().getSpecifiedName());
		assertEquals("foo", versionMapping.getColumn().getName());
		assertEquals("id", versionMapping.getColumn().getDefaultName());
	}
	
}
