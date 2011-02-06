/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OneToOne2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaCascade2_0Tests
	extends Generic2_0ContextModelTestCase
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
	
	
	public GenericJavaCascade2_0Tests(String name) {
		super(name);
	}
	
	
	public void testUpdateCascadeDetach() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOne2_0Annotation annotation = (OneToOne2_0Annotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping2_0 mapping = (JavaOneToOneMapping2_0) persistentAttribute.getMapping();
		JavaCascade2_0 cascade = (JavaCascade2_0) mapping.getCascade();
		
		assertFalse(cascade.isDetach());
		assertFalse(annotation.isCascadeDetach());
		
		//set detach in the resource model, verify context model updated
		annotation.setCascadeDetach(true);
		getJpaProject().synchronizeContextModel();
		assertTrue(annotation.isCascadeDetach());
		assertTrue(cascade.isDetach());
		
		//set detach to false in the resource model
		annotation.setCascadeDetach(false);
		getJpaProject().synchronizeContextModel();
		assertFalse(annotation.isCascadeDetach());
		assertFalse(cascade.isDetach());
	}
	
	public void testModifyCascadeDetach() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOne2_0Annotation annotation = (OneToOne2_0Annotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		JavaOneToOneMapping2_0 mapping = (JavaOneToOneMapping2_0) persistentAttribute.getMapping();
		JavaCascade2_0 cascade = (JavaCascade2_0) mapping.getCascade();
		
		assertFalse(cascade.isDetach());
		assertFalse(annotation.isCascadeDetach());
					
		//set detach in the context model, verify resource model updated
		cascade.setDetach(true);
		assertTrue(annotation.isCascadeDetach());
		assertTrue(cascade.isDetach());
		
		//set detach to false in the context model
		cascade.setDetach(false);
		assertFalse(annotation.isCascadeDetach());
		assertFalse(cascade.isDetach());
	}
}
