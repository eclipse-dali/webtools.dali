/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.resource.java;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;


@SuppressWarnings("nls")
public class SourceAttributeTests
		extends JavaResourceModelTestCase {
	
	private static String TEST_CLASS_NAME = "TestClass";
	
	
	public SourceAttributeTests(String name) {
		super(name);
	}
	
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		return new AnnotationDefinition[0];
	}
	
	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		return new NestableAnnotationDefinition[0];
	}
	
	
	private ICompilationUnit createTestClassWithVariousAttributes() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import java.util.List;").append(CR);
				sb.append(CR);
				sb.append("public class ").append(TEST_CLASS_NAME).append("<T extends Number> ").append("{").append(CR);
				sb.append("    public String string;").append(CR);
				sb.append("    public List<String> stringList;").append(CR);
				sb.append("    public String[] stringArray;").append(CR);
				sb.append("    public String[][] stringDoubleArray;").append(CR);
				sb.append("    public T generic;").append(CR);
				sb.append("    public List<T> genericList;").append(CR);
				sb.append("    public T[] genericArray;").append(CR);
				sb.append("    public List<?> wildcardList;").append(CR);
				sb.append("}").append(CR);
			}
		};
		return this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, TEST_CLASS_NAME + ".java", sourceWriter);
	}
	
	
	public void testAttributeTypes() throws Exception {
		ICompilationUnit testClassCU = createTestClassWithVariousAttributes();
		JavaResourceType testClass = buildJavaResourceType(testClassCU);
		
		// String string
		JavaResourceField att = getField(testClass, 0);
		assertEquals("string", att.getName());
		assertEquals("java.lang.String", att.getTypeBinding().getQualifiedName());
		assertEquals(false, att.getTypeBinding().isArray());
		assertEquals(null, att.getTypeBinding().getArrayComponentTypeName());
		assertEquals(0, att.getTypeBinding().getArrayDimensionality());
		assertEquals(0, att.getTypeBinding().getTypeArgumentNamesSize());
		
		// List<String> stringList
		att = getField(testClass, 1);
		assertEquals("stringList", att.getName());
		assertEquals("java.util.List", att.getTypeBinding().getQualifiedName());
		assertEquals(false, att.getTypeBinding().isArray());
		assertEquals(null, att.getTypeBinding().getArrayComponentTypeName());
		assertEquals(0, att.getTypeBinding().getArrayDimensionality());
		assertEquals(1, att.getTypeBinding().getTypeArgumentNamesSize());
		assertEquals("java.lang.String", att.getTypeBinding().getTypeArgumentName(0));
		
		// String[] stringArray
		att = getField(testClass, 2);
		assertEquals("stringArray", att.getName());
		assertEquals("java.lang.String[]", att.getTypeBinding().getQualifiedName());
		assertEquals(true, att.getTypeBinding().isArray());
		assertEquals("java.lang.String", att.getTypeBinding().getArrayComponentTypeName());
		assertEquals(1, att.getTypeBinding().getArrayDimensionality());
		assertEquals(0, att.getTypeBinding().getTypeArgumentNamesSize());
		
		// String[] stringDoubleArray
		att = getField(testClass, 3);
		assertEquals("stringDoubleArray", att.getName());
		assertEquals("java.lang.String[][]", att.getTypeBinding().getQualifiedName());
		assertEquals(true, att.getTypeBinding().isArray());
		assertEquals("java.lang.String", att.getTypeBinding().getArrayComponentTypeName());
		assertEquals(2, att.getTypeBinding().getArrayDimensionality());
		assertEquals(0, att.getTypeBinding().getTypeArgumentNamesSize());
		
		// T generic
		att = getField(testClass, 4);
		assertEquals("generic", att.getName());
		assertEquals("java.lang.Number", att.getTypeBinding().getQualifiedName());
		assertEquals(false, att.getTypeBinding().isArray());
		assertEquals(null, att.getTypeBinding().getArrayComponentTypeName());
		assertEquals(0, att.getTypeBinding().getArrayDimensionality());
		assertEquals(0, att.getTypeBinding().getTypeArgumentNamesSize());
		
		// List<T> genericList
		att = getField(testClass, 5);
		assertEquals("genericList", att.getName());
		assertEquals("java.util.List", att.getTypeBinding().getQualifiedName());
		assertEquals(false, att.getTypeBinding().isArray());
		assertEquals(null, att.getTypeBinding().getArrayComponentTypeName());
		assertEquals(0, att.getTypeBinding().getArrayDimensionality());
		assertEquals(1, att.getTypeBinding().getTypeArgumentNamesSize());
		assertEquals("java.lang.Number", att.getTypeBinding().getTypeArgumentName(0));
		
		// T[] genericArray
		att = getField(testClass, 6);
		assertEquals("genericArray", att.getName());
		assertEquals("java.lang.Number[]", att.getTypeBinding().getQualifiedName());
		assertEquals(true, att.getTypeBinding().isArray());
		assertEquals("java.lang.Number", att.getTypeBinding().getArrayComponentTypeName());
		assertEquals(1, att.getTypeBinding().getArrayDimensionality());
		assertEquals(0, att.getTypeBinding().getTypeArgumentNamesSize());
		
		// List<?> wildcardList
		att = getField(testClass, 7);
		assertEquals("wildcardList", att.getName());
		assertEquals("java.util.List", att.getTypeBinding().getQualifiedName());
		assertEquals(false, att.getTypeBinding().isArray());
		assertEquals(null, att.getTypeBinding().getArrayComponentTypeName());
		assertEquals(0, att.getTypeBinding().getArrayDimensionality());
		assertEquals(1, att.getTypeBinding().getTypeArgumentNamesSize());
		assertEquals("java.lang.Object", att.getTypeBinding().getTypeArgumentName(0));
	}
}
