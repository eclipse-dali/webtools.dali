/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinTableRelationshipStrategy<P extends JavaJoinTableRelationship>
	extends AbstractJavaContextModel<P>
	implements JavaSpecifiedJoinTableRelationshipStrategy, JavaSpecifiedJoinTable.ParentAdapter
{
	protected JavaSpecifiedJoinTable joinTable;


	protected AbstractJavaJoinTableRelationshipStrategy(P parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		if (this.joinTable != null) {
			this.joinTable.synchronizeWithResourceModel(monitor);
		}
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateJoinTable(monitor);
	}


	// ********** join table **********

	public JavaSpecifiedJoinTable getJoinTable() {
		return this.joinTable;
	}

	protected void setJoinTable(JavaSpecifiedJoinTable joinTable) {
		JavaSpecifiedJoinTable old = this.joinTable;
		this.joinTable = joinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, joinTable);
	}

	protected void updateJoinTable(IProgressMonitor monitor) {
		if (this.buildsJoinTable()) {
			if (this.joinTable == null) {
				this.setJoinTable(this.buildJoinTable());
			} else {
				this.joinTable.update(monitor);
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

	protected JavaSpecifiedJoinTable buildJoinTable() {
		return this.getJpaFactory().buildJavaJoinTable(this);
	}


	// ********** join table annotation **********

	protected abstract JoinTableAnnotation addJoinTableAnnotation();

	protected abstract void removeJoinTableAnnotation();


	// ********** misc **********

	public JavaJoinTableRelationship getRelationship() {
		return this.parent;
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public void initializeFrom(VirtualJoinTableRelationshipStrategy virtualStrategy) {
		VirtualJoinTable oldTable = virtualStrategy.getJoinTable();
		if (oldTable != null) {
			this.addStrategy();
			this.joinTable.initializeFrom(oldTable);
		}
	}

	public String getTableName() {
		return (this.joinTable == null) ? null : this.joinTable.getName();
	}

	public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
		return (this.joinTable == null) ? null : this.joinTable.getDbTable();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return ObjectTools.notEquals(this.getTableName(), tableName);
	}

	public String getColumnTableNotValidDescription() {
		return JptJpaCoreValidationArgumentMessages.DOES_NOT_MATCH_JOIN_TABLE;
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
			if (this.getJoinTableAnnotation().isSpecified()) {
				this.removeJoinTableAnnotation();
			}
			this.setJoinTable(null);
		}
	}

	public JavaSpecifiedJoinTableRelationshipStrategy getTableParent() {
		return this;
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.joinTable != null) {
			result = this.joinTable.getCompletionProposals(pos);
		}
		return result;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.joinTable != null) {
			this.joinTable.validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

}
