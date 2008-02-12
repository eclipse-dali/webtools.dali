/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;


public class EmbeddableImpl extends AbstractAnnotationResource<Type> implements Embeddable
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	protected EmbeddableImpl(JavaPersistentTypeResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		//nothing to initialize	
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void updateFromJava(CompilationUnit astRoot) {
		//no annotation members
	}

	
	public static class EmbeddableAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final EmbeddableAnnotationDefinition INSTANCE = new EmbeddableAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static EmbeddableAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private EmbeddableAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaPersistentResource parent, Member member) {
			return new EmbeddableImpl((JavaPersistentTypeResource) parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaPersistentResource parent, Member member) {
			return null;
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
