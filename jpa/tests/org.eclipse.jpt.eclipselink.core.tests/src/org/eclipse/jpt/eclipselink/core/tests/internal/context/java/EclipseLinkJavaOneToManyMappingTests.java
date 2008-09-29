/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.PrivateOwnedAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EclipseLinkJavaOneToManyMappingTests extends EclipseLinkJavaContextModelTestCase
{

	private void createPrivateOwnedAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "PrivateOwned", "");		
	}
	
	private ICompilationUnit createTestEntityWithPrivateOwnedOneToMany() throws Exception {
		createPrivateOwnedAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, EclipseLinkJPA.PRIVATE_OWNED);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany").append(CR);
				sb.append("@PrivateOwned").append(CR);
			}
		});
	}

	public EclipseLinkJavaOneToManyMappingTests(String name) {
		super(name);
	}


	public void testGetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(true, oneToManyMapping.getPrivateOwned());
	}

	public void testSetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(true, oneToManyMapping.getPrivateOwned());
		
		oneToManyMapping.setPrivateOwned(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(false, oneToManyMapping.getPrivateOwned());

		oneToManyMapping.setPrivateOwned(true);
		assertNotNull(attributeResource.getAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(true, oneToManyMapping.getPrivateOwned());
	}
	
	public void testPrivateOwnedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(true, oneToManyMapping.getPrivateOwned());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, oneToManyMapping.getPrivateOwned());
		
		attributeResource.addAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME);
		assertEquals(true, oneToManyMapping.getPrivateOwned());
	}
}
