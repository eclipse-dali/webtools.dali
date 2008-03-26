/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;
import org.eclipse.jpt.core.utility.TextRange;


public class OrmCascade extends AbstractOrmJpaContextNode implements Cascade, OrmJpaContextNode
{

	protected boolean all;

	protected boolean persist;

	protected boolean merge;

	protected boolean remove;

	protected boolean refresh;

	
	protected XmlRelationshipMapping relationshipMapping;
	
	protected OrmCascade(OrmRelationshipMapping parent) {
		super(parent);
	}
	
	public void initializeFrom(Cascade oldCascade) {
		setAll(oldCascade.isAll());
		setPersist(oldCascade.isPersist());
		setMerge(oldCascade.isMerge());
		setRemove(oldCascade.isRemove());
		setRefresh(oldCascade.isRefresh());
	}

	public boolean isAll() {
		return this.all;
	}

	public void setAll(boolean newAll) {
		boolean oldAll = this.all;
		this.all = newAll;
		if (oldAll != newAll) {
			if (this.cascadeResource() != null) {
				this.cascadeResource().setCascadeAll(newAll);						
				if (this.cascadeResource().isAllFeaturesUnset()) {
					removeCascadeResource();
				}
			}
			else if (newAll != false) {
				addCascadeResource();
				cascadeResource().setCascadeAll(newAll);
			}
		}
		firePropertyChanged(ALL_PROPERTY, oldAll, newAll);
	}
	
	protected void setAll_(boolean newAll) {
		boolean oldAll = this.all;
		this.all = newAll;
		firePropertyChanged(ALL_PROPERTY, oldAll, newAll);
	}
	
	public boolean isPersist() {
		return this.persist;
	}

	public void setPersist(boolean newPersist) {
		boolean oldPersist = this.persist;
		this.persist = newPersist;
		if (oldPersist != newPersist) {
			if (this.cascadeResource() != null) {
				this.cascadeResource().setCascadePersist(newPersist);						
				if (this.cascadeResource().isAllFeaturesUnset()) {
					removeCascadeResource();
				}
			}
			else if (newPersist != false) {
				addCascadeResource();
				cascadeResource().setCascadePersist(newPersist);
			}
		}		
		firePropertyChanged(PERSIST_PROPERTY, oldPersist, newPersist);
	}
	
	protected void setPersist_(boolean newPersist) {
		boolean oldPersist = this.persist;
		this.persist = newPersist;		
		firePropertyChanged(PERSIST_PROPERTY, oldPersist, newPersist);
	}


	public boolean isMerge() {
		return this.merge;
	}

	public void setMerge(boolean newMerge) {
		boolean oldMerge = this.merge;
		this.merge = newMerge;
		if (oldMerge != newMerge) {
			if (this.cascadeResource() != null) {
				this.cascadeResource().setCascadeMerge(newMerge);						
				if (this.cascadeResource().isAllFeaturesUnset()) {
					removeCascadeResource();
				}
			}
			else if (newMerge != false) {
				addCascadeResource();
				cascadeResource().setCascadeMerge(newMerge);
			}
		}		
		firePropertyChanged(MERGE_PROPERTY, oldMerge, newMerge);
	}
	
	protected void setMerge_(boolean newMerge) {
		boolean oldMerge = this.merge;
		this.merge = newMerge;
		firePropertyChanged(MERGE_PROPERTY, oldMerge, newMerge);
	}

	public boolean isRemove() {
		return this.remove;
	}

	public void setRemove(boolean newRemove) {
		boolean oldRemove = this.remove;
		this.remove = newRemove;
		if (oldRemove != newRemove) {
			if (this.cascadeResource() != null) {
				this.cascadeResource().setCascadeRemove(newRemove);						
				if (this.cascadeResource().isAllFeaturesUnset()) {
					removeCascadeResource();
				}
			}
			else if (newRemove != false) {
				addCascadeResource();
				cascadeResource().setCascadeRemove(newRemove);
			}
		}		
		firePropertyChanged(REMOVE_PROPERTY, oldRemove, newRemove);
	}
	
	protected void setRemove_(boolean newRemove) {
		boolean oldRemove = this.remove;
		this.remove = newRemove;		
		firePropertyChanged(REMOVE_PROPERTY, oldRemove, newRemove);
	}

	public boolean isRefresh() {
		return this.refresh;
	}

	public void setRefresh(boolean newRefresh) {
		boolean oldRefresh = this.refresh;
		this.refresh = newRefresh;	
		if (oldRefresh != newRefresh) {
			if (this.cascadeResource() != null) {
				this.cascadeResource().setCascadeRefresh(newRefresh);						
				if (this.cascadeResource().isAllFeaturesUnset()) {
					removeCascadeResource();
				}
			}
			else if (newRefresh != false) {
				addCascadeResource();
				cascadeResource().setCascadeRefresh(newRefresh);
			}
		}
		firePropertyChanged(REFRESH_PROPERTY, oldRefresh, newRefresh);
	}
	
	protected void setRefresh_(boolean newRefresh) {
		boolean oldRefresh = this.refresh;
		this.refresh = newRefresh;
		firePropertyChanged(REFRESH_PROPERTY, oldRefresh, newRefresh);
	}
	

	protected CascadeType cascadeResource() {
		return this.relationshipMapping.getCascade();
	}
	
	protected void removeCascadeResource() {
		this.relationshipMapping.setCascade(null);		
	}
	
	protected void addCascadeResource() {
		this.relationshipMapping.setCascade(OrmFactory.eINSTANCE.createCascadeTypeImpl());			
	}

	public void initialize(XmlRelationshipMapping relationshipMapping) {
		this.relationshipMapping = relationshipMapping;
		CascadeType cascade = cascadeResource();
		this.all = this.all(cascade);
		this.persist = this.persist(cascade);
		this.merge = this.merge(cascade);
		this.remove = this.remove(cascade);
		this.refresh = this.refresh(cascade);
	}
	
	public void update(XmlRelationshipMapping relationshipMapping) {
		this.relationshipMapping = relationshipMapping;
		CascadeType cascade = cascadeResource();
		this.setAll_(this.all(cascade));
		this.setPersist_(this.persist(cascade));
		this.setMerge_(this.merge(cascade));
		this.setRemove_(this.remove(cascade));
		this.setRefresh_(this.refresh(cascade));		
	}

	protected boolean all(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadeAll();
	}

	protected boolean persist(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadePersist();
	}

	protected boolean merge(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadeMerge();
	}

	protected boolean remove(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadeRemove();
	}

	protected boolean refresh(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadeRefresh();
	}

	public TextRange getValidationTextRange() {
		return this.cascadeResource().getValidationTextRange();
	}
}
