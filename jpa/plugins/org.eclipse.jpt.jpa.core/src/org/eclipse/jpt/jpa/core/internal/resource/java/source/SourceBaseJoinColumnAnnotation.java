/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.jpa.core.resource.java.BaseJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;

/**
 * <ul>
 * <li><code>javax.persistence.JoinColumn</code>
 * <li><code>javax.persistence.MapKeyJoinColumn</code>
 * </ul>
 */
public abstract class SourceBaseJoinColumnAnnotation
	extends SourceBaseColumnAnnotation
	implements BaseJoinColumnAnnotation
{
	private DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;
	private AnnotationElementAdapter<String> referencedColumnNameAdapter;
	private String referencedColumnName;


	protected SourceBaseJoinColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.referencedColumnNameDeclarationAdapter = this.buildReferencedColumnNameDeclarationAdapter();
		this.referencedColumnNameAdapter = this.buildReferencedColumnNameAdapter();
	}

	protected SourceBaseJoinColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new ElementAnnotationAdapter(member, daa));
	}

	protected SourceBaseJoinColumnAnnotation(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, member, idaa, new ElementIndexedAnnotationAdapter(member, idaa));
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.referencedColumnName = this.buildReferencedColumnName(astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncReferencedColumnName(this.buildReferencedColumnName(astRoot));
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

	private String buildReferencedColumnName(CompilationUnit astRoot) {
		return this.referencedColumnNameAdapter.getValue(astRoot);
	}

	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.referencedColumnNameDeclarationAdapter, astRoot);
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.referencedColumnNameDeclarationAdapter, pos, astRoot);
	}

	private DeclarationAnnotationElementAdapter<String> buildReferencedColumnNameDeclarationAdapter() {
		return this.buildStringElementAdapter(this.getReferencedColumnNameElementName());
	}

	private AnnotationElementAdapter<String> buildReferencedColumnNameAdapter() {
		return this.buildStringElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}

	protected abstract String getReferencedColumnNameElementName();


	// ********** NestableAnnotation implementation **********

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.referencedColumnName == null);
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.referencedColumnNameDeclarationAdapter = this.buildReferencedColumnNameDeclarationAdapter();
		this.referencedColumnNameAdapter = this.buildReferencedColumnNameAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);
		map.put(REFERENCED_COLUMN_NAME_PROPERTY, this.referencedColumnName);
		this.referencedColumnName = null;
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);
		this.setReferencedColumnName((String) map.get(REFERENCED_COLUMN_NAME_PROPERTY));
	}
}
