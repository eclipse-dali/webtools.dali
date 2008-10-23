/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractResourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.eclipselink.core.resource.java.ReadOnlyAnnotation;


public class ReadOnlyImpl extends AbstractResourceAnnotation<Type> implements ReadOnlyAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	protected ReadOnlyImpl(JavaResourceNode parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		//nothing to initialize
	}
	
	public void update(CompilationUnit astRoot) {
		//nothing to update
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	
	public static class ReadOnlyAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final ReadOnlyAnnotationDefinition INSTANCE = new ReadOnlyAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private ReadOnlyAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new ReadOnlyImpl(parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
