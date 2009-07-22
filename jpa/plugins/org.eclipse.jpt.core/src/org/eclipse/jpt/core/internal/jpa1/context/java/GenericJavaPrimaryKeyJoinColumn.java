/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;


public class GenericJavaPrimaryKeyJoinColumn extends AbstractJavaNamedColumn<PrimaryKeyJoinColumnAnnotation>
	implements JavaPrimaryKeyJoinColumn
{
	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected PrimaryKeyJoinColumnAnnotation resourcePrimaryKeyJoinColumn;
	
	public GenericJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, JavaBaseJoinColumn.Owner owner) {
		super(parent, owner);
	}

	@Override
	public void initialize(PrimaryKeyJoinColumnAnnotation column) {
		this.resourcePrimaryKeyJoinColumn = column;
		super.initialize(column);
		this.specifiedReferencedColumnName = this.getResourceReferencedColumnName(column);
		this.defaultReferencedColumnName = this.buildDefaultReferencedColumnName();
	}

	//************** JavaNamedColumn implementation ***************
	@Override
	public JavaBaseJoinColumn.Owner getOwner() {
		return (JavaBaseJoinColumn.Owner) super.getOwner();
	}
	
	@Override
	protected PrimaryKeyJoinColumnAnnotation getResourceColumn() {
		return this.resourcePrimaryKeyJoinColumn;
	}
	
	//************** IAbstractJoinColumn implementation ***************
	
	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName == null) ? this.defaultReferencedColumnName : this.specifiedReferencedColumnName;
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		getResourceColumn().setReferencedColumnName(newSpecifiedReferencedColumnName);
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	protected void setSpecifiedReferencedColumnName_(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, oldDefaultReferencedColumnName, newDefaultReferencedColumnName);
	}
	
	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}

	@Override
	protected String getTableName() {
		return this.getOwner().getTypeMapping().getPrimaryTableName();
	}

	public Column getReferencedDbColumn() {
		Table table = this.getReferencedColumnDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getReferencedColumnName());
	}

	public Table getReferencedColumnDbTable() {
		return getOwner().getReferencedColumnDbTable();
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceColumn().referencedColumnNameTouches(pos, astRoot);
	}

	@Override
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos, astRoot)) {
			return this.javaCandidateReferencedColumnNames(filter);
		}
		return null;
	}

	private Iterator<String> javaCandidateReferencedColumnNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateReferencedColumnNames(filter));
	}

	private Iterator<String> candidateReferencedColumnNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateReferencedColumnNames(), filter);
	}

	private Iterator<String> candidateReferencedColumnNames() {
		Table table = this.getOwner().getReferencedColumnDbTable();
		return (table != null) ? table.sortedColumnIdentifiers() : EmptyIterator.<String> instance();
	}

	public boolean isReferencedColumnResolved() {
		return getReferencedDbColumn() != null;
	}

	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		return this.getResourceColumn().getReferencedColumnNameTextRange(astRoot);
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getResourceColumn().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getOwner().getValidationTextRange(astRoot);	
	}

	@Override
	public void update(PrimaryKeyJoinColumnAnnotation resourceColumn) {
		this.resourcePrimaryKeyJoinColumn = resourceColumn;
		super.update(resourceColumn);
		this.setSpecifiedReferencedColumnName_(this.getResourceReferencedColumnName(resourceColumn));
		this.setDefaultReferencedColumnName(this.buildDefaultReferencedColumnName());
	}
	
	protected String getResourceReferencedColumnName(PrimaryKeyJoinColumnAnnotation resourceColumn) {
		return resourceColumn.getReferencedColumnName();
	}
	
	//TODO not correct when we start supporting primaryKeyJoinColumns in 1-1 mappings
	protected String buildDefaultReferencedColumnName() {
		return buildDefaultName();
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append("=>"); //$NON-NLS-1$
		sb.append(this.getReferencedColumnName());
	}

}
