/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinTableRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements JavaJoinTableRelationshipStrategy, ReadOnlyTable.Owner
{
	protected JavaJoinTable joinTable;


	protected AbstractJavaJoinTableRelationshipStrategy(JavaJoinTableRelationship parent) {
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
			|| this.getRelationship().mayHaveDefaultJoinTable();
	}

	protected JavaJoinTable buildJoinTable() {
		return this.getJpaFactory().buildJavaJoinTable(this, this);
	}


	// ********** join table annotation **********

	protected abstract JoinTableAnnotation addJoinTableAnnotation();

	protected abstract void removeJoinTableAnnotation();


	// ********** misc **********

	@Override
	public JavaJoinTableRelationship getParent() {
		return (JavaJoinTableRelationship) super.getParent();
	}

	public JavaJoinTableRelationship getRelationship() {
		return this.getParent();
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public void initializeFrom(ReadOnlyJoinTableRelationshipStrategy oldStrategy) {
		ReadOnlyJoinTable oldJoinTable = oldStrategy.getJoinTable();
		if (oldJoinTable != null) {
			this.addStrategy();
			this.joinTable.initializeFrom(oldJoinTable);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinTableRelationshipStrategy virtualStrategy) {
		ReadOnlyJoinTable oldJoinTable = virtualStrategy.getJoinTable();
		if (oldJoinTable != null) {
			this.addStrategy();
			this.joinTable.initializeFromVirtual(oldJoinTable);
		}
	}

	public String getTableName() {
		return (this.joinTable == null) ? null : this.joinTable.getName();
	}

	public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
		return (this.joinTable == null) ? null : this.joinTable.getDbTable();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return Tools.valuesAreDifferent(this.getTableName(), tableName);
	}

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.DOES_NOT_MATCH_JOIN_TABLE;
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationship());
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
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.joinTable != null) {
			result = this.joinTable.getJavaCompletionProposals(pos, filter, astRoot);
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
