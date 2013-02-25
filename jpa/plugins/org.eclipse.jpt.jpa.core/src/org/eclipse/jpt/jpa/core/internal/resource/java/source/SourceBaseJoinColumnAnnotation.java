/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.resource.java.BaseJoinColumnAnnotation;

/**
 * <code><ul>
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * </ul></code>
 */
public abstract class SourceBaseJoinColumnAnnotation
	extends SourceBaseColumnAnnotation
	implements BaseJoinColumnAnnotation
{
	private DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;
	private AnnotationElementAdapter<String> referencedColumnNameAdapter;
	private String referencedColumnName;
	private TextRange referencedColumnNameTextRange;


	protected SourceBaseJoinColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.referencedColumnNameDeclarationAdapter = this.buildReferencedColumnNameDeclarationAdapter();
		this.referencedColumnNameAdapter = this.buildReferencedColumnNameAdapter();
	}

	protected SourceBaseJoinColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	protected SourceBaseJoinColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.referencedColumnName = this.buildReferencedColumnName(astAnnotation);
		this.referencedColumnNameTextRange = this.buildReferencedColumnNameTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncReferencedColumnName(this.buildReferencedColumnName(astAnnotation));
		this.referencedColumnNameTextRange = this.buildReferencedColumnNameTextRange(astAnnotation);
	}


	//************ BaseJoinColumnAnnotation implementation ***************

	// referenced column name
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		if (this.attributeValueHasChanged(this.referencedColumnName, referencedColumnName)) {
			this.referencedColumnName = referencedColumnName;
			this.referencedColumnNameAdapter.setValue(referencedColumnName);
		}
	}

	private void syncReferencedColumnName(String astReferencedColumnName) {
		String old = this.referencedColumnName;
		this.referencedColumnName = astReferencedColumnName;
		this.firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, old, astReferencedColumnName);
	}

	private String buildReferencedColumnName(Annotation astAnnotation) {
		return this.referencedColumnNameAdapter.getValue(astAnnotation);
	}

	public TextRange getReferencedColumnNameTextRange() {
		return this.referencedColumnNameTextRange;
	}

	private TextRange buildReferencedColumnNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.referencedColumnNameDeclarationAdapter, astAnnotation);
	}

	public boolean referencedColumnNameTouches(int pos) {
		return this.textRangeTouches(this.referencedColumnNameTextRange, pos);
	}

	private DeclarationAnnotationElementAdapter<String> buildReferencedColumnNameDeclarationAdapter() {
		return this.buildStringElementAdapter(this.getReferencedColumnNameElementName());
	}

	private AnnotationElementAdapter<String> buildReferencedColumnNameAdapter() {
		return this.buildStringElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}

	protected abstract String getReferencedColumnNameElementName();


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.referencedColumnName == null);
	}
}
