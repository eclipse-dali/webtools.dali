/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.CustomizerAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class CustomizerAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public CustomizerAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestCustomizer() throws Exception {
		this.createAnnotationAndMembers("Customizer", "Class value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Customizer");
			}
		});
	}
	
	private ICompilationUnit createTestCustomizerWithValue() throws Exception {
		this.createAnnotationAndMembers("Customizer", "Class value();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Customizer(Foo.class)");
			}
		});
	}

	public void testCustomizerAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestCustomizer();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		assertNotNull(typeResource.getSupportingAnnotation(EclipseLinkJPA.CUSTOMIZER));
		
		typeResource.removeSupportingAnnotation(EclipseLinkJPA.CUSTOMIZER);
		assertNull(typeResource.getSupportingAnnotation(EclipseLinkJPA.CUSTOMIZER));
		
		typeResource.addSupportingAnnotation(EclipseLinkJPA.CUSTOMIZER);
		assertNotNull(typeResource.getSupportingAnnotation(EclipseLinkJPA.CUSTOMIZER));
	}

	public void testGetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestCustomizerWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CustomizerAnnotation converter = (CustomizerAnnotation) typeResource.getSupportingAnnotation(EclipseLinkJPA.CUSTOMIZER);
		assertEquals("Foo", converter.getValue());
	}

	public void testSetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestCustomizerWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CustomizerAnnotation converter = (CustomizerAnnotation) typeResource.getSupportingAnnotation(EclipseLinkJPA.CUSTOMIZER);
		assertEquals("Foo", converter.getValue());
		
		converter.setValue("Bar");
		assertEquals("Bar", converter.getValue());
		
		assertSourceContains("@Customizer(Bar.class)", cu);
	}
	
	public void testSetConverterClassNull() throws Exception {
		ICompilationUnit cu = this.createTestCustomizerWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CustomizerAnnotation converter = (CustomizerAnnotation) typeResource.getSupportingAnnotation(EclipseLinkJPA.CUSTOMIZER);
		assertEquals("Foo", converter.getValue());
		
		converter.setValue(null);
		assertNull(converter.getValue());
		
		assertSourceContains("@Customizer", cu);
		assertSourceDoesNotContain("Foo.class", cu);
	}
}
