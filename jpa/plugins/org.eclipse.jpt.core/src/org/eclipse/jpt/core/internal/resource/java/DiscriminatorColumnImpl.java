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
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class DiscriminatorColumnImpl extends AbstractNamedColumn implements DiscriminatorColumn
{
	private static final String ANNOTATION_NAME = JPA.DISCRIMINATOR_COLUMN;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private static final DeclarationAnnotationElementAdapter<String> DISCRIMINATOR_TYPE_ADAPTER = buildDiscriminatorTypeAdapter();

	// hold this so we can get the 'length' text range
	private final DeclarationAnnotationElementAdapter<String> lengthDeclarationAdapter;

	private final AnnotationElementAdapter<String> discriminatorTypeAdapter;

	private final IntAnnotationElementAdapter lengthAdapter;

	private DiscriminatorType discriminatorType;

	private int length = -1;	
	
	protected DiscriminatorColumnImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa) {
		super(parent, member, daa,  new MemberAnnotationAdapter(member, daa));
		this.discriminatorTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, DISCRIMINATOR_TYPE_ADAPTER);
		this.lengthDeclarationAdapter = this.buildNumberElementAdapter(JPA.DISCRIMINATOR_COLUMN__LENGTH);
		this.lengthAdapter = this.buildShortCircuitIntElementAdapter(this.lengthDeclarationAdapter);
	}
	
	@Override
	protected String nameElementName() {
		return JPA.DISCRIMINATOR_COLUMN__NAME;
	}
	
	@Override
	protected String columnDefinitionElementName() {
		return JPA.DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public void moveAnnotation(int newIndex) {
		//TODO move makes no sense for DiscriminatorColumn.  maybe NestableAnnotation
		//needs to be split up and we could have IndexableAnnotation
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		DiscriminatorColumn oldColumn = (DiscriminatorColumn) oldAnnotation;
		setLength(oldColumn.getLength());
		setDiscriminatorType(oldColumn.getDiscriminatorType());
	}
	
	public DiscriminatorType getDiscriminatorType() {
		return this.discriminatorType;
	}
	
	public void setDiscriminatorType(DiscriminatorType discriminatorType) {
		this.discriminatorType = discriminatorType;
		this.discriminatorTypeAdapter.setValue(DiscriminatorType.toJavaAnnotationValue(discriminatorType));
	}
	
	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
		this.lengthAdapter.setValue(length);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setLength(this.lengthAdapter.getValue(astRoot));
		setDiscriminatorType(DiscriminatorType.fromJavaAnnotationValue(this.discriminatorTypeAdapter.getValue(astRoot)));
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildDiscriminatorTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
	}
	
	public static class DiscriminatorColumnAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final DiscriminatorColumnAnnotationDefinition INSTANCE = new DiscriminatorColumnAnnotationDefinition();


		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private DiscriminatorColumnAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new DiscriminatorColumnImpl(parent, member, DiscriminatorColumnImpl.DECLARATION_ANNOTATION_ADAPTER);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
