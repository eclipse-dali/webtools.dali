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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.ICascade;
import org.eclipse.jpt.core.internal.resource.java.RelationshipMapping;

public class JavaCascade extends JavaContextModel implements ICascade
{
	protected boolean all;

	protected boolean persist;

	protected boolean merge;

	protected boolean remove;

	protected boolean refresh;

	protected RelationshipMapping relationshipMapping;

	protected JavaCascade(IJavaRelationshipMapping parent) {
		super(parent);
	}

	public boolean isAll() {
		return this.all;
	}

	public void setAll(boolean newAll) {
		boolean oldAll = this.all;
		this.all = newAll;
		this.relationshipMapping.setCascadeAll(newAll);
		firePropertyChanged(ICascade.ALL_PROPERTY, oldAll, newAll);
	}

	public boolean isPersist() {
		return this.persist;
	}

	public void setPersist(boolean newPersist) {
		boolean oldPersist = this.persist;
		this.persist = newPersist;
		this.relationshipMapping.setCascadePersist(newPersist);
		firePropertyChanged(ICascade.PERSIST_PROPERTY, oldPersist, newPersist);
	}

	public boolean isMerge() {
		return this.merge;
	}

	public void setMerge(boolean newMerge) {
		boolean oldMerge = this.merge;
		this.merge = newMerge;
		this.relationshipMapping.setCascadeMerge(newMerge);
		firePropertyChanged(ICascade.MERGE_PROPERTY, oldMerge, newMerge);
	}

	public boolean isRemove() {
		return this.remove;
	}

	public void setRemove(boolean newRemove) {
		boolean oldRemove = this.remove;
		this.remove = newRemove;
		this.relationshipMapping.setCascadeRemove(newRemove);
		firePropertyChanged(ICascade.REMOVE_PROPERTY, oldRemove, newRemove);
	}

	public boolean isRefresh() {
		return this.refresh;
	}

	public void setRefresh(boolean newRefresh) {
		boolean oldRefresh = this.refresh;
		this.refresh = newRefresh;
		this.relationshipMapping.setCascadeRefresh(newRefresh);
		firePropertyChanged(ICascade.REFRESH_PROPERTY, oldRefresh, newRefresh);
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		return this.relationshipMapping.cascadeTextRange(astRoot);
	}
	
	public void initialize(RelationshipMapping relationshipMapping) {
		this.relationshipMapping = relationshipMapping;
		this.all = relationshipMapping.isCascadeAll();
		this.persist = relationshipMapping.isCascadePersist();
		this.merge = relationshipMapping.isCascadeMerge();
		this.remove = relationshipMapping.isCascadeRemove();
		this.refresh = relationshipMapping.isCascadeRefresh();
	}
	
	public void update(RelationshipMapping relationshipMapping) {
		this.relationshipMapping = relationshipMapping;
		this.setAll(relationshipMapping.isCascadeAll());
		this.setPersist(relationshipMapping.isCascadePersist());
		this.setMerge(relationshipMapping.isCascadeMerge());
		this.setRemove(relationshipMapping.isCascadeRemove());
		this.setRefresh(relationshipMapping.isCascadeRefresh());
	}
}
