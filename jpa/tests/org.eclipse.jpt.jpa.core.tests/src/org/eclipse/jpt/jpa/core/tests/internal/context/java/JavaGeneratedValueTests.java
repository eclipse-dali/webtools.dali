/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.GeneratedValue;
import org.eclipse.jpt.jpa.core.context.GenerationType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaGeneratedValueTests extends ContextModelTestCase
{
	private static final String GENERATOR = "MY_GENERATOR";

	private ICompilationUnit createTestEntityWithGeneratedValue() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.GENERATED_VALUE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@GeneratedValue(generator=\"" + GENERATOR + "\")");
			}
		});
	}
		
	public JavaGeneratedValueTests(String name) {
		super(name);
	}
	
	public void testGetGenerator() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		
		generatedValue.setGenerator("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", idMapping.getGeneratedValue().getGenerator());
	}

	public void testSetSpecifiedGenerator() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		idMapping.getGeneratedValue().setSpecifiedGenerator("foo");
		
		assertEquals("foo", idMapping.getGeneratedValue().getGenerator());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		
		assertEquals("foo", generatedValue.getGenerator());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		idMapping.getGeneratedValue().setSpecifiedGenerator(null);
		
		assertNotNull(idMapping.getGeneratedValue());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		
		assertNotNull(generatedValue);
	}
	
	public void testGetStrategy() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		
		generatedValue.setStrategy(org.eclipse.jpt.jpa.core.resource.java.GenerationType.IDENTITY);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(GenerationType.IDENTITY, idMapping.getGeneratedValue().getStrategy());
		assertEquals(GeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getDefaultStrategy());
	}

	public void testSetSpecifiedStrategy() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());

		idMapping.getGeneratedValue().setSpecifiedStrategy(GenerationType.IDENTITY);
		
		assertEquals(GenerationType.IDENTITY, idMapping.getGeneratedValue().getStrategy());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.GenerationType.IDENTITY, generatedValue.getStrategy());
		
		idMapping.getGeneratedValue().setSpecifiedStrategy(null);
		
		assertEquals(GeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());
		generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		assertNotNull(generatedValue);
		assertNull(generatedValue.getStrategy());
	}
}
