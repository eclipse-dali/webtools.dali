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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.BasicCollectionAnnotation;


public class BasicCollectionImpl extends AbstractResourceAnnotation<Attribute> implements BasicCollectionAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	
	protected BasicCollectionImpl(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void update(CompilationUnit astRoot) {
	}
	
	public static class BasicCollectionAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final BasicCollectionAnnotationDefinition INSTANCE = new BasicCollectionAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static BasicCollectionAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure single instance.
		 */
		private BasicCollectionAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new BasicCollectionImpl((JavaResourcePersistentAttribute) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
