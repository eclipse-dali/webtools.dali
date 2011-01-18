/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.java.JavaVirtualJoinTable;
import org.eclipse.jpt.core.context.java.JavaVirtualJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaVirtualJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaVirtualOverrideJoinTableJoiningStrategy2_0
	extends AbstractJavaJpaContextNode
	implements JavaVirtualJoinTableJoiningStrategy
{
	protected JavaVirtualJoinTable joinTable;


	public GenericJavaVirtualOverrideJoinTableJoiningStrategy2_0(JavaVirtualJoinTableEnabledRelationshipReference parent) {
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
		JoinTable overriddenJoinTable = this.getOverriddenJoinTable();
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

	protected JoinTable getOverriddenJoinTable() {
		JoinTableJoiningStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getJoinTable();
	}

	protected JavaVirtualJoinTable buildJoinTable(JoinTable overriddenJoinTable) {
		return this.getJpaFactory().buildJavaVirtualJoinTable(this, overriddenJoinTable);
	}


	// ********** misc **********

	@Override
	public JavaVirtualJoinTableEnabledRelationshipReference getParent() {
		return (JavaVirtualJoinTableEnabledRelationshipReference) super.getParent();
	}

	public JavaVirtualJoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}

	protected JoinTableJoiningStrategy getOverriddenStrategy() {
		JoinTableEnabledRelationshipReference relationship = this.getOverriddenJoinTableRelationship();
		return (relationship == null) ? null : relationship.getJoinTableJoiningStrategy();
	}

	protected JoinTableEnabledRelationshipReference getOverriddenJoinTableRelationship() {
		RelationshipReference relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof JoinTableEnabledRelationshipReference) ? (JoinTableEnabledRelationshipReference) relationship : null;
	}

	protected RelationshipReference resolveOverriddenRelationship() {
		return this.getRelationshipReference().resolveOverriddenRelationship();
	}

	public String getTableName() {
		return this.joinTable.getName();
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationshipReference());
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}
