/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MapKey;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class MapKeyTests extends JavaResourceModelTestCase {

	public MapKeyTests(String name) {
		super(name);
	}

	private IType createTestMapKey() throws Exception {
		this.createAnnotationAndMembers("MapKey", "String name() default \"\";");
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
	
	private IType createTestMapKeyWithName() throws Exception {
		this.createAnnotationAndMembers("MapKey", "String name() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAP_KEY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKey(name=\"key\")");
			}
		});
	}

	public void testMapKey() throws Exception {
		IType testType = this.createTestMapKey();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapKey mapKey = (MapKey) attributeResource.annotation(JPA.MAP_KEY);
		assertNotNull(mapKey);
	}
	
	public void testGetName() throws Exception {
		IType testType = this.createTestMapKeyWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapKey mapKey = (MapKey) attributeResource.annotation(JPA.MAP_KEY);
		assertEquals("key", mapKey.getName());
	}
	
	public void testSetName() throws Exception {
		IType testType = this.createTestMapKey();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		MapKey mapKey = (MapKey) attributeResource.annotation(JPA.MAP_KEY);

		mapKey.setName("foo");
		
		assertSourceContains("@MapKey(name=\"foo\")");
		
		mapKey.setName(null);
		
		assertSourceContains("@MapKey");
		assertSourceDoesNotContain("@MapKey(name=\"foo\")");
	}

}
