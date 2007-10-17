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

public class LobImpl extends AbstractAnnotationResource<Attribute> implements Lob
{
	private static final String ANNOTATION_NAME = JPA.LOB;
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	protected LobImpl(JavaResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	
	public static class LobAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final LobAnnotationDefinition INSTANCE = new LobAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private LobAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new LobImpl(parent, (Attribute) member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
