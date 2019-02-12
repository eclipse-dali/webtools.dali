/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class EmbeddableTests extends JpaJavaResourceModelTestCase {

	public EmbeddableTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEmbeddable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable");
			}
		});
	}
	
	private ICompilationUnit createTestEmbeddableAndEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.EMBEDDABLE, JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append("@Embeddable");
			}
		});
	}

	public void testEmbeddable() throws Exception {
		ICompilationUnit cu = this.createTestEmbeddable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		JavaResourceModel mappingAnnotation = resourceType.getAnnotation(EmbeddableAnnotation.ANNOTATION_NAME);
		assertTrue(mappingAnnotation instanceof EmbeddableAnnotation);
	}
	
	public void testEmbeddableAndEntity() throws Exception {
		ICompilationUnit cu = this.createTestEmbeddableAndEntity();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		JavaResourceModel mappingAnnotation = resourceType.getAnnotation(EmbeddableAnnotation.ANNOTATION_NAME);
		assertTrue(mappingAnnotation instanceof EmbeddableAnnotation);
		
		EntityAnnotation entity = (EntityAnnotation) resourceType.getAnnotation(JPA.ENTITY);
		assertNotNull(entity);
	}

}
