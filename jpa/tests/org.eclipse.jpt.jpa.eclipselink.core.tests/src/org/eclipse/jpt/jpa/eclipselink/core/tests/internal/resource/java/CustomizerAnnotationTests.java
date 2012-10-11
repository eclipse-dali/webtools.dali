/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkCustomizerAnnotation;

@SuppressWarnings("nls")
public class CustomizerAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public CustomizerAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestCustomizer() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Customizer");
			}
		});
	}
	
	private ICompilationUnit createTestCustomizerWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Customizer(Foo.class)");
			}
		});
	}

	public void testCustomizerAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestCustomizer();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertNotNull(resourceType.getAnnotation(EclipseLink.CUSTOMIZER));
		
		resourceType.removeAnnotation(EclipseLink.CUSTOMIZER);
		assertNull(resourceType.getAnnotation(EclipseLink.CUSTOMIZER));
		
		resourceType.addAnnotation(EclipseLink.CUSTOMIZER);
		assertNotNull(resourceType.getAnnotation(EclipseLink.CUSTOMIZER));
	}

	public void testGetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestCustomizerWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCustomizerAnnotation converter = (EclipseLinkCustomizerAnnotation) resourceType.getAnnotation(EclipseLink.CUSTOMIZER);
		assertEquals("Foo", converter.getValue());
	}

	public void testSetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestCustomizerWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCustomizerAnnotation converter = (EclipseLinkCustomizerAnnotation) resourceType.getAnnotation(EclipseLink.CUSTOMIZER);
		assertEquals("Foo", converter.getValue());
		
		converter.setValue("Bar");
		assertEquals("Bar", converter.getValue());
		
		assertSourceContains("@Customizer(Bar.class)", cu);
	}
	
	public void testSetConverterClassNull() throws Exception {
		ICompilationUnit cu = this.createTestCustomizerWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCustomizerAnnotation converter = (EclipseLinkCustomizerAnnotation) resourceType.getAnnotation(EclipseLink.CUSTOMIZER);
		assertEquals("Foo", converter.getValue());
		
		converter.setValue(null);
		assertNull(converter.getValue());
		
		assertSourceContains("@Customizer", cu);
		assertSourceDoesNotContain("Foo.class", cu);
	}
}
