/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaBaseColumn;
import org.eclipse.jpt.core.internal.context.java.JavaJoinColumnTextRangeResolver;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;


public class GenericJavaJoinColumn extends AbstractJavaBaseColumn<JoinColumnAnnotation> implements JavaJoinColumn
{

	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;
	
	public GenericJavaJoinColumn(JavaJpaContextNode parent, JavaJoinColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	public void initialize(JoinColumnAnnotation annotation) {
		super.initialize(annotation);
		this.specifiedReferencedColumnName = annotation.getReferencedColumnName();
		this.defaultReferencedColumnName = this.buildDefaultReferencedColumnName();
	}
	
	@Override
	public void update(JoinColumnAnnotation annotation) {
		super.update(annotation);
		this.setSpecifiedReferencedColumnName_(annotation.getReferencedColumnName());
		this.setDefaultReferencedColumnName(this.buildDefaultReferencedColumnName());
	}

	
	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName == null) ? this.defaultReferencedColumnName : this.specifiedReferencedColumnName;
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		this.getResourceColumn().setReferencedColumnName(newSpecifiedReferencedColumnName);
		firePropertyChanged(BaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	protected void setSpecifiedReferencedColumnName_(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		firePropertyChanged(BaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		firePropertyChanged(BaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, oldDefaultReferencedColumnName, newDefaultReferencedColumnName);
	}


	@Override
	public JavaJoinColumn.Owner getOwner() {
		return (JavaJoinColumn.Owner) super.getOwner();
	}

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}

	public Table getReferencedColumnDbTable() {
		return getOwner().getReferencedColumnDbTable();
	}

	public Column getReferencedDbColumn() {
		Table table = this.getReferencedColumnDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getReferencedColumnName());
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return getResourceColumn().referencedColumnNameTouches(pos, astRoot);
	}

	@Override
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos, astRoot)) {
			return this.getJavaCandidateReferencedColumnNames(filter).iterator();
		}
		return null;
	}

	private Iterable<String> getJavaCandidateReferencedColumnNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateReferencedColumnNames(filter));
	}

	private Iterable<String> getCandidateReferencedColumnNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateReferencedColumnNames(), filter);
	}

	private Iterable<String> getCandidateReferencedColumnNames() {
		Table table = this.getOwner().getReferencedColumnDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}

	public boolean isReferencedColumnResolved() {
		return getReferencedDbColumn() != null;
	}

	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		TextRange textRange = getResourceColumn().getReferencedColumnNameTextRange(astRoot);
		return (textRange != null) ? textRange : getOwner().getValidationTextRange(astRoot);
	}
	
	@Override
	protected String buildDefaultName() {
		return MappingTools.buildJoinColumnDefaultName(this, this.getOwner());
	}
	
	protected String buildDefaultReferencedColumnName() {
		return MappingTools.buildJoinColumnDefaultReferencedColumnName(this.getOwner());
	}

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaJoinColumnTextRangeResolver(this, astRoot);
	}
}
