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
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class EmbeddedIdImpl extends AbstractAnnotationResource<Attribute> implements EmbeddedId
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	protected EmbeddedIdImpl(JavaPersistentAttributeResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
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

	
	public static class EmbeddedIdAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final EmbeddedIdAnnotationDefinition INSTANCE = new EmbeddedIdAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static EmbeddedIdAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private EmbeddedIdAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaPersistentResource parent, Member member) {
			return new EmbeddedIdImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaPersistentResource parent, Member member) {
			return null;
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
