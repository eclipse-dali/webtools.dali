/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JavaNamedQueryTests extends ContextModelTestCase
{
	private static final String QUERY_NAME = "QUERY_NAME";
	private static final String QUERY_QUERY = "MY_QUERY";
		
	private ICompilationUnit createTestEntityWithNamedQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.NAMED_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedQuery(name=\"" + QUERY_NAME + "\", query=\"" + QUERY_QUERY + "\")");
			}
		});
	}


		
	public JavaNamedQueryTests(String name) {
		super(name);
	}
	
	public void testUpdateName() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
				
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation javaNamedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);
		
		assertEquals(QUERY_NAME, javaNamedQuery.getName());
		assertEquals(QUERY_NAME, namedQuery.getName());
				
		//set name to null in the resource model
		javaNamedQuery.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(javaNamedQuery.getName());
		assertNull(namedQuery.getName());

		//set name in the resource model, verify context model updated
		javaNamedQuery.setName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", javaNamedQuery.getName());
		assertEquals("foo", namedQuery.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
				
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation javaNamedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);
		
		assertEquals(QUERY_NAME, javaNamedQuery.getName());
		assertEquals(QUERY_NAME, namedQuery.getName());
				
		//set name to null in the context model
		namedQuery.setName(null);
		assertNull(javaNamedQuery.getName());
		assertNull(namedQuery.getName());

		//set name in the context model, verify resource model updated
		namedQuery.setName("foo");
		assertEquals("foo", javaNamedQuery.getName());
		assertEquals("foo", namedQuery.getName());
	}
	
	public void testUpdateQuery() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
				
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation javaNamedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);
		
		assertEquals(QUERY_QUERY, javaNamedQuery.getQuery());
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
				
		//set name to null in the resource model
		javaNamedQuery.setQuery(null);
		getJpaProject().synchronizeContextModel();
		assertNull(javaNamedQuery.getQuery());
		assertNull(namedQuery.getQuery());

		//set name in the resource model, verify context model updated
		javaNamedQuery.setQuery("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", javaNamedQuery.getQuery());
		assertEquals("foo", namedQuery.getQuery());
	}
	
	public void testModifyQuery() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
				
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation javaNamedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);
		
		assertEquals(QUERY_QUERY, javaNamedQuery.getQuery());
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
				
		//set name to null in the context model
		namedQuery.setQuery(null);
		assertNull(javaNamedQuery.getQuery());
		assertNull(namedQuery.getQuery());

		//set name in the context model, verify resource model updated
		namedQuery.setQuery("foo");
		assertEquals("foo", javaNamedQuery.getQuery());
		assertEquals("foo", namedQuery.getQuery());
	}
	
	
	public void testAddHint() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
				
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation javaNamedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);
	
		
		QueryHint queryHint = namedQuery.addHint(0);
		queryHint.setName("FOO");

		assertEquals("FOO", javaNamedQuery.hintAt(0).getName());
		
		QueryHint queryHint2 = namedQuery.addHint(0);
		queryHint2.setName("BAR");
		
		assertEquals("BAR", javaNamedQuery.hintAt(0).getName());
		assertEquals("FOO", javaNamedQuery.hintAt(1).getName());
		
		QueryHint queryHint3 = namedQuery.addHint(1);
		queryHint3.setName("BAZ");
		
		assertEquals("BAR", javaNamedQuery.hintAt(0).getName());
		assertEquals("BAZ", javaNamedQuery.hintAt(1).getName());
		assertEquals("FOO", javaNamedQuery.hintAt(2).getName());
		
		ListIterator<QueryHint> hints = namedQuery.getHints().iterator();
		assertEquals(queryHint2, hints.next());
		assertEquals(queryHint3, hints.next());
		assertEquals(queryHint, hints.next());
		
		hints = namedQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
	}
	
	public void testRemoveHint() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
				
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation javaNamedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);

		namedQuery.addHint(0).setName("FOO");
		namedQuery.addHint(1).setName("BAR");
		namedQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, javaNamedQuery.hintsSize());
		
		namedQuery.removeHint(0);
		assertEquals(2, javaNamedQuery.hintsSize());
		assertEquals("BAR", javaNamedQuery.hintAt(0).getName());
		assertEquals("BAZ", javaNamedQuery.hintAt(1).getName());

		namedQuery.removeHint(0);
		assertEquals(1, javaNamedQuery.hintsSize());
		assertEquals("BAZ", javaNamedQuery.hintAt(0).getName());
		
		namedQuery.removeHint(0);
		assertEquals(0, javaNamedQuery.hintsSize());
	}
	
	public void testMoveHint() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
				
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation javaNamedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);

		namedQuery.addHint(0).setName("FOO");
		namedQuery.addHint(1).setName("BAR");
		namedQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, javaNamedQuery.hintsSize());
		
		
		namedQuery.moveHint(2, 0);
		ListIterator<QueryHint> hints = namedQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAR", javaNamedQuery.hintAt(0).getName());
		assertEquals("BAZ", javaNamedQuery.hintAt(1).getName());
		assertEquals("FOO", javaNamedQuery.hintAt(2).getName());


		namedQuery.moveHint(0, 1);
		hints = namedQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAZ", javaNamedQuery.hintAt(0).getName());
		assertEquals("BAR", javaNamedQuery.hintAt(1).getName());
		assertEquals("FOO", javaNamedQuery.hintAt(2).getName());
	}
	
	public void testUpdateHints() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
				
		JavaResourcePersistentType resourceType = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation namedQueryAnnotation = (NamedQueryAnnotation) resourceType.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);
		
		namedQueryAnnotation.addHint(0).setName("FOO");
		namedQueryAnnotation.addHint(1).setName("BAR");
		namedQueryAnnotation.addHint(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();

		ListIterator<QueryHint> hints = namedQuery.getHints().iterator();
		assertEquals("FOO", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedQueryAnnotation.moveHint(2, 0);
		getJpaProject().synchronizeContextModel();
		hints = namedQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		namedQueryAnnotation.moveHint(0, 1);
		getJpaProject().synchronizeContextModel();
		hints = namedQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		namedQueryAnnotation.removeHint(1);
		getJpaProject().synchronizeContextModel();
		hints = namedQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		namedQueryAnnotation.removeHint(1);
		getJpaProject().synchronizeContextModel();
		hints = namedQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedQueryAnnotation.removeHint(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(namedQuery.getHints().iterator().hasNext());
	}
	
	public void testHintsSize() throws Exception {
		createTestEntityWithNamedQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		
		NamedQuery namedQuery = entity.getQueryContainer().namedQueries().next();
		assertEquals(0, namedQuery.getHintsSize());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		NamedQueryAnnotation javaNamedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(NamedQueryAnnotation.ANNOTATION_NAME);
		
		
		javaNamedQuery.addHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(1, namedQuery.getHintsSize());
		
		javaNamedQuery.addHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(2, namedQuery.getHintsSize());
		
		javaNamedQuery.removeHint(0);
		javaNamedQuery.removeHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, namedQuery.getHintsSize());
	}
}
