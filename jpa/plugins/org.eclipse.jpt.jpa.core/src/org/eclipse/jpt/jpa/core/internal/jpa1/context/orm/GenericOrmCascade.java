/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCascade2_0;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlRelationshipMapping;
import org.eclipse.jpt.jpa.core.resource.orm.CascadeType;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;

public class GenericOrmCascade
	extends AbstractOrmXmlContextNode
	implements OrmCascade2_0
{
	protected boolean all;

	protected boolean persist;

	protected boolean merge;

	protected boolean remove;

	protected boolean refresh;

	/* JPA 2.0 */
	protected boolean detach;


	/**
	 * This is built directly by the mapping implementation; as opposed to via
	 * a platform-specific factory.
	 * @see AbstractOrmRelationshipMapping#buildCascade()
	 */
	public GenericOrmCascade(OrmRelationshipMapping parent) {
		super(parent);
		this.all = this.buildAll();
		this.persist = this.buildPersist();
		this.merge = this.buildMerge();
		this.remove = this.buildRemove();
		this.refresh = this.buildRefresh();
		this.detach = this.buildDetach();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setAll_(this.buildAll());
		this.setPersist_(this.buildPersist());
		this.setMerge_(this.buildMerge());
		this.setRemove_(this.buildRemove());
		this.setRefresh_(this.buildRefresh());
		this.setDetach_(this.buildDetach());
	}


	// ********** all **********

	public boolean isAll() {
		return this.all;
	}

	public void setAll(boolean all) {
		if (this.all != all) {
			CascadeType xmlCascade = this.getXmlCascadeForUpdate();
			this.setAll_(all);
			xmlCascade.setCascadeAll(all);
			this.removeXmlCascadeIfUnset();
		}
	}

	protected void setAll_(boolean all) {
		boolean old = this.all;
		this.all = all;
		this.firePropertyChanged(ALL_PROPERTY, old, all);
	}

	protected boolean buildAll() {
		CascadeType xmlCascade = this.getXmlCascade();
		return (xmlCascade != null) && xmlCascade.isCascadeAll();
	}


	// ********** persist **********

	public boolean isPersist() {
		return this.persist;
	}

	public void setPersist(boolean persist) {
		if (this.persist != persist) {
			CascadeType xmlCascade = this.getXmlCascadeForUpdate();
			this.setPersist_(persist);
			xmlCascade.setCascadePersist(persist);
			this.removeXmlCascadeIfUnset();
		}
	}

	protected boolean setPersist_(boolean persist) {
		boolean old = this.persist;
		this.persist = persist;
		return this.firePropertyChanged(PERSIST_PROPERTY, old, persist);
	}

	protected boolean buildPersist() {
		CascadeType xmlCascade = this.getXmlCascade();
		return (xmlCascade != null) && xmlCascade.isCascadePersist();
	}


	// ********** merge **********

	public boolean isMerge() {
		return this.merge;
	}

	public void setMerge(boolean merge) {
		if (this.merge != merge) {
			CascadeType xmlCascade = this.getXmlCascadeForUpdate();
			this.setMerge_(merge);
			xmlCascade.setCascadeMerge(merge);
			this.removeXmlCascadeIfUnset();
		}
	}

	protected boolean setMerge_(boolean merge) {
		boolean old = this.merge;
		this.merge = merge;
		return this.firePropertyChanged(MERGE_PROPERTY, old, merge);
	}

	protected boolean buildMerge() {
		CascadeType xmlCascade = this.getXmlCascade();
		return (xmlCascade != null) && xmlCascade.isCascadeMerge();
	}


	// ********** remove **********

	public boolean isRemove() {
		return this.remove;
	}

	public void setRemove(boolean remove) {
		if (this.remove != remove) {
			CascadeType xmlCascade = this.getXmlCascadeForUpdate();
			this.setRemove_(remove);
			xmlCascade.setCascadeRemove(remove);
			this.removeXmlCascadeIfUnset();
		}
	}

	protected boolean setRemove_(boolean remove) {
		boolean old = this.remove;
		this.remove = remove;
		return this.firePropertyChanged(REMOVE_PROPERTY, old, remove);
	}

	protected boolean buildRemove() {
		CascadeType xmlCascade = this.getXmlCascade();
		return (xmlCascade != null) && xmlCascade.isCascadeRemove();
	}


	// ********** refresh **********

	public boolean isRefresh() {
		return this.refresh;
	}

	public void setRefresh(boolean refresh) {
		if (this.refresh != refresh) {
			CascadeType xmlCascade = this.getXmlCascadeForUpdate();
			this.setRefresh_(refresh);
			xmlCascade.setCascadeRefresh(refresh);
			this.removeXmlCascadeIfUnset();
		}
	}

	protected boolean setRefresh_(boolean refresh) {
		boolean old = this.refresh;
		this.refresh = refresh;
		return this.firePropertyChanged(REFRESH_PROPERTY, old, refresh);
	}

	protected boolean buildRefresh() {
		CascadeType xmlCascade = this.getXmlCascade();
		return (xmlCascade != null) && xmlCascade.isCascadeRefresh();
	}


	// ********** detach **********

	public boolean isDetach() {
		return this.detach;
	}

	public void setDetach(boolean detach) {
		if (this.detach != detach) {
			CascadeType xmlCascade = this.getXmlCascadeForUpdate();
			this.setDetach_(detach);
			xmlCascade.setCascadeDetach(detach);
			this.removeXmlCascadeIfUnset();
		}
	}

	protected boolean setDetach_(boolean detach) {
		boolean old = this.detach;
		this.detach = detach;
		return this.firePropertyChanged(DETACH_PROPERTY, old, detach);
	}

	protected boolean buildDetach() {
		return this.isJpa2_0Compatible() && this.buildDetach_();
	}

	protected boolean buildDetach_() {
		CascadeType xmlCascade = this.getXmlCascade();
		return (xmlCascade != null) && xmlCascade.isCascadeDetach();
	}


	// ********** XML cascade **********

	/**
	 * Return <code>null</code> if XML cascade does not exists.
	 */
	protected CascadeType getXmlCascade() {
		return this.getXmlRelationshipMapping().getCascade();
	}

	/**
	 * Build the XML cascade if it does not exist.
	 */
	protected CascadeType getXmlCascadeForUpdate() {
		CascadeType xmlCascade = this.getXmlCascade();
		return (xmlCascade != null) ? xmlCascade : this.buildXmlCascade();
	}

	protected CascadeType buildXmlCascade() {
		CascadeType xmlCascade = OrmFactory.eINSTANCE.createCascadeType();
		this.getXmlRelationshipMapping().setCascade(xmlCascade);
		return xmlCascade;
	}

	protected void removeXmlCascadeIfUnset() {
		if (this.getXmlCascade().isUnset()) {
			this.removeXmlCascade();
		}
	}

	protected void removeXmlCascade() {
		this.getXmlRelationshipMapping().setCascade(null);
	}


	// ********** misc **********

	@Override
	public OrmRelationshipMapping getParent() {
		return (OrmRelationshipMapping) super.getParent();
	}

	protected OrmRelationshipMapping getRelationshipMapping() {
		return this.getParent();
	}

	protected AbstractXmlRelationshipMapping getXmlRelationshipMapping() {
		return this.getRelationshipMapping().getXmlAttributeMapping();
	}

	public void initializeFrom(Cascade oldCascade) {
		this.setAll(oldCascade.isAll());
		this.setPersist(oldCascade.isPersist());
		this.setMerge(oldCascade.isMerge());
		this.setRemove(oldCascade.isRemove());
		this.setRefresh(oldCascade.isRefresh());
		if (this.isJpa2_0Compatible()) {
			this.setDetach(((Cascade2_0) oldCascade).isDetach());
		}
	}

	public TextRange getValidationTextRange() {
		CascadeType xmlCascade = this.getXmlCascade();
		return (xmlCascade != null) ?
				xmlCascade.getValidationTextRange() :
				this.getRelationshipMapping().getValidationTextRange();
	}
}
