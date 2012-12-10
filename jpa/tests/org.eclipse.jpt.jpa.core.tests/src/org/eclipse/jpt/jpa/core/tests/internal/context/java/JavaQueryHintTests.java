/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaQueryHintTests extends ContextModelTestCase
{
	private static final String QUERY_NAME = "QUERY_NAME";
		
	private ICompilationUnit createTestEntityWithNamedQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.NAMED_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedQuery(name=\"" + QUERY_NAME + "\", hints=@QueryHint())");
			}
		});
	}


		
	public JavaQueryHintTests(String name) {
		super(name);
	}
	
	public void testUpdateName() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		QueryHint queryHint = entity.getQueryContainer().getNamedQueries().iterator().next().getHints().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedQueryAnnotation queryAnnotation = ((NamedQueryAnnotation) resourceType.getAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME));
		QueryHintAnnotation queryHintAnnotation = queryAnnotation.getHints().iterator().next();
		
		assertNull(queryHintAnnotation.getName());
		assertNull(queryHint.getName());

		//set name in the resource model, verify context model updated
		queryHintAnnotation.setName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", queryHintAnnotation.getName());
		assertEquals("foo", queryHint.getName());
		
		//set name to null in the resource model
		queryHintAnnotation.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(queryHint.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		QueryHint queryHint = entity.getQueryContainer().getNamedQueries().iterator().next().getHints().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedQueryAnnotation queryAnnotation = ((NamedQueryAnnotation) resourceType.getAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME));
		QueryHintAnnotation queryHintAnnotation = queryAnnotation.getHints().iterator().next();
		
		assertNull(queryHintAnnotation.getName());
		assertNull(queryHint.getName());

		//set name in the context model, verify resource model updated
		queryHint.setName("foo");
		assertEquals("foo", queryHintAnnotation.getName());
		assertEquals("foo", queryHint.getName());
		
		//set name to null in the context model
		queryHint.setName(null);
		assertNull(queryHintAnnotation.getName());
		assertNull(queryHint.getName());
	}
	
	public void testUpdateValue() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		QueryHint queryHint = entity.getQueryContainer().getNamedQueries().iterator().next().getHints().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedQueryAnnotation queryAnnotation = ((NamedQueryAnnotation) resourceType.getAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME));
		QueryHintAnnotation queryHintAnnotation = queryAnnotation.getHints().iterator().next();
		
		assertNull(queryHintAnnotation.getValue());
		assertNull(queryHint.getValue());

		//set name in the resource model, verify context model updated
		queryHintAnnotation.setValue("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", queryHintAnnotation.getValue());
		assertEquals("foo", queryHint.getValue());
		
		//set name to null in the resource model
		queryHintAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertNull(queryHint.getValue());
	}
	
	public void testModifyValue() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		QueryHint queryHint = entity.getQueryContainer().getNamedQueries().iterator().next().getHints().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedQueryAnnotation queryAnnotation = ((NamedQueryAnnotation) resourceType.getAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME));
		QueryHintAnnotation queryHintAnnotation = queryAnnotation.getHints().iterator().next();
		
		assertNull(queryHintAnnotation.getValue());
		assertNull(queryHint.getValue());

		//set name in the context model, verify resource model updated
		queryHint.setValue("foo");
		assertEquals("foo", queryHintAnnotation.getValue());
		assertEquals("foo", queryHint.getValue());
		
		//set name to null in the context model
		queryHint.setValue(null);
		assertNull(queryHintAnnotation.getValue());
		assertNull(queryHint.getValue());
	}
}
