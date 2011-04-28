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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;

public class GenericJavaVirtualOverrideJoinTableRelationshipStrategy2_0
	extends AbstractJavaJpaContextNode
	implements JavaVirtualJoinTableRelationshipStrategy
{
	protected JavaVirtualJoinTable joinTable;


	public GenericJavaVirtualOverrideJoinTableRelationshipStrategy2_0(JavaVirtualJoinTableRelationship parent) {
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
		JoinTableRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getJoinTable();
	}

	protected JavaVirtualJoinTable buildJoinTable(JoinTable overriddenJoinTable) {
		return this.getJpaFactory().buildJavaVirtualJoinTable(this, overriddenJoinTable);
	}


	// ********** misc **********

	@Override
	public JavaVirtualJoinTableRelationship getParent() {
		return (JavaVirtualJoinTableRelationship) super.getParent();
	}

	public JavaVirtualJoinTableRelationship getRelationship() {
		return this.getParent();
	}

	protected JoinTableRelationshipStrategy getOverriddenStrategy() {
		JoinTableRelationship relationship = this.getOverriddenJoinTableRelationship();
		return (relationship == null) ? null : relationship.getJoinTableStrategy();
	}

	protected JoinTableRelationship getOverriddenJoinTableRelationship() {
		Relationship relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof JoinTableRelationship) ? (JoinTableRelationship) relationship : null;
	}

	protected Relationship resolveOverriddenRelationship() {
		return this.getRelationship().resolveOverriddenRelationship();
	}

	public String getTableName() {
		return this.joinTable.getName();
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationship());
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationship().getValidationTextRange(astRoot);
	}
}
