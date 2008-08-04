/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;


public class GenericJavaJoinColumn extends AbstractJavaBaseColumn<JoinColumnAnnotation> implements JavaJoinColumn
{

	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected JoinColumnAnnotation joinColumn;
	
	public GenericJavaJoinColumn(JavaJpaContextNode parent, JavaJoinColumn.Owner owner) {
		super(parent, owner);
	}

	@Override
	protected JoinColumnAnnotation getColumnResource() {
		return this.joinColumn;
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
		this.joinColumn.setReferencedColumnName(newSpecifiedReferencedColumnName);
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

	public Table getDbReferencedColumnTable() {
		return getOwner().getDbReferencedColumnTable();
	}

	public Column getDbReferencedColumn() {
		Table table = this.getDbReferencedColumnTable();
		return (table == null) ? null : table.getColumnNamed(this.getReferencedColumnName());
	}

	@Override
	public boolean tableIsAllowed() {
		return this.getOwner().tableIsAllowed();
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return getColumnResource().referencedColumnNameTouches(pos, astRoot);
	}

	private Iterator<String> candidateReferencedColumnNames() {
		Table table = this.getOwner().getDbReferencedColumnTable();
		return (table != null) ? table.columnNames() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateReferencedColumnNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateReferencedColumnNames(), filter);
	}

	private Iterator<String> quotedCandidateReferencedColumnNames(Filter<String> filter) {
		return StringTools.quote(this.candidateReferencedColumnNames(filter));
	}

	@Override
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos, astRoot)) {
			return this.quotedCandidateReferencedColumnNames(filter);
		}
		return null;
	}

	public boolean isReferencedColumnResolved() {
		return getDbReferencedColumn() != null;
	}

	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		TextRange textRange = getColumnResource().getReferencedColumnNameTextRange(astRoot);
		return (textRange != null) ? textRange : getOwner().getValidationTextRange(astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void initialize(JoinColumnAnnotation joinColumn) {
		this.joinColumn = joinColumn;
		super.initialize(joinColumn);
		this.specifiedReferencedColumnName = joinColumn.getReferencedColumnName();
		this.defaultReferencedColumnName = this.defaultReferencedColumnName();
	}
	
	@Override
	public void update(JoinColumnAnnotation joinColumn) {
		this.joinColumn = joinColumn;
		super.update(joinColumn);
		this.setSpecifiedReferencedColumnName_(joinColumn.getReferencedColumnName());
		this.setDefaultReferencedColumnName(this.defaultReferencedColumnName());
	}
	
	@Override
	protected String defaultName() {
		RelationshipMapping relationshipMapping = getOwner().getRelationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!getOwner().getRelationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return MappingTools.buildJoinColumnDefaultName(this);
	}
	
	protected String defaultReferencedColumnName() {
		RelationshipMapping relationshipMapping = getOwner().getRelationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!getOwner().getRelationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return MappingTools.buildJoinColumnDefaultReferencedColumnName(this);
	}
	
	@Override
	protected String defaultTable() {
		RelationshipMapping relationshipMapping = getOwner().getRelationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!getOwner().getRelationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return super.defaultTable();
	}
}
