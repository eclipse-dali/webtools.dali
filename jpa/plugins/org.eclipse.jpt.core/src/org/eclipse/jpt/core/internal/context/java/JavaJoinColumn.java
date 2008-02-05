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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.JoinColumn;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;


public class JavaJoinColumn extends AbstractJavaColumn<JoinColumn> implements IJavaJoinColumn
{

	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected JoinColumn joinColumn;
	
	public JavaJoinColumn(IJavaJpaContextNode parent, IJoinColumn.Owner owner) {
		super(parent, owner);
	}

	@Override
	protected JoinColumn columnResource() {
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
		firePropertyChanged(IAbstractJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	protected void setSpecifiedReferencedColumnName_(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		firePropertyChanged(IAbstractJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		firePropertyChanged(IAbstractJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, oldDefaultReferencedColumnName, newDefaultReferencedColumnName);
	}


	@Override
	public IJoinColumn.Owner owner() {
		return (IJoinColumn.Owner) super.owner();
	}

	public boolean isVirtual() {
		return owner().isVirtual(this);
	}

	public Table dbReferencedColumnTable() {
		return owner().dbReferencedColumnTable();
	}

	public Column dbReferencedColumn() {
		Table table = this.dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(this.getReferencedColumnName());
	}

	@Override
	public boolean tableIsAllowed() {
		return this.owner().tableIsAllowed();
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return columnResource().referencedColumnNameTouches(pos, astRoot);
	}

	private Iterator<String> candidateReferencedColumnNames() {
		Table table = this.owner().dbReferencedColumnTable();
		return (table != null) ? table.columnNames() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateReferencedColumnNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateReferencedColumnNames(), filter);
	}

	private Iterator<String> quotedCandidateReferencedColumnNames(Filter<String> filter) {
		return StringTools.quote(this.candidateReferencedColumnNames(filter));
	}

	@Override
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos, astRoot)) {
			return this.quotedCandidateReferencedColumnNames(filter);
		}
		return null;
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

	public ITextRange referencedColumnNameTextRange(CompilationUnit astRoot) {
		ITextRange textRange = columnResource().referencedColumnNameTextRange(astRoot);
		return (textRange != null) ? textRange : owner().validationTextRange(astRoot);
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void initializeFromResource(JoinColumn joinColumn) {
		this.joinColumn = joinColumn;
		super.initializeFromResource(joinColumn);
		this.specifiedReferencedColumnName = joinColumn.getReferencedColumnName();
		this.defaultReferencedColumnName = this.defaultReferencedColumnName();
	}
	
	@Override
	public void update(JoinColumn joinColumn) {
		this.joinColumn = joinColumn;
		super.update(joinColumn);
		this.setSpecifiedReferencedColumnName_(joinColumn.getReferencedColumnName());
		this.setDefaultReferencedColumnName(this.defaultReferencedColumnName());
	}
	
	@Override
	protected String defaultName() {
		if (!owner().relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return buildDefaultName();
	}
	
	protected String defaultReferencedColumnName() {
		if (!owner().relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return buildDefaultReferencedColumnName();
	}
	
	@Override
	protected String defaultTable() {
		if (!owner().relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return super.defaultTable();
	}
	
	/**
	 * return the join column's default name;
	 * which is typically &lt;attribute name&gt;_&lt;referenced column name&gt;
	 * but, if we don't have an attribute name (e.g. in a unidirectional
	 * OneToMany or ManyToMany) is
	 * &lt;target entity name&gt;_&lt;referenced column name&gt;
	 */
	// <attribute name>_<referenced column name>
	//     or
	// <target entity name>_<referenced column name>
	protected String buildDefaultName() {
		if (owner().joinColumnsSize() != 1) {
			return null;
		}
		String prefix = owner().attributeName();
		if (prefix == null) {
			prefix = targetEntityName();
		}
		if (prefix == null) {
			return null;
		}
		// TODO not sure which of these is correct...
		// (the spec implies that the referenced column is always the
		// primary key column of the target entity)
		// String targetColumn = this.targetPrimaryKeyColumnName();
		String targetColumn = getReferencedColumnName();
		if (targetColumn == null) {
			return null;
		}
		return prefix + "_" + targetColumn;
	}
	
	/**
	 * return the name of the target entity
	 */
	protected String targetEntityName() {
		IEntity targetEntity = owner().targetEntity();
		return (targetEntity == null) ? null : targetEntity.getName();
	}

	protected String buildDefaultReferencedColumnName() {
		if (owner().joinColumnsSize() != 1) {
			return null;
		}
		return this.targetPrimaryKeyColumnName();
	}
	
	/**
	 * return the name of the single primary key column of the target entity
	 */
	protected String targetPrimaryKeyColumnName() {
		IEntity targetEntity = owner().targetEntity();
		return (targetEntity == null) ? null : targetEntity.primaryKeyColumnName();
	}

}
