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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.generic.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValue;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class DiscriminatorValueTests extends AnnotationTestCase {

	public DiscriminatorValueTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private IType createTestDiscriminatorValue() throws Exception {
		this.createAnnotationAndMembers("DiscriminatorValue", "String value() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@DiscriminatorValue");
			}
		});
	}
	
	private IType createTestDiscriminatorValueWithValue() throws Exception {
		this.createAnnotationAndMembers("DiscriminatorValue", "String value() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@DiscriminatorValue(value=\"discriminator\")");
			}
		});
	}

	protected JavaResource buildParentResource(final IJpaPlatform jpaPlatform) {
		return new JavaResource() {
			public void updateFromJava(CompilationUnit astRoot) {
			}
			public IJpaPlatform jpaPlatform() {
				return jpaPlatform;
			}
		};
	}
	
	protected IJpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}

	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) {
		JavaPersistentTypeResource typeResource = new JavaPersistentTypeResourceImpl(buildParentResource(buildJpaPlatform()), new Type(testType, MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER));
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
	}

	public void testDiscriminatorValue() throws Exception {
		IType testType = this.createTestDiscriminatorValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
	
		DiscriminatorValue discriminatorValue = (DiscriminatorValue) typeResource.annotation(JPA.DISCRIMINATOR_VALUE);
		assertNotNull(discriminatorValue);
	}
	
	public void testGetValue() throws Exception {
		IType testType = this.createTestDiscriminatorValueWithValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		DiscriminatorValue discriminatorValue = (DiscriminatorValue) typeResource.annotation(JPA.DISCRIMINATOR_VALUE);
		assertEquals("discriminator", discriminatorValue.getValue());
	}
	
	public void testSetValue() throws Exception {
		IType testType = this.createTestDiscriminatorValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		DiscriminatorValue discriminatorValue = (DiscriminatorValue) typeResource.annotation(JPA.DISCRIMINATOR_VALUE);

		discriminatorValue.setValue("foo");
		
		assertSourceContains("@DiscriminatorValue(\"foo\")");
		
		discriminatorValue.setValue(null);
		
		assertSourceDoesNotContain("@DiscriminatorValue");
	}

}
