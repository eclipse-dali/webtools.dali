/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_0.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.resource.java.EclipseLink2_0;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.resource.java.EclipseLinkMapKeyConvertAnnotation2_0;

@SuppressWarnings("nls")
public class MapKeyConvertAnnotationTests extends EclipseLink2_0JavaResourceModelTestCase {
	
	public MapKeyConvertAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestMapKeyConvert() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_0.MAP_KEY_CONVERT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyConvert");
			}
		});
	}
	
	private ICompilationUnit createTestConverterWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_0.MAP_KEY_CONVERT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyConvert(value=\"myConverter\")");
			}
		});
	}

	public void testMapKeyConvertAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestMapKeyConvert();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		assertNotNull(resourceField.getAnnotation(EclipseLink2_0.MAP_KEY_CONVERT));
		
		resourceField.removeAnnotation(EclipseLink2_0.MAP_KEY_CONVERT);
		assertNull(resourceField.getAnnotation(EclipseLink2_0.MAP_KEY_CONVERT));
		
		resourceField.addAnnotation(EclipseLink2_0.MAP_KEY_CONVERT);
		assertNotNull(resourceField.getAnnotation(EclipseLink2_0.MAP_KEY_CONVERT));
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkMapKeyConvertAnnotation2_0 convert = (EclipseLinkMapKeyConvertAnnotation2_0) resourceField.getAnnotation(EclipseLink2_0.MAP_KEY_CONVERT);
		assertEquals("myConverter", convert.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkMapKeyConvertAnnotation2_0 convert = (EclipseLinkMapKeyConvertAnnotation2_0) resourceField.getAnnotation(EclipseLink2_0.MAP_KEY_CONVERT);
		assertEquals("myConverter", convert.getValue());
		
		convert.setValue("Bar");
		assertEquals("Bar", convert.getValue());
		
		assertSourceContains("@MapKeyConvert(value=\"Bar\")", cu);
	}
	
	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkMapKeyConvertAnnotation2_0 convert = (EclipseLinkMapKeyConvertAnnotation2_0) resourceField.getAnnotation(EclipseLink2_0.MAP_KEY_CONVERT);
		assertEquals("myConverter", convert.getValue());
		
		convert.setValue(null);
		assertNull(convert.getValue());
		
		assertSourceContains("@MapKeyConvert", cu);
		assertSourceDoesNotContain("value", cu);
	}

}
