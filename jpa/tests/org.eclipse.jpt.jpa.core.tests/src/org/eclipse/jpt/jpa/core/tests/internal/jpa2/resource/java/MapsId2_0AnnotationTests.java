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
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsIdAnnotation2_0;

@SuppressWarnings("nls")
public class MapsId2_0AnnotationTests
	extends JavaResourceModel2_0TestCase
{
	public MapsId2_0AnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestMapsId() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.MAPS_ID);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapsId");
			}
		});
	}
	
	private ICompilationUnit createTestMapsIdWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.MAPS_ID);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapsId(\"foo\")");
			}
		});
	}
	
	public void testMapsId() throws Exception {
		ICompilationUnit cu = this.createTestMapsId();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		MapsIdAnnotation2_0 annotation = (MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID);
		assertNotNull(annotation);
	}
	
	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapsIdWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		MapsIdAnnotation2_0 annotation = (MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID);
		assertEquals("foo", annotation.getValue());
	}
	
	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestMapsId();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		MapsIdAnnotation2_0 annotation = (MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID);
		annotation.setValue("foo");
		assertSourceContains("@MapsId(\"foo\")", cu);
		
		annotation.setValue(null);
		assertSourceContains("@MapsId", cu);
	}
}
