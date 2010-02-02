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
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinTableJoiningStrategy 
	extends AbstractJavaJpaContextNode
	implements JavaJoinTableJoiningStrategy
{
	protected JavaJoinTable joinTable;
	
	
	protected AbstractJavaJoinTableJoiningStrategy(JoinTableEnabledRelationshipReference parent) {
		super(parent);
	}
	
	
	@Override
	public JoinTableEnabledRelationshipReference getParent() {
		return (JoinTableEnabledRelationshipReference) super.getParent();
	}
	
	public JoinTableEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public String getTableName() {
		return getJoinTable().getName();
	}

	public Table getDbTable(String tableName) {
		return getJoinTable().getDbTable();
	}

	public String getJoinTableDefaultName() {
		return MappingTools.buildJoinTableDefaultName(this.getRelationshipReference());
	}

	public void addStrategy() {
		if (this.joinTable == null) {
			this.joinTable = getJpaFactory().buildJavaJoinTable(this);
			addAnnotation();
			this.firePropertyChanged(JOIN_TABLE_PROPERTY, null, this.joinTable);
		}
	}
	
	public void removeStrategy() {
		if (this.joinTable != null) {
			JavaJoinTable oldJoinTable = this.joinTable;
			this.joinTable = null;
			removeAnnotation();
			this.firePropertyChanged(JOIN_TABLE_PROPERTY, oldJoinTable, null);
		}
	}
			
	public void initializeFrom(JoinTableJoiningStrategy oldStrategy) {
		JoinTable oldJoinTable = oldStrategy.getJoinTable();
		if (oldJoinTable != null) {
			this.addStrategy();
			this.getJoinTable().setSpecifiedCatalog(oldJoinTable.getSpecifiedCatalog());
			this.getJoinTable().setSpecifiedSchema(oldJoinTable.getSpecifiedSchema());
			this.getJoinTable().setSpecifiedName(oldJoinTable.getSpecifiedName());
		}
	}
	
	
	// **************** join table *********************************************
	
	public JavaJoinTable getJoinTable() {
		return this.joinTable;
	}
	
	protected void setJoinTable_(JavaJoinTable newJoinTable) {
		JavaJoinTable oldJoinTable = this.joinTable;
		this.joinTable = newJoinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, oldJoinTable, newJoinTable);
	}
	
	protected abstract JoinTableAnnotation addAnnotation();
	
	protected abstract void removeAnnotation();
		
	
	// **************** resource => context ************************************

	public void initialize() {
		JoinTableAnnotation annotation = getAnnotation();
		if (annotation.isSpecified() || getRelationshipReference().mayHaveDefaultJoinTable()) {
			this.joinTable = getJpaFactory().buildJavaJoinTable(this);
			this.joinTable.initialize(annotation);
		}
	}
	
	public void update() {
		JoinTableAnnotation annotation = getAnnotation();
		if (annotation.isSpecified() || getRelationshipReference().mayHaveDefaultJoinTable()) {
			if (this.joinTable == null) {
				setJoinTable_(getJpaFactory().buildJavaJoinTable(this));
			}
			this.joinTable.update(annotation);
		}
		else {
			if (this.joinTable != null) {
				// no annotation, so no clean up
				setJoinTable_(null);
			}
		}
	}
	
	// **************** Java completion proposals ******************************
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null && this.joinTable != null) {
			result = this.joinTable.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.joinTable != null) {
			this.joinTable.validate(messages, reporter, astRoot);
		}
	}

}
