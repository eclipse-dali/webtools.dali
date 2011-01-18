/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinTableJoiningStrategy
	extends AbstractJavaJpaContextNode
	implements JavaJoinTableJoiningStrategy, Table.Owner
{
	protected JavaJoinTable joinTable;


	protected AbstractJavaJoinTableJoiningStrategy(JavaJoinTableEnabledRelationshipReference parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		if (this.joinTable != null) {
			this.joinTable.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.updateJoinTable();
	}


	// ********** join table **********

	public JavaJoinTable getJoinTable() {
		return this.joinTable;
	}

	protected void setJoinTable(JavaJoinTable joinTable) {
		JavaJoinTable old = this.joinTable;
		this.joinTable = joinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, joinTable);
	}

	protected void updateJoinTable() {
		if (this.buildsJoinTable()) {
			if (this.joinTable == null) {
				this.setJoinTable(this.buildJoinTable());
			} else {
				this.joinTable.update();
			}
		} else {
			if (this.joinTable != null) {
				this.setJoinTable(null);
			}
		}
	}

	/**
	 * The strategy can have a join table if either the table annotation is present
	 * or the [mapping] relationship supports a default join table.
	 */
	protected boolean buildsJoinTable() {
		return this.getJoinTableAnnotation().isSpecified()
			|| this.getRelationshipReference().mayHaveDefaultJoinTable();
	}

	protected JavaJoinTable buildJoinTable() {
		return this.getJpaFactory().buildJavaJoinTable(this, this);
	}


	// ********** join table annotation **********

	protected abstract JoinTableAnnotation addJoinTableAnnotation();

	protected abstract void removeJoinTableAnnotation();


	// ********** misc **********

	@Override
	public JavaJoinTableEnabledRelationshipReference getParent() {
		return (JavaJoinTableEnabledRelationshipReference) super.getParent();
	}

	public JavaJoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getMapping();
	}

	public void initializeFrom(ReadOnlyJoinTableJoiningStrategy oldStrategy) {
		ReadOnlyJoinTable oldJoinTable = oldStrategy.getJoinTable();
		if (oldJoinTable != null) {
			this.addStrategy();
			this.joinTable.initializeFrom(oldJoinTable);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinTableJoiningStrategy virtualStrategy) {
		ReadOnlyJoinTable oldJoinTable = virtualStrategy.getJoinTable();
		if (oldJoinTable != null) {
			this.addStrategy();
			this.joinTable.initializeFromVirtual(oldJoinTable);
		}
	}

	public String getTableName() {
		return (this.joinTable == null) ? null : this.joinTable.getName();
	}

	public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
		return (this.joinTable == null) ? null : this.joinTable.getDbTable();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return Tools.valuesAreDifferent(this.getTableName(), tableName);
	}

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.DOES_NOT_MATCH_JOIN_TABLE;
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationshipReference());
	}

	public void addStrategy() {
		if (this.joinTable == null) {
			this.addJoinTableAnnotation();
			this.setJoinTable(this.buildJoinTable());
		}
	}

	public void removeStrategy() {
		if (this.joinTable != null) {
			this.removeJoinTableAnnotation();
			this.setJoinTable(null);
		}
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.joinTable != null) {
			result = this.joinTable.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.joinTable != null) {
			this.joinTable.validate(messages, reporter, astRoot);
		}
	}
}
