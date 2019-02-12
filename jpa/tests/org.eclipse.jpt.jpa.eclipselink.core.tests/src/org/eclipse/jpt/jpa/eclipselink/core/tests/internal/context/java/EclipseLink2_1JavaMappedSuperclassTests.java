/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_2ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_1JavaMappedSuperclassTests extends EclipseLink2_2ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;

	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}


	public EclipseLink2_1JavaMappedSuperclassTests(String name) {
		super(name);
	}
	
	public void testGetSequenceGenerator() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaMappedSuperclass().getGeneratorContainer().getSequenceGenerator());
		assertEquals(0, getPersistenceUnit().getGeneratorsSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		resourceType.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(getJavaMappedSuperclass().getGeneratorContainer().getSequenceGenerator());
		assertEquals(2, resourceType.getAnnotationsSize());
		assertEquals(1, getPersistenceUnit().getGeneratorsSize());
		
		getJavaMappedSuperclass().getGeneratorContainer().getSequenceGenerator().setName("foo");
		assertEquals(1, getPersistenceUnit().getGeneratorsSize());
	}
	
	public void testAddSequenceGenerator() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		assertNull(getJavaMappedSuperclass().getGeneratorContainer().getSequenceGenerator());
		
		getJavaMappedSuperclass().getGeneratorContainer().addSequenceGenerator();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
	
		assertNotNull(resourceType.getAnnotation(JPA.SEQUENCE_GENERATOR));
		assertNotNull(getJavaMappedSuperclass().getGeneratorContainer().getSequenceGenerator());
		
		//try adding another sequence generator, should get an IllegalStateException
		try {
			getJavaMappedSuperclass().getGeneratorContainer().addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		resourceType.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		getJavaMappedSuperclass().getGeneratorContainer().removeSequenceGenerator();
		
		assertNull(getJavaMappedSuperclass().getGeneratorContainer().getSequenceGenerator());
		assertNull(resourceType.getAnnotation(JPA.SEQUENCE_GENERATOR));

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			getJavaMappedSuperclass().getGeneratorContainer().removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetTableGenerator() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaMappedSuperclass().getGeneratorContainer().getTableGenerator());
		assertEquals(0, getPersistenceUnit().getGeneratorsSize());
	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		resourceType.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(getJavaMappedSuperclass().getGeneratorContainer().getTableGenerator());		
		assertEquals(2, resourceType.getAnnotationsSize());
		assertEquals(1, getPersistenceUnit().getGeneratorsSize());
		
		getJavaMappedSuperclass().getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, getPersistenceUnit().getGeneratorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaMappedSuperclass().getGeneratorContainer().getTableGenerator());
		
		getJavaMappedSuperclass().getGeneratorContainer().addTableGenerator();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
	
		assertNotNull(resourceType.getAnnotation(JPA.TABLE_GENERATOR));
		assertNotNull(getJavaMappedSuperclass().getGeneratorContainer().getTableGenerator());
		
		//try adding another table generator, should get an IllegalStateException
		try {
			getJavaMappedSuperclass().getGeneratorContainer().addTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		resourceType.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		getJavaMappedSuperclass().getGeneratorContainer().removeTableGenerator();
		
		assertNull(getJavaMappedSuperclass().getGeneratorContainer().getTableGenerator());
		assertNull(resourceType.getAnnotation(JPA.TABLE_GENERATOR));
		
		//try removing the table generator again, should get an IllegalStateException
		try {
			getJavaMappedSuperclass().getGeneratorContainer().removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

}
