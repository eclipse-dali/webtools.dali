/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConvertAnnotation;

@SuppressWarnings("nls")
public class ConvertAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public ConvertAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestConvert() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.CONVERT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert");
			}
		});
	}
	
	private ICompilationUnit createTestConverterWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.CONVERT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(value=\"myConverter\")");
			}
		});
	}

	public void testConvertAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestConvert();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		assertNotNull(resourceField.getAnnotation(EclipseLink.CONVERT));
		
		resourceField.removeAnnotation(EclipseLink.CONVERT);
		assertNull(resourceField.getAnnotation(EclipseLink.CONVERT));
		
		resourceField.addAnnotation(EclipseLink.CONVERT);
		assertNotNull(resourceField.getAnnotation(EclipseLink.CONVERT));
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ConvertAnnotation convert = (ConvertAnnotation) resourceField.getAnnotation(EclipseLink.CONVERT);
		assertEquals("myConverter", convert.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ConvertAnnotation convert = (ConvertAnnotation) resourceField.getAnnotation(EclipseLink.CONVERT);
		assertEquals("myConverter", convert.getValue());
		
		convert.setValue("Bar");
		assertEquals("Bar", convert.getValue());
		
		assertSourceContains("@Convert(value=\"Bar\")", cu);
	}
	
	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ConvertAnnotation convert = (ConvertAnnotation) resourceField.getAnnotation(EclipseLink.CONVERT);
		assertEquals("myConverter", convert.getValue());
		
		convert.setValue(null);
		assertNull(convert.getValue());
		
		assertSourceContains("@Convert", cu);
		assertSourceDoesNotContain("value", cu);
	}

}
