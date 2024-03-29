/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyEnumeratedAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.EnumType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class MapKeyEnumerated2_0AnnotationTests extends JavaResourceModel2_0TestCase {

	public MapKeyEnumerated2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestMapKeyEnumerated() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.MAP_KEY_ENUMERATED);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyEnumerated");
			}
		});
	}
	
	private ICompilationUnit createTestMapKeyEnumeratedWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.MAP_KEY_ENUMERATED, JPA.ENUM_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyEnumerated(EnumType.ORDINAL)");
			}
		});
	}

	public void testMapKeyEnumerated() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyEnumerated();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		MapKeyEnumeratedAnnotation2_0 enumerated = (MapKeyEnumeratedAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_ENUMERATED);
		assertNotNull(enumerated);
	}
	
	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyEnumeratedWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		MapKeyEnumeratedAnnotation2_0 enumerated = (MapKeyEnumeratedAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_ENUMERATED);
		assertEquals(EnumType.ORDINAL, enumerated.getValue());
	}
	
	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyEnumerated();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		MapKeyEnumeratedAnnotation2_0 enumerated = (MapKeyEnumeratedAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAP_KEY_ENUMERATED);

		enumerated.setValue(EnumType.STRING);
		
		assertSourceContains("@MapKeyEnumerated(STRING)", cu);
		
		enumerated.setValue(null);
		
		assertSourceDoesNotContain("@MapKeyEnumerated(", cu);
		assertSourceContains("@MapKeyEnumerated", cu);
	}

}
