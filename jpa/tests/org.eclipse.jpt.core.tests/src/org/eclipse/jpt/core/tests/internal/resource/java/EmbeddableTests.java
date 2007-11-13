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
import org.eclipse.jpt.core.internal.resource.java.Embeddable;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EmbeddableTests extends JavaResourceModelTestCase {

	public EmbeddableTests(String name) {
		super(name);
	}

	private IType createTestEmbeddable() throws Exception {
		this.createAnnotationAndMembers("Embeddable", "");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable");
			}
		});
	}
	
	private IType createTestEmbeddableAndEntity() throws Exception {
		this.createAnnotationAndMembers("Embeddable", "");
		this.createAnnotationAndMembers("Entity", "");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE, JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append("@Embeddable");
			}
		});
	}

	public void testEmbeddable() throws Exception {
		IType testType = this.createTestEmbeddable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		JavaResource mappingAnnotation = typeResource.mappingAnnotation();
		assertTrue(mappingAnnotation instanceof Embeddable);
	}
	
	public void testEmbeddableAndEntity() throws Exception {
		IType testType = this.createTestEmbeddableAndEntity();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		JavaResource mappingAnnotation = typeResource.mappingAnnotation();
		assertTrue(mappingAnnotation instanceof Embeddable);
		
		Entity entity = (Entity) typeResource.mappingAnnotation(JPA.ENTITY);
		assertNotNull(entity);
	}

}
