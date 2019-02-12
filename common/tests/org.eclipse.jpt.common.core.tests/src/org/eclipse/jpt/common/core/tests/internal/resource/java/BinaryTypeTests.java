/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.resource.java;

import java.io.File;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryTypeCache;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;

@SuppressWarnings("nls")
public class BinaryTypeTests
		extends JavaResourceModelTestCase {
	
	private static URL JAR_URL = BinaryTypeTests.class.getResource("binmodel.jar");
	
	private static String SUPERCLASS_CLASS_NAME = BinaryTypeTests.class.getPackage().getName() + ".binmodel.GenericSuperclass";
	
	private static String SUBCLASS_CLASS_NAME = BinaryTypeTests.class.getPackage().getName() + ".binmodel.GenericSubclass";
	
	
	public BinaryTypeTests(String name) {
		super(name);
	}
	
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		return new AnnotationDefinition[] {
				DeprecatedAnnotationDefinition.instance() };
	}
	
	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		return new NestableAnnotationDefinition[0];
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getJavaProjectTestHarness().addJar(new File(FileLocator.resolve(JAR_URL).toURI()).getAbsolutePath());
	}
	
	public void testInheritedAttributeTypes() throws Exception {
		BinaryTypeCache typeCache = new BinaryTypeCache(buildAnnotationProvider());
		JavaResourceType superclassType = 
				(JavaResourceType) typeCache.addType(this.getJavaProject().findType(SUPERCLASS_CLASS_NAME));
		JavaResourceType subclassType = 
				(JavaResourceType) typeCache.addType(this.getJavaProject().findType(SUBCLASS_CLASS_NAME));
		
		// generic field 1 -> Object, String
		JavaResourceField genericField1 = superclassType.getField("genericField1");
		assertEquals("java.lang.Object", superclassType.getAttributeTypeBinding(genericField1).getQualifiedName());
		assertEquals("java.lang.String", subclassType.getAttributeTypeBinding(genericField1).getQualifiedName());
		
		// generic field 2 -> Object, Number
		JavaResourceField genericField2 = superclassType.getField("genericField2");
		assertEquals("java.lang.Object", superclassType.getAttributeTypeBinding(genericField2).getQualifiedName());
		assertEquals("java.lang.Number", subclassType.getAttributeTypeBinding(genericField2).getQualifiedName());
		
		// generic method 1 -> Object, String
		JavaResourceMethod genericMethod1 = superclassType.getMethod("genericMethod1");
		assertEquals("java.lang.Object", superclassType.getAttributeTypeBinding(genericMethod1).getQualifiedName());
		assertEquals("java.lang.String", subclassType.getAttributeTypeBinding(genericMethod1).getQualifiedName());
		
		// generic method 1 -> Object, Number
		JavaResourceMethod genericMethod2 = superclassType.getMethod("genericMethod2");
		assertEquals("java.lang.Object", superclassType.getAttributeTypeBinding(genericMethod2).getQualifiedName());
		assertEquals("java.lang.Number", subclassType.getAttributeTypeBinding(genericMethod2).getQualifiedName());
	}
	
	public void testGeneralTypeAPI() throws Exception {
		BinaryTypeCache typeCache = new BinaryTypeCache(buildAnnotationProvider());
		JavaResourceType superclassType = 
				(JavaResourceType) typeCache.addType(this.getJavaProject().findType(SUPERCLASS_CLASS_NAME));
		JavaResourceType subclassType = 
				(JavaResourceType) typeCache.addType(this.getJavaProject().findType(SUBCLASS_CLASS_NAME));
		
		// superclass qualified name
		assertEquals("java.lang.Object", superclassType.getSuperclassQualifiedName());
		assertEquals(SUPERCLASS_CLASS_NAME, subclassType.getSuperclassQualifiedName());
		
		// has any annotated fields/methods
		assertFalse(superclassType.hasAnyAnnotatedFields());
		assertFalse(superclassType.hasAnyAnnotatedMethods());
		assertTrue(subclassType.hasAnyAnnotatedFields());
		assertTrue(subclassType.hasAnyAnnotatedMethods());
		
		// has equals/hashcode method
		assertFalse(superclassType.hasEqualsMethod());
		assertFalse(superclassType.hasHashCodeMethod());
		assertTrue(subclassType.hasEqualsMethod());
		assertTrue(subclassType.hasHashCodeMethod());
		
		// has xxx no arg constructor
		assertTrue(superclassType.hasNoArgConstructor());
		assertFalse(superclassType.hasPrivateNoArgConstructor());
		assertTrue(superclassType.hasPublicNoArgConstructor());
		assertTrue(superclassType.hasPublicOrProtectedNoArgConstructor());
		assertTrue(subclassType.hasNoArgConstructor());
		assertTrue(subclassType.hasPrivateNoArgConstructor());
		assertFalse(subclassType.hasPublicNoArgConstructor());
		assertFalse(subclassType.hasPublicOrProtectedNoArgConstructor());
		
		// is abstract
		assertTrue(superclassType.isAbstract());
		assertFalse(subclassType.isAbstract());
	}
}
