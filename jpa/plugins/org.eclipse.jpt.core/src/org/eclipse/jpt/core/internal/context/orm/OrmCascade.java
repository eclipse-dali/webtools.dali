/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;
import org.eclipse.jpt.core.utility.TextRange;


public class OrmCascade extends AbstractXmlContextNode implements Cascade
{
	protected boolean all;

	protected boolean persist;

	protected boolean merge;

	protected boolean remove;

	protected boolean refresh;

	
	protected final XmlRelationshipMapping relationshipMapping;
	
	protected OrmCascade(OrmRelationshipMapping parent, XmlRelationshipMapping relationshipMapping) {
		super(parent);
		this.relationshipMapping = relationshipMapping;
		CascadeType cascade = getResourceCascade();
		this.all = this.getResourceAll(cascade);
		this.persist = this.getResourcePersist(cascade);
		this.merge = this.getResourceMerge(cascade);
		this.remove = this.getResourceRemove(cascade);
		this.refresh = this.getResourceRefresh(cascade);
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
			if (this.getResourceCascade() != null) {
				this.getResourceCascade().setCascadeAll(newAll);						
				if (this.getResourceCascade().isUnset()) {
					removeResourceCascade();
				}
			}
			else if (newAll != false) {
				addResourceCascade();
				getResourceCascade().setCascadeAll(newAll);
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
			if (this.getResourceCascade() != null) {
				this.getResourceCascade().setCascadePersist(newPersist);						
				if (this.getResourceCascade().isUnset()) {
					removeResourceCascade();
				}
			}
			else if (newPersist != false) {
				addResourceCascade();
				getResourceCascade().setCascadePersist(newPersist);
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
			if (this.getResourceCascade() != null) {
				this.getResourceCascade().setCascadeMerge(newMerge);						
				if (this.getResourceCascade().isUnset()) {
					removeResourceCascade();
				}
			}
			else if (newMerge != false) {
				addResourceCascade();
				getResourceCascade().setCascadeMerge(newMerge);
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
			if (this.getResourceCascade() != null) {
				this.getResourceCascade().setCascadeRemove(newRemove);						
				if (this.getResourceCascade().isUnset()) {
					removeResourceCascade();
				}
			}
			else if (newRemove != false) {
				addResourceCascade();
				getResourceCascade().setCascadeRemove(newRemove);
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
			if (this.getResourceCascade() != null) {
				this.getResourceCascade().setCascadeRefresh(newRefresh);						
				if (this.getResourceCascade().isUnset()) {
					removeResourceCascade();
				}
			}
			else if (newRefresh != false) {
				addResourceCascade();
				getResourceCascade().setCascadeRefresh(newRefresh);
			}
		}
		firePropertyChanged(REFRESH_PROPERTY, oldRefresh, newRefresh);
	}
	
	protected void setRefresh_(boolean newRefresh) {
		boolean oldRefresh = this.refresh;
		this.refresh = newRefresh;
		firePropertyChanged(REFRESH_PROPERTY, oldRefresh, newRefresh);
	}
	

	protected CascadeType getResourceCascade() {
		return this.relationshipMapping.getCascade();
	}
	
	protected void removeResourceCascade() {
		this.relationshipMapping.setCascade(null);		
	}
	
	protected void addResourceCascade() {
		this.relationshipMapping.setCascade(OrmFactory.eINSTANCE.createCascadeTypeImpl());			
	}
	
	public void update() {
		CascadeType cascade = getResourceCascade();
		this.setAll_(this.getResourceAll(cascade));
		this.setPersist_(this.getResourcePersist(cascade));
		this.setMerge_(this.getResourceMerge(cascade));
		this.setRemove_(this.getResourceRemove(cascade));
		this.setRefresh_(this.getResourceRefresh(cascade));		
	}

	protected boolean getResourceAll(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadeAll();
	}

	protected boolean getResourcePersist(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadePersist();
	}

	protected boolean getResourceMerge(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadeMerge();
	}

	protected boolean getResourceRemove(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadeRemove();
	}

	protected boolean getResourceRefresh(CascadeType cascade) {
		return cascade == null ? false : cascade.isCascadeRefresh();
	}

	public TextRange getValidationTextRange() {
		return this.getResourceCascade().getValidationTextRange();
	}
}
