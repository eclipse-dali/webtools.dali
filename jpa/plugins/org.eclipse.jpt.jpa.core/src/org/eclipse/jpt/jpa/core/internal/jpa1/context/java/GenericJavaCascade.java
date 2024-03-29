/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.RelationshipMappingAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;

public class GenericJavaCascade
	extends AbstractJavaContextModel<JavaRelationshipMapping>
	implements Cascade2_0
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
	 * @see AbstractJavaRelationshipMapping#buildCascade()
	 */
	public GenericJavaCascade(JavaRelationshipMapping parent) {
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
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
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
		if (all != this.all) {
			this.getMappingAnnotationForUpdate().setCascadeAll(all);
			this.setAll_(all);
		}
	}

	protected void setAll_(boolean all) {
		boolean old = this.all;
		this.all = all;
		this.firePropertyChanged(ALL_PROPERTY, old, all);
	}

	protected boolean buildAll() {
		RelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation != null) && annotation.isCascadeAll();
	}


	// ********** persist **********

	public boolean isPersist() {
		return this.persist;
	}

	public void setPersist(boolean persist) {
		if (persist != this.persist) {
			this.getMappingAnnotationForUpdate().setCascadePersist(persist);
			this.setPersist_(persist);
		}
	}

	protected void setPersist_(boolean persist) {
		boolean old = this.persist;
		this.persist = persist;
		this.firePropertyChanged(PERSIST_PROPERTY, old, persist);
	}

	protected boolean buildPersist() {
		RelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation != null) && annotation.isCascadePersist();
	}


	// ********** merge **********

	public boolean isMerge() {
		return this.merge;
	}

	public void setMerge(boolean merge) {
		if (merge != this.merge) {
			this.getMappingAnnotationForUpdate().setCascadeMerge(merge);
			this.setMerge_(merge);
		}
	}

	protected void setMerge_(boolean merge) {
		boolean old = this.merge;
		this.merge = merge;
		this.firePropertyChanged(MERGE_PROPERTY, old, merge);
	}

	protected boolean buildMerge() {
		RelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation != null) && annotation.isCascadeMerge();
	}


	// ********** remove **********

	public boolean isRemove() {
		return this.remove;
	}

	public void setRemove(boolean remove) {
		if (remove != this.remove) {
			this.getMappingAnnotationForUpdate().setCascadeRemove(remove);
			this.setRemove_(remove);
		}
	}

	protected void setRemove_(boolean remove) {
		boolean oldRemove = this.remove;
		this.remove = remove;
		this.firePropertyChanged(REMOVE_PROPERTY, oldRemove, remove);
	}

	protected boolean buildRemove() {
		RelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation != null) && annotation.isCascadeRemove();
	}


	// ********** refresh **********

	public boolean isRefresh() {
		return this.refresh;
	}

	public void setRefresh(boolean refresh) {
		if (refresh != this.refresh) {
			this.getMappingAnnotationForUpdate().setCascadeRefresh(refresh);
			this.setRefresh_(refresh);
		}
	}

	protected void setRefresh_(boolean refresh) {
		boolean old = this.refresh;
		this.refresh = refresh;
		this.firePropertyChanged(REFRESH_PROPERTY, old, refresh);
	}

	protected boolean buildRefresh() {
		RelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation != null) && annotation.isCascadeRefresh();
	}


	// ********** detach **********

	public boolean isDetach() {
		return this.detach;
	}

	public void setDetach(boolean detach) {
		if (detach != this.detach) {
			this.getMappingAnnotationForUpdate2_0().setCascadeDetach(detach);
			this.setDetach_(detach);
		}
	}

	protected void setDetach_(boolean detach) {
		boolean old = this.detach;
		this.detach = detach;
		this.firePropertyChanged(DETACH_PROPERTY, old, detach);
	}

	protected boolean buildDetach() {
		return this.isJpa2_0Compatible() && this.buildDetach_();
	}

	protected boolean buildDetach_() {
		RelationshipMappingAnnotation2_0 annotation = this.getMappingAnnotation2_0();
		return (annotation != null) && annotation.isCascadeDetach();
	}



	// ********** misc **********

	protected JavaRelationshipMapping getMapping() {
		return this.parent;
	}

	protected RelationshipMappingAnnotation getMappingAnnotation() {
		return this.getMapping().getMappingAnnotation();
	}

	protected RelationshipMappingAnnotation getMappingAnnotationForUpdate() {
		return this.getMapping().getAnnotationForUpdate();
	}

	protected RelationshipMappingAnnotation2_0 getMappingAnnotation2_0() {
		return (RelationshipMappingAnnotation2_0) this.getMappingAnnotation();
	}

	protected RelationshipMappingAnnotation2_0 getMappingAnnotationForUpdate2_0() {
		return (RelationshipMappingAnnotation2_0) this.getMappingAnnotationForUpdate();
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationCascadeTextRange();
		return (textRange != null) ? textRange : this.getMapping().getValidationTextRange();
	}

	protected TextRange getAnnotationCascadeTextRange() {
		RelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getCascadeTextRange();
	}
}
