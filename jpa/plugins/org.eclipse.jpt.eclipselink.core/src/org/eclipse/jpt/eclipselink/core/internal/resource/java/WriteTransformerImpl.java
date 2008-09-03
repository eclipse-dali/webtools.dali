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
import org.eclipse.jpt.core.internal.resource.java.ColumnImpl;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.WriteTransformerAnnotation;


public class WriteTransformerImpl extends AbstractResourceAnnotation<Attribute> implements WriteTransformerAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> transformerClassAdapter;
	private final AnnotationElementAdapter<String> methodAdapter;
	private final MemberAnnotationAdapter columnAdapter;

	private static final DeclarationAnnotationElementAdapter<String> TRANSFORMER_CLASS_ADAPTER = buildTransformerClassAdapter();
	private static final DeclarationAnnotationElementAdapter<String> METHOD_ADAPTER = buildMethodAdapter();

	private String transformerClass;
	private String method;
	private ColumnImpl column;
	
	
	protected WriteTransformerImpl(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.transformerClassAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, TRANSFORMER_CLASS_ADAPTER);
		this.methodAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, METHOD_ADAPTER);
		this.columnAdapter = new MemberAnnotationAdapter(getMember(), buildColumnAnnotationAdapter(getDeclarationAnnotationAdapter()));
	}

	public void initialize(CompilationUnit astRoot) {
		this.transformerClass = this.transformerClass(astRoot);
		this.method = this.method(astRoot);
		if (this.columnAdapter.getAnnotation(astRoot) != null) {
			this.column = createColumn(this, getMember(), getDeclarationAnnotationAdapter());
			this.column.initialize(astRoot);
		}
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** WriteTransformer implementation ****************
	public String getTransformerClass() {
		return this.transformerClass;
	}
	
	public void setTransformerClass(String newTransformerClass) {
		if (attributeValueHasNotChanged(this.transformerClass, newTransformerClass)) {
			return;
		}
		String oldTransformerClass = this.transformerClass;
		this.transformerClass = newTransformerClass;
		this.transformerClassAdapter.setValue(newTransformerClass);
		firePropertyChanged(TRANSFORMER_CLASS_PROPERTY, oldTransformerClass, newTransformerClass);		
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public void setMethod(String newMethod) {
		if (attributeValueHasNotChanged(this.method, newMethod)) {
			return;
		}
		String oldMethod = this.method;
		this.method = newMethod;
		this.methodAdapter.setValue(newMethod);
		firePropertyChanged(METHOD_PROPERTY, oldMethod, newMethod);		
	}

	public ColumnAnnotation getNonNullColumn() {
		return (getColumn() != null) ? getColumn() : new NullWriteTransformerColumn(this);
	}
	
	public ColumnAnnotation getColumn() {
		return this.column;
	}
	
	public ColumnAnnotation addColumn() {
		ColumnImpl column = createColumn(this, getMember(), getDeclarationAnnotationAdapter());
		column.newAnnotation();
		setColumn(column);
		return column;
	}

	public void removeColumn() {
		this.column.removeAnnotation();
		setColumn(null);
	}
	
	protected void setColumn(ColumnImpl newColumn) {
		ColumnImpl oldColumn = this.column;
		this.column = newColumn;
		firePropertyChanged(WriteTransformerAnnotation.COLUMN_PROPERTY, oldColumn, newColumn);
	}

	
	public TextRange getTransformerClassTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(TRANSFORMER_CLASS_ADAPTER, astRoot);
	}
	
	public TextRange getMethodTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(METHOD_ADAPTER, astRoot);
	}
	
	public TextRange getColumnTextRange(CompilationUnit astRoot) {
		if (this.column != null) {
			return this.column.getTextRange(astRoot);
		}
		return getTextRange(astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setTransformerClass(this.transformerClass(astRoot));
		this.setMethod(this.method(astRoot));
		if (this.columnAdapter.getAnnotation(astRoot) == null) {
			this.setColumn(null);
		}
		else {
			if (getColumn() != null) {
				getColumn().update(astRoot);
			}
			else {
				ColumnImpl column = createColumn(this, getMember(), getDeclarationAnnotationAdapter());
				column.initialize(astRoot);
				this.setColumn(column);
			}
		}
	}
	
	protected String transformerClass(CompilationUnit astRoot) {
		return this.transformerClassAdapter.getValue(astRoot);
	}
	
	protected String method(CompilationUnit astRoot) {
		return this.methodAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildTransformerClassAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.WRITE_TRANSFORMER__TRANSFORMER_CLASS, false, SimpleTypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildMethodAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.WRITE_TRANSFORMER__METHOD, false);
	}
	
	private static DeclarationAnnotationAdapter buildColumnAnnotationAdapter(DeclarationAnnotationAdapter writeTransformerAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(writeTransformerAnnotationAdapter, EclipseLinkJPA.WRITE_TRANSFORMER__COLUMN, JPA.COLUMN, false);
	}

	private static ColumnImpl createColumn(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter writeTransformerAnnotationAdapter) {
		return new ColumnImpl(parent, member, buildColumnAnnotationAdapter(writeTransformerAnnotationAdapter));
	}

	public static class WriteTransformerAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final WriteTransformerAnnotationDefinition INSTANCE = new WriteTransformerAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static WriteTransformerAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private WriteTransformerAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new WriteTransformerImpl((JavaResourcePersistentAttribute) parent, (Attribute) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
