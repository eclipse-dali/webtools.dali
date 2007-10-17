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
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.resource.java.TemporalType;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class TemporalTests extends AnnotationTestCase {

	public TemporalTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	private void createEnum(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	private IType createTestTemporal() throws Exception {
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");
		this.createEnum("TemporalType", "DATE, TIME, TIMESTAMP");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TEMPORAL);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@Temporal");
			}
		});
	}
	
	private IType createTestTemporalWithValue() throws Exception {
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");
		this.createEnum("TemporalType", "DATE, TIME, TIMESTAMP");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@Temporal(TemporalType.DATE)");
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

	public void testTemporal() throws Exception {
		IType testType = this.createTestTemporal();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		Temporal temporal = (Temporal) attributeResource.annotation(JPA.TEMPORAL);
		assertNotNull(temporal);
	}
	
	public void testGetValue() throws Exception {
		IType testType = this.createTestTemporalWithValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		Temporal temporal = (Temporal) attributeResource.annotation(JPA.TEMPORAL);
		assertEquals(TemporalType.DATE, temporal.getValue());
	}
	
	public void testSetValue() throws Exception {
		IType testType = this.createTestTemporal();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();

		Temporal temporal = (Temporal) attributeResource.annotation(JPA.TEMPORAL);

		temporal.setValue(TemporalType.TIME);
		
		assertSourceContains("@Temporal(TIME)");
		
		temporal.setValue(null);
		
		assertSourceDoesNotContain("@Temporal");
	}

}
