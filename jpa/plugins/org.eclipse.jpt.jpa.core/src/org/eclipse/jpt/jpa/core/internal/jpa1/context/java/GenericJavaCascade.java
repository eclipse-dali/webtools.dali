/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCascade2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.RelationshipMapping2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;

public class GenericJavaCascade
	extends AbstractJavaJpaContextNode
	implements JavaCascade2_0
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
		RelationshipMapping2_0Annotation annotation = this.getMappingAnnotation2_0();
		return (annotation != null) && annotation.isCascadeDetach();
	}



	// ********** misc **********

	@Override
	public JavaRelationshipMapping getParent() {
		return (JavaRelationshipMapping) super.getParent();
	}

	protected JavaRelationshipMapping getMapping() {
		return this.getParent();
	}

	protected RelationshipMappingAnnotation getMappingAnnotation() {
		return this.getMapping().getMappingAnnotation();
	}

	protected RelationshipMappingAnnotation getMappingAnnotationForUpdate() {
		return this.getMapping().getAnnotationForUpdate();
	}

	protected RelationshipMapping2_0Annotation getMappingAnnotation2_0() {
		return (RelationshipMapping2_0Annotation) this.getMappingAnnotation();
	}

	protected RelationshipMapping2_0Annotation getMappingAnnotationForUpdate2_0() {
		return (RelationshipMapping2_0Annotation) this.getMappingAnnotationForUpdate();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		RelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getCascadeTextRange(astRoot);
	}
}
