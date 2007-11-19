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
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaIdMappingTests extends ContextModelTestCase
{
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createIdAnnotation() throws Exception{
		this.createAnnotationAndMembers("Id", "");		
	}
	
	private void createGeneratedValueAnnotation() throws Exception{
		this.createAnnotationAndMembers("GeneratedValue", "");		
	}
	
	private void createTemporalAnnotation() throws Exception{
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");		
	}

	private IType createTestEntityWithIdMapping() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
			}
		});
	}

	private IType createTestEntityWithTemporal() throws Exception {
		createEntityAnnotation();
		createTemporalAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@Temporal(TemporalType.TIMESTAMP)").append(CR);
			}
		});
	}
	
	private IType createTestEntityWithIdMappingGeneratedValue() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
		createGeneratedValueAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.GENERATED_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@GeneratedValue").append(CR);
			}
		});
	}
	
	public JavaIdMappingTests(String name) {
		super(name);
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResource pr = persistenceResource();
		return pr.getPersistence().getPersistenceUnits().get(0);
	}
	
	protected IPersistenceUnit persistenceUnit() {
		return jpaContent().getPersistenceXml().getPersistence().persistenceUnits().next();
	}
	
	protected IClassRef classRef() {
		return persistenceUnit().classRefs().next();
	}
	
	protected IJavaPersistentType javaPersistentType() {
		return classRef().getJavaPersistentType();
	}
	
	protected IEntity javaEntity() {
		return (IEntity) javaPersistentType().getMapping();
	}
	
	protected void addXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass(className);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
	}
	
	
	public void testGetTemporal() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(idMapping.getTemporal());
	}
	
	public void testGetTemporal2() throws Exception {
		createTestEntityWithTemporal();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(TemporalType.TIMESTAMP, idMapping.getTemporal());
	}

	public void testSetTemporal() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(idMapping.getTemporal());
		
		idMapping.setTemporal(TemporalType.TIME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Temporal temporal = (Temporal) attributeResource.annotation(Temporal.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.TemporalType.TIME, temporal.getValue());
		
		idMapping.setTemporal(null);
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
	}
	
	public void testGetTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(idMapping.getTemporal());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Temporal temporal = (Temporal) attributeResource.addAnnotation(Temporal.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.core.internal.resource.java.TemporalType.DATE);
		
		assertEquals(TemporalType.DATE, idMapping.getTemporal());
		
		attributeResource.removeAnnotation(Temporal.ANNOTATION_NAME);
		
		assertNull(idMapping.getTemporal());
		assertFalse(idMapping.isDefault());
		assertSame(idMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetColumn() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getColumn().getSpecifiedName());
		assertEquals("id", idMapping.getColumn().getName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Column column = (Column) attributeResource.addAnnotation(JPA.COLUMN);
		column.setName("foo");
		
		assertEquals("foo", idMapping.getColumn().getSpecifiedName());
		assertEquals("foo", idMapping.getColumn().getName());
		assertEquals("id", idMapping.getColumn().getDefaultName());
	}

	public void testGetSequenceGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getSequenceGenerator());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(JPA.SEQUENCE_GENERATOR);
		
		assertNotNull(idMapping.getSequenceGenerator());
	}
	
	public void testAddSequenceGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getSequenceGenerator());
		
		idMapping.addSequenceGenerator();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		assertNotNull(attributeResource.annotation(JPA.SEQUENCE_GENERATOR));
		assertNotNull(idMapping.getSequenceGenerator());
		
		//try adding another sequence generator, should get an IllegalStateException
		try {
				idMapping.addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(JPA.SEQUENCE_GENERATOR);
		
		
		idMapping.removeSequenceGenerator();
		
		assertNull(idMapping.getSequenceGenerator());
		assertNull(attributeResource.annotation(JPA.SEQUENCE_GENERATOR));

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			idMapping.removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetTableGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getTableGenerator());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(JPA.TABLE_GENERATOR);
		
		assertNotNull(idMapping.getTableGenerator());		
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getTableGenerator());
		
		idMapping.addTableGenerator();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		assertNotNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
		assertNotNull(idMapping.getTableGenerator());
		
		//try adding another table generator, should get an IllegalStateException
		try {
			idMapping.addTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(JPA.TABLE_GENERATOR);
		
		
		idMapping.removeTableGenerator();
		
		assertNull(idMapping.getTableGenerator());
		assertNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
		
		//try removing the table generator again, should get an IllegalStateException
		try {
			idMapping.removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetGeneratedValue() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getGeneratedValue());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(JPA.GENERATED_VALUE);
		
		assertNotNull(idMapping.getGeneratedValue());		
	}
	
	public void testGetGeneratedValue2() throws Exception {
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityWithIdMappingGeneratedValue();
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNotNull(idMapping.getGeneratedValue());
	}
	
	public void testAddGeneratedValue() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getGeneratedValue());
		
		idMapping.addGeneratedValue();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		assertNotNull(attributeResource.annotation(JPA.GENERATED_VALUE));
		assertNotNull(idMapping.getGeneratedValue());
		
		//try adding another generated value, should get an IllegalStateException
		try {
			idMapping.addGeneratedValue();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveGeneratedValue() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IIdMapping idMapping = (IIdMapping) persistentAttribute.getSpecifiedMapping();

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(JPA.GENERATED_VALUE);
		
		
		idMapping.removeGeneratedValue();
		
		assertNull(idMapping.getGeneratedValue());
		assertNull(attributeResource.annotation(JPA.GENERATED_VALUE));
		
		//try removing the generatedValue again, should get an IllegalStateException
		try {
			idMapping.removeGeneratedValue();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
}
