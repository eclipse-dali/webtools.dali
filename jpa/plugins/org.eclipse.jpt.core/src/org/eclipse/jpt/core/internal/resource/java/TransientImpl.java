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

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class TransientImpl extends AbstractAnnotationResource<Attribute> implements Transient
{
	private static final String ANNOTATION_NAME = JPA.TRANSIENT;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	protected TransientImpl(JavaPersistentAttributeResource parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void updateFromJava(@SuppressWarnings("unused") CompilationUnit astRoot) {
		//no annotation members
	}
	
	public static class TransientAnnotationDefinition implements MappingAnnotationDefinition
	{
		// singleton
		private static final TransientAnnotationDefinition INSTANCE = new TransientAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static TransientAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private TransientAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new TransientImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
		}

		public Iterator<String> correspondingAnnotationNames() {
			return EmptyIterator.instance();
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
