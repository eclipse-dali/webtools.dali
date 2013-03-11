/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OneToOneAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE);
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation2_0 annotation = (OneToOneAnnotation2_0) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 mapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		Cascade2_0 cascade = (Cascade2_0) mapping.getCascade();
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation2_0 annotation = (OneToOneAnnotation2_0) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 mapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		Cascade2_0 cascade = (Cascade2_0) mapping.getCascade();
		
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
