/*******************************************************************************
 *  Copyright (c) 2013 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License 2.0 
 *  which accompanies this distribution, and is available at 
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.persistence;

import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2_1.context.ConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ConverterAnnotation2_1;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.Generic2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class ClassRef2_1Tests extends Generic2_1ContextModelTestCase
{
	public ClassRef2_1Tests(String name) {
		super(name);
	}

	public void testUpdateClassName() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();

		// add class ref
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		ClassRef classRef = persistenceUnit.getSpecifiedClassRefs().iterator().next();

		// test that class names are initially equal
		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());

		// set xml class name to different name, test equality
		xmlClassRef.setJavaClass("com.bar.Foo");

		classRef = persistenceUnit.getSpecifiedClassRefs().iterator().next();
		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());

		// set class name to empty string, test equality
		xmlClassRef.setJavaClass("");

		classRef = persistenceUnit.getSpecifiedClassRefs().iterator().next();
		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());

		// set name back to non empty string, test equality
		xmlClassRef.setJavaClass("com.foo.Bar");

		classRef = persistenceUnit.getSpecifiedClassRefs().iterator().next();
		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());
	}

	public void testModifyClassName() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();

		// add class ref
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		ClassRef classRef = persistenceUnit.getSpecifiedClassRefs().iterator().next();

		// test that class names are initially equal
		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());

		// set context class name to different name, test equality
		classRef.setClassName("com.bar.Foo");

		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());

		// set class name to empty string, test equality
		classRef.setClassName("");

		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());

		// set class name to null, test equality
		classRef.setClassName(null);

		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());

		// set name back to non-null, test equality
		classRef.setClassName("com.foo.Bar");

		assertEquals(classRef.getClassName(), xmlClassRef.getJavaClass());
	}

	public void testGetJavaManagedType() throws Exception {
		createTestType();

		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();

		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass(FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);

		ClassRef classRef = getSpecifiedClassRef();

		assertEquals(FULLY_QUALIFIED_TYPE_NAME, classRef.getJavaManagedType().getName());

		//test setting to a class that does not exist in the project
		xmlClassRef.setJavaClass("com.foo.Bar");

		classRef = getSpecifiedClassRef();		
		assertNull(classRef.getJavaManagedType());

		xmlClassRef.setJavaClass(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, classRef.getJavaManagedType().getName());
		assertEquals(PersistentType.class, classRef.getJavaManagedType().getManagedTypeType());

		classRef.getJavaResourceType().addAnnotation(ConverterAnnotation2_1.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, classRef.getJavaManagedType().getName());
		assertEquals(ConverterType2_1.class, classRef.getJavaManagedType().getManagedTypeType());

		//verify both @Converter and @Entity annotation, Entity wins
		classRef.getJavaResourceType().addAnnotation(EntityAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, classRef.getJavaManagedType().getName());
		assertEquals(PersistentType.class, classRef.getJavaManagedType().getManagedTypeType());

		classRef.getJavaResourceType().removeAnnotation(EntityAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, classRef.getJavaManagedType().getName());
		assertEquals(ConverterType2_1.class, classRef.getJavaManagedType().getManagedTypeType());
	}
}
