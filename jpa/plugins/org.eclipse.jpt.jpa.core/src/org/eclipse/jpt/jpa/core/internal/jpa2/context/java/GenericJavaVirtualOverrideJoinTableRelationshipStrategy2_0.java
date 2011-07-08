/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaVirtualOverrideRelationship2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaVirtualOverrideJoinTableRelationshipStrategy2_0
	extends AbstractJavaJpaContextNode
	implements JavaVirtualJoinTableRelationshipStrategy, ReadOnlyTable.Owner
{
	protected JavaVirtualJoinTable joinTable;


	public GenericJavaVirtualOverrideJoinTableRelationshipStrategy2_0(JavaVirtualOverrideRelationship2_0 parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateJoinTable();
	}


	// ********** join table **********

	public JavaVirtualJoinTable getJoinTable() {
		return this.joinTable;
	}

	protected void setJoinTable(JavaVirtualJoinTable joinTable) {
		JavaVirtualJoinTable old = this.joinTable;
		this.joinTable = joinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, joinTable);
	}

	protected void updateJoinTable() {
		ReadOnlyJoinTable overriddenJoinTable = this.getOverriddenJoinTable();
		if (overriddenJoinTable == null) {
			if (this.joinTable != null) {
				this.setJoinTable(null);
			}
		} else {
			if ((this.joinTable != null) && (this.joinTable.getOverriddenTable() == overriddenJoinTable)) {
				this.joinTable.update();
			} else {
				this.setJoinTable(this.buildJoinTable(overriddenJoinTable));
			}
		}
	}

	protected ReadOnlyJoinTable getOverriddenJoinTable() {
		ReadOnlyJoinTableRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getJoinTable();
	}

	protected JavaVirtualJoinTable buildJoinTable(ReadOnlyJoinTable overriddenJoinTable) {
		return this.getJpaFactory().buildJavaVirtualJoinTable(this, this, overriddenJoinTable);
	}


	// ********** misc **********

	@Override
	public JavaVirtualOverrideRelationship2_0 getParent() {
		return (JavaVirtualOverrideRelationship2_0) super.getParent();
	}

	public JavaVirtualOverrideRelationship2_0 getRelationship() {
		return this.getParent();
	}

	protected ReadOnlyJoinTableRelationshipStrategy getOverriddenStrategy() {
		ReadOnlyJoinTableRelationship relationship = this.getOverriddenJoinTableRelationship();
		return (relationship == null) ? null : relationship.getJoinTableStrategy();
	}

	protected ReadOnlyJoinTableRelationship getOverriddenJoinTableRelationship() {
		ReadOnlyRelationship relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof ReadOnlyJoinTableRelationship) ? (ReadOnlyJoinTableRelationship) relationship : null;
	}

	protected ReadOnlyRelationship resolveOverriddenRelationship() {
		return this.getRelationship().resolveOverriddenRelationship();
	}

	public String getTableName() {
		return this.joinTable.getName();
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationship());
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.joinTable != null) {
			this.joinTable.validate(messages, reporter, astRoot);
		}
	}

	public boolean validatesAgainstDatabase() {
		return this.getRelationship().getTypeMapping().validatesAgainstDatabase();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationship().getValidationTextRange(astRoot);
	}

	public JptValidator buildTableValidator(ReadOnlyTable table, TableTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableValidator((ReadOnlyJoinTable) table, textRangeResolver);
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableJoinColumnValidator(column, owner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getRelationship().buildJoinTableInverseJoinColumnValidator(column, owner, textRangeResolver);
	}
}
