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
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaBasicMappingTests extends ContextModelTestCase
{
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createBasicAnnotation() throws Exception{
		this.createAnnotationAndMembers("Basic", "FetchType fetch() default EAGER; boolean optional() default true;");		
	}
	

	private IType createTestEntity() throws Exception {
		createEntityAnnotation();

		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	private IType createTestEntityWithBasicMapping() throws Exception {
		createEntityAnnotation();
		createBasicAnnotation();
	
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
	private IType createTestEntityWithBasicMappingFetchOptionalSpecified() throws Exception {
		createEntityAnnotation();
		createBasicAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.FETCH_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic(fetch=FetchType.EAGER, optional=false)").append(CR);
			}
		});
	}


		
	public JavaBasicMappingTests(String name) {
		super(name);
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResource prm = persistenceResourceModel();
		return prm.getPersistence().getPersistenceUnits().get(0);
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

	
	public void testDefaultBasicGetDefaultFetch() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(FetchType.EAGER, basicMapping.getDefaultFetch());
	}
	
	public void testSpecifiedBasicGetDefaultFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(FetchType.EAGER, basicMapping.getDefaultFetch());
	}
	
	public void testGetFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(FetchType.EAGER, basicMapping.getFetch());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);		
		assertEquals(FetchType.LAZY, basicMapping.getFetch());
	}
	
	public void testGetSpecifiedFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedFetch());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		basic.setFetch(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY);
		
		assertEquals(FetchType.LAZY, basicMapping.getSpecifiedFetch());
	}
	
	public void testGetSpecifiedFetch2() throws Exception {
		createTestEntityWithBasicMappingFetchOptionalSpecified();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(FetchType.EAGER, basicMapping.getSpecifiedFetch());
	}

	public void testSetSpecifiedFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(basicMapping.getSpecifiedFetch());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY, basic.getFetch());
		
		basicMapping.setSpecifiedFetch(null);
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
	}
	
	public void testSetSpecifiedFetch2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertNull(basicMapping.getSpecifiedFetch());
		assertTrue(basicMapping.isDefault());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY, basic.getFetch());
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(FetchType.LAZY, basicMapping.getSpecifiedFetch());
		assertFalse(basicMapping.isDefault());

		basicMapping.setSpecifiedFetch(null);
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
	}
	
	public void testSetBasicRemovedFromResourceModel() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		
		assertFalse(basicMapping.isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.setMappingAnnotation(null);
		
		assertNotSame(basicMapping, persistentAttribute.getMapping());
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertTrue(basicMapping.isDefault());
	}
	
	public void testDefaultBasicGetDefaultOptional() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(Boolean.TRUE, basicMapping.getDefaultOptional());
	}
	
	public void testSpecifiedBasicGetDefaultOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(Boolean.TRUE, basicMapping.getDefaultOptional());
	}
	
	public void testGetOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Boolean.TRUE, basicMapping.getOptional());
		
		basicMapping.setSpecifiedOptional(basicMapping.getOptional());
		assertEquals(Boolean.TRUE, basicMapping.getOptional());
	}
	
	public void testGetSpecifiedOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedOptional());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		basic.setOptional(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
	}
	
	public void testGetSpecifiedOptional2() throws Exception {
		createTestEntityWithBasicMappingFetchOptionalSpecified();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
	}

	public void testSetSpecifiedOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(basicMapping.getSpecifiedOptional());
		
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		
		assertEquals(Boolean.FALSE, basic.getOptional());
		
		basicMapping.setSpecifiedOptional(null);
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
	}
	
	public void testSetSpecifiedOptional2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertNull(basicMapping.getSpecifiedOptional());
		assertTrue(basicMapping.isDefault());
		
		basicMapping.setSpecifiedOptional(Boolean.TRUE);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		
		assertEquals(Boolean.TRUE, basic.getOptional());
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(Boolean.TRUE, basicMapping.getSpecifiedOptional());
		assertFalse(basicMapping.isDefault());

		basicMapping.setSpecifiedOptional(null);
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
	}

	
	public void testGetSpecifiedOptionalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedOptional());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		basic.setOptional(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
		
		basic.setOptional(null);
		assertNull(basicMapping.getSpecifiedOptional());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
		
		basic.setOptional(Boolean.FALSE);
		attributeResource.setMappingAnnotation(null);
		
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(Boolean.TRUE, ((IBasicMapping) persistentAttribute.getMapping()).getOptional());
	}

}
