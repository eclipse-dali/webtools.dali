/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class JavaCascade extends AbstractJavaJpaContextNode implements Cascade
{
	protected boolean all;

	protected boolean persist;

	protected boolean merge;

	protected boolean remove;

	protected boolean refresh;

	protected RelationshipMappingAnnotation relationshipMapping;

	protected JavaCascade(JavaRelationshipMapping parent) {
		super(parent);
	}

	public boolean isAll() {
		return this.all;
	}

	public void setAll(boolean newAll) {
		boolean oldAll = this.all;
		this.all = newAll;
		this.relationshipMapping.setCascadeAll(newAll);
		firePropertyChanged(Cascade.ALL_PROPERTY, oldAll, newAll);
	}

	public boolean isPersist() {
		return this.persist;
	}

	public void setPersist(boolean newPersist) {
		boolean oldPersist = this.persist;
		this.persist = newPersist;
		this.relationshipMapping.setCascadePersist(newPersist);
		firePropertyChanged(Cascade.PERSIST_PROPERTY, oldPersist, newPersist);
	}

	public boolean isMerge() {
		return this.merge;
	}

	public void setMerge(boolean newMerge) {
		boolean oldMerge = this.merge;
		this.merge = newMerge;
		this.relationshipMapping.setCascadeMerge(newMerge);
		firePropertyChanged(Cascade.MERGE_PROPERTY, oldMerge, newMerge);
	}

	public boolean isRemove() {
		return this.remove;
	}

	public void setRemove(boolean newRemove) {
		boolean oldRemove = this.remove;
		this.remove = newRemove;
		this.relationshipMapping.setCascadeRemove(newRemove);
		firePropertyChanged(Cascade.REMOVE_PROPERTY, oldRemove, newRemove);
	}

	public boolean isRefresh() {
		return this.refresh;
	}

	public void setRefresh(boolean newRefresh) {
		boolean oldRefresh = this.refresh;
		this.refresh = newRefresh;
		this.relationshipMapping.setCascadeRefresh(newRefresh);
		firePropertyChanged(Cascade.REFRESH_PROPERTY, oldRefresh, newRefresh);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.relationshipMapping.cascadeTextRange(astRoot);
	}
	
	public void initialize(RelationshipMappingAnnotation relationshipMapping) {
		this.relationshipMapping = relationshipMapping;
		this.all = relationshipMapping.isCascadeAll();
		this.persist = relationshipMapping.isCascadePersist();
		this.merge = relationshipMapping.isCascadeMerge();
		this.remove = relationshipMapping.isCascadeRemove();
		this.refresh = relationshipMapping.isCascadeRefresh();
	}
	
	public void update(RelationshipMappingAnnotation relationshipMapping) {
		this.relationshipMapping = relationshipMapping;
		this.setAll(relationshipMapping.isCascadeAll());
		this.setPersist(relationshipMapping.isCascadePersist());
		this.setMerge(relationshipMapping.isCascadeMerge());
		this.setRemove(relationshipMapping.isCascadeRemove());
		this.setRefresh(relationshipMapping.isCascadeRefresh());
	}
}
