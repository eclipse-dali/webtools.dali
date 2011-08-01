/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaNamedNativeQueryTests extends ContextModelTestCase
{
	private static final String QUERY_NAME = "QUERY_NAME";
	private static final String QUERY_QUERY = "MY_QUERY";
		
	private ICompilationUnit createTestEntityWithNamedNativeQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedNativeQuery(name=\"" + QUERY_NAME + "\", query=\"" + QUERY_QUERY + "\")");
			}
		});
	}


		
	public JavaNamedNativeQueryTests(String name) {
		super(name);
	}
	
	public void testUpdateName() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);

		assertEquals(QUERY_NAME, javaNamedNativeQuery.getName());
		assertEquals(QUERY_NAME, namedNativeQuery.getName());

		//set name to null in the resource model
		javaNamedNativeQuery.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(javaNamedNativeQuery.getName());
		assertNull(namedNativeQuery.getName());

		//set name in the resource model, verify context model updated
		javaNamedNativeQuery.setName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", javaNamedNativeQuery.getName());
		assertEquals("foo", namedNativeQuery.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);

		assertEquals(QUERY_NAME, javaNamedNativeQuery.getName());
		assertEquals(QUERY_NAME, namedNativeQuery.getName());
				
		//set name to null in the context model
		namedNativeQuery.setName(null);
		assertNull(javaNamedNativeQuery.getName());
		assertNull(namedNativeQuery.getName());

		//set name in the context model, verify resource model updated
		namedNativeQuery.setName("foo");
		assertEquals("foo", javaNamedNativeQuery.getName());
		assertEquals("foo", namedNativeQuery.getName());
	}
	
	public void testUpdateQuery() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);

		assertEquals(QUERY_QUERY, javaNamedNativeQuery.getQuery());
		assertEquals(QUERY_QUERY, namedNativeQuery.getQuery());
				
		//set name to null in the resource model
		javaNamedNativeQuery.setQuery(null);
		getJpaProject().synchronizeContextModel();
		assertNull(javaNamedNativeQuery.getQuery());
		assertNull(namedNativeQuery.getQuery());

		//set name in the resource model, verify context model updated
		javaNamedNativeQuery.setQuery("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", javaNamedNativeQuery.getQuery());
		assertEquals("foo", namedNativeQuery.getQuery());
	}
	
	public void testModifyQuery() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
	
		assertEquals(QUERY_QUERY, javaNamedNativeQuery.getQuery());
		assertEquals(QUERY_QUERY, namedNativeQuery.getQuery());
				
		//set name to null in the context model
		namedNativeQuery.setQuery(null);
		assertNull(javaNamedNativeQuery.getQuery());
		assertNull(namedNativeQuery.getQuery());

		//set name in the context model, verify resource model updated
		namedNativeQuery.setQuery("foo");
		assertEquals("foo", javaNamedNativeQuery.getQuery());
		assertEquals("foo", namedNativeQuery.getQuery());
	}
	
	
	public void testAddHint() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
	
		
		QueryHint queryHint = namedNativeQuery.addHint(0);
		queryHint.setName("FOO");

		assertEquals("FOO", javaNamedNativeQuery.hintAt(0).getName());
		
		QueryHint queryHint2 = namedNativeQuery.addHint(0);
		queryHint2.setName("BAR");
		
		assertEquals("BAR", javaNamedNativeQuery.hintAt(0).getName());
		assertEquals("FOO", javaNamedNativeQuery.hintAt(1).getName());
		
		QueryHint queryHint3 = namedNativeQuery.addHint(1);
		queryHint3.setName("BAZ");
		
		assertEquals("BAR", javaNamedNativeQuery.hintAt(0).getName());
		assertEquals("BAZ", javaNamedNativeQuery.hintAt(1).getName());
		assertEquals("FOO", javaNamedNativeQuery.hintAt(2).getName());
		
		ListIterator<? extends QueryHint> hints = namedNativeQuery.getHints().iterator();
		assertEquals(queryHint2, hints.next());
		assertEquals(queryHint3, hints.next());
		assertEquals(queryHint, hints.next());
		
		hints = namedNativeQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
	}
	
	public void testRemoveHint() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);

		namedNativeQuery.addHint(0).setName("FOO");
		namedNativeQuery.addHint(1).setName("BAR");
		namedNativeQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, javaNamedNativeQuery.getHintsSize());
		
		namedNativeQuery.removeHint(0);
		assertEquals(2, javaNamedNativeQuery.getHintsSize());
		assertEquals("BAR", javaNamedNativeQuery.hintAt(0).getName());
		assertEquals("BAZ", javaNamedNativeQuery.hintAt(1).getName());

		namedNativeQuery.removeHint(0);
		assertEquals(1, javaNamedNativeQuery.getHintsSize());
		assertEquals("BAZ", javaNamedNativeQuery.hintAt(0).getName());
		
		namedNativeQuery.removeHint(0);
		assertEquals(0, javaNamedNativeQuery.getHintsSize());
	}
	
	public void testMoveHint() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);

		namedNativeQuery.addHint(0).setName("FOO");
		namedNativeQuery.addHint(1).setName("BAR");
		namedNativeQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, javaNamedNativeQuery.getHintsSize());
		
		
		namedNativeQuery.moveHint(2, 0);
		ListIterator<? extends QueryHint> hints = namedNativeQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAR", javaNamedNativeQuery.hintAt(0).getName());
		assertEquals("BAZ", javaNamedNativeQuery.hintAt(1).getName());
		assertEquals("FOO", javaNamedNativeQuery.hintAt(2).getName());


		namedNativeQuery.moveHint(0, 1);
		hints = namedNativeQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAZ", javaNamedNativeQuery.hintAt(0).getName());
		assertEquals("BAR", javaNamedNativeQuery.hintAt(1).getName());
		assertEquals("FOO", javaNamedNativeQuery.hintAt(2).getName());
	}
	
	public void testUpdateHints() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation queryAnnotation = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		
		queryAnnotation.addHint(0).setName("FOO");
		queryAnnotation.addHint(1).setName("BAR");
		queryAnnotation.addHint(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<? extends QueryHint> hints = namedNativeQuery.getHints().iterator();
		assertEquals("FOO", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		queryAnnotation.moveHint(2, 0);
		getJpaProject().synchronizeContextModel();
		hints = namedNativeQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		queryAnnotation.moveHint(0, 1);
		getJpaProject().synchronizeContextModel();
		hints = namedNativeQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		queryAnnotation.removeHint(1);
		getJpaProject().synchronizeContextModel();
		hints = namedNativeQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		queryAnnotation.removeHint(1);
		getJpaProject().synchronizeContextModel();
		hints = namedNativeQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		queryAnnotation.removeHint(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(namedNativeQuery.getHints().iterator().hasNext());
	}

	public void testHintsSize() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
		assertEquals(0, namedNativeQuery.getHintsSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		
		
		javaNamedNativeQuery.addHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(1, namedNativeQuery.getHintsSize());
		
		javaNamedNativeQuery.addHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(2, namedNativeQuery.getHintsSize());
		
		javaNamedNativeQuery.removeHint(0);
		javaNamedNativeQuery.removeHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, namedNativeQuery.getHintsSize());
	}
	
	public void testUpdateResultClass() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation queryAnnotation = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		
		assertEquals(null, queryAnnotation.getResultClass());
		assertEquals(null, namedNativeQuery.getResultClass());

		//set name in the resource model, verify context model updated
		queryAnnotation.setResultClass("foo");
		this.getJpaProject().synchronizeContextModel();
		assertEquals("foo", queryAnnotation.getResultClass());
		assertEquals("foo", namedNativeQuery.getResultClass());
		
		//set name to null in the resource model
		queryAnnotation.setResultClass(null);
		this.getJpaProject().synchronizeContextModel();
		assertNull(queryAnnotation.getResultClass());
		assertNull(namedNativeQuery.getResultClass());
	}
	
	public void testModifyResultClass() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		
		assertEquals(null, javaNamedNativeQuery.getResultClass());
		assertEquals(null, namedNativeQuery.getResultClass());
				
		//set name in the context model, verify resource model updated
		namedNativeQuery.setResultClass("foo");
		assertEquals("foo", javaNamedNativeQuery.getResultClass());
		assertEquals("foo", namedNativeQuery.getResultClass());

		//set name to null in the context model
		namedNativeQuery.setResultClass(null);
		assertNull(javaNamedNativeQuery.getResultClass());
		assertNull(namedNativeQuery.getResultClass());
	}
	
	public void testUpdateResultSetMapping() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		
		assertEquals(null, javaNamedNativeQuery.getResultSetMapping());
		assertEquals(null, namedNativeQuery.getResultSetMapping());

		//set name in the resource model, verify context model updated
		javaNamedNativeQuery.setResultSetMapping("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", javaNamedNativeQuery.getResultSetMapping());
		assertEquals("foo", namedNativeQuery.getResultSetMapping());
		
		//set name to null in the resource model
		javaNamedNativeQuery.setResultSetMapping(null);
		getJpaProject().synchronizeContextModel();
		assertNull(javaNamedNativeQuery.getResultSetMapping());
		assertNull(namedNativeQuery.getResultSetMapping());
	}
	
	public void testModifyResultSetMapping() throws Exception {
		createTestEntityWithNamedNativeQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedNativeQuery namedNativeQuery = entity.getQueryContainer().getNamedNativeQueries().iterator().next();
				
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		NamedNativeQueryAnnotation javaNamedNativeQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		
		assertEquals(null, javaNamedNativeQuery.getResultSetMapping());
		assertEquals(null, namedNativeQuery.getResultSetMapping());

		//set name in the context model, verify resource model updated
		namedNativeQuery.setResultSetMapping("foo");
		assertEquals("foo", javaNamedNativeQuery.getResultSetMapping());
		assertEquals("foo", namedNativeQuery.getResultSetMapping());
		
		//set name to null in the context model
		namedNativeQuery.setResultSetMapping(null);
		assertNull(javaNamedNativeQuery.getResultSetMapping());
		assertNull(namedNativeQuery.getResultSetMapping());
	}

}
