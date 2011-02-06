/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaCascade;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaCascadeTests
	extends ContextModelTestCase
{
	private ICompilationUnit createTestEntityWithOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
			}
		});
	}
	
	
	public JavaCascadeTests(String name) {
		super(name);
	}
	
	
	public void testUpdateCascadeAll() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isAll());
		assertFalse(annotation.isCascadeAll());
		
		//set all in the resource model, verify context model updated
		annotation.setCascadeAll(true);
		getJpaProject().synchronizeContextModel();
		assertTrue(annotation.isCascadeAll());
		assertTrue(cascade.isAll());
		
		//set all to false in the resource model
		annotation.setCascadeAll(false);
		getJpaProject().synchronizeContextModel();
		assertFalse(annotation.isCascadeAll());
		assertFalse(cascade.isAll());
	}
	
	public void testModifyCascadeAll() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isAll());
		assertFalse(annotation.isCascadeAll());
					
		//set all in the context model, verify resource model updated
		cascade.setAll(true);
		assertTrue(annotation.isCascadeAll());
		assertTrue(cascade.isAll());
		
		//set all to false in the context model
		cascade.setAll(false);
		assertFalse(annotation.isCascadeAll());
		assertFalse(cascade.isAll());
	}
	
	public void testUpdateCascadePersist() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isPersist());
		assertFalse(annotation.isCascadePersist());
		
		//set persist in the resource model, verify context model updated
		annotation.setCascadePersist(true);
		getJpaProject().synchronizeContextModel();
		assertTrue(annotation.isCascadePersist());
		assertTrue(cascade.isPersist());
		
		//set persist to false in the resource model
		annotation.setCascadePersist(false);
		getJpaProject().synchronizeContextModel();
		assertFalse(annotation.isCascadePersist());
		assertFalse(cascade.isPersist());
	}
	
	public void testModifyCascadePersist() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isPersist());
		assertFalse(annotation.isCascadePersist());
					
		//set persist in the context model, verify resource model updated
		cascade.setPersist(true);
		assertTrue(annotation.isCascadePersist());
		assertTrue(cascade.isPersist());
		
		//set persist to false in the context model
		cascade.setPersist(false);
		assertFalse(annotation.isCascadePersist());
		assertFalse(cascade.isPersist());
	}
	
	public void testUpdateCascadeMerge() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isMerge());
		assertFalse(annotation.isCascadeMerge());
		
		//set merge in the resource model, verify context model updated
		annotation.setCascadeMerge(true);
		getJpaProject().synchronizeContextModel();
		assertTrue(annotation.isCascadeMerge());
		assertTrue(cascade.isMerge());
		
		//set merge to false in the resource model
		annotation.setCascadeMerge(false);
		getJpaProject().synchronizeContextModel();
		assertFalse(annotation.isCascadeMerge());
		assertFalse(cascade.isMerge());
	}
	
	public void testModifyCascadeMerge() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isMerge());
		assertFalse(annotation.isCascadeMerge());
					
		//set merge in the context model, verify resource model updated
		cascade.setMerge(true);
		assertTrue(annotation.isCascadeMerge());
		assertTrue(cascade.isMerge());
		
		//set merge to false in the context model
		cascade.setMerge(false);
		assertFalse(annotation.isCascadeMerge());
		assertFalse(cascade.isMerge());
	}
	
	public void testUpdateCascadeRemove() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isRemove());
		assertFalse(annotation.isCascadeRemove());
		
		//set remove in the resource model, verify context model updated
		annotation.setCascadeRemove(true);
		getJpaProject().synchronizeContextModel();
		assertTrue(annotation.isCascadeRemove());
		assertTrue(cascade.isRemove());
		
		//set remove to false in the resource model
		annotation.setCascadeRemove(false);
		getJpaProject().synchronizeContextModel();
		assertFalse(annotation.isCascadeRemove());
		assertFalse(cascade.isRemove());
	}
	
	public void testModifyCascadeRemove() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isRemove());
		assertFalse(annotation.isCascadeRemove());
					
		//set remove in the context model, verify resource model updated
		cascade.setRemove(true);
		assertTrue(annotation.isCascadeRemove());
		assertTrue(cascade.isRemove());
		
		//set remove to false in the context model
		cascade.setRemove(false);
		assertFalse(annotation.isCascadeRemove());
		assertFalse(cascade.isRemove());
	}
	
	public void testUpdateCascadeRefresh() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isRefresh());
		assertFalse(annotation.isCascadeRefresh());
		
		//set refresh in the resource model, verify context model updated
		annotation.setCascadeRefresh(true);
		getJpaProject().synchronizeContextModel();
		assertTrue(annotation.isCascadeRefresh());
		assertTrue(cascade.isRefresh());
		
		//set refresh to false in the resource model
		annotation.setCascadeRefresh(false);
		getJpaProject().synchronizeContextModel();
		assertFalse(annotation.isCascadeRefresh());
		assertFalse(cascade.isRefresh());
	}
	
	public void testModifyCascadeRefresh() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping mapping = (JavaOneToOneMapping) persistentAttribute.getMapping();
		JavaCascade cascade = mapping.getCascade();
		
		assertFalse(cascade.isRefresh());
		assertFalse(annotation.isCascadeRefresh());
					
		//set refresh in the context model, verify resource model updated
		cascade.setRefresh(true);
		assertTrue(annotation.isCascadeRefresh());
		assertTrue(cascade.isRefresh());
		
		//set refresh to false in the context model
		cascade.setRefresh(false);
		assertFalse(annotation.isCascadeRefresh());
		assertFalse(cascade.isRefresh());
	}
}
