/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class MapKeyTests extends JavaResourceModelTestCase {

	public MapKeyTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestMapKey() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAP_KEY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKey");
			}
		});
	}
	
	private ICompilationUnit createTestMapKeyWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAP_KEY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKey(name = \"key\")");
			}
		});
	}

	public void testMapKey() throws Exception {
		ICompilationUnit cu = this.createTestMapKey();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapKeyAnnotation mapKey = (MapKeyAnnotation) attributeResource.getSupportingAnnotation(JPA.MAP_KEY);
		assertNotNull(mapKey);
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapKeyAnnotation mapKey = (MapKeyAnnotation) attributeResource.getSupportingAnnotation(JPA.MAP_KEY);
		assertEquals("key", mapKey.getName());
	}
	
	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestMapKey();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		MapKeyAnnotation mapKey = (MapKeyAnnotation) attributeResource.getSupportingAnnotation(JPA.MAP_KEY);

		mapKey.setName("foo");
	
		assertSourceContains("@MapKey(name = \"foo\")", cu);
		
		mapKey.setName(null);
		
		assertSourceContains("@MapKey", cu);
		assertSourceDoesNotContain("@MapKey(name = \"foo\")", cu);
	}

}
