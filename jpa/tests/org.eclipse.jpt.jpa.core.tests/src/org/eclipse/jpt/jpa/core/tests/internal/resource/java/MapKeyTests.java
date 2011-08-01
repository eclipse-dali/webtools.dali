/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;

@SuppressWarnings("nls")
public class MapKeyTests extends JpaJavaResourceModelTestCase {

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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		MapKeyAnnotation mapKey = (MapKeyAnnotation) resourceField.getAnnotation(JPA.MAP_KEY);
		assertNotNull(mapKey);
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		MapKeyAnnotation mapKey = (MapKeyAnnotation) resourceField.getAnnotation(JPA.MAP_KEY);
		assertEquals("key", mapKey.getName());
	}
	
	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestMapKey();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);

		MapKeyAnnotation mapKey = (MapKeyAnnotation) resourceField.getAnnotation(JPA.MAP_KEY);

		mapKey.setName("foo");
	
		assertSourceContains("@MapKey(name = \"foo\")", cu);
		
		mapKey.setName(null);
		
		assertSourceContains("@MapKey", cu);
		assertSourceDoesNotContain("@MapKey(name = \"foo\")", cu);
	}

}
