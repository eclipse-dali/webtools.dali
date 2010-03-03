/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCascade2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.RelationshipMapping2_0Annotation;
import org.eclipse.jpt.core.utility.TextRange;

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
	
	
	public GenericJavaCascade(JavaRelationshipMapping2_0 parent) {
		super(parent);
	}
	
	
	protected JavaRelationshipMapping2_0 getMapping() {
		return (JavaRelationshipMapping2_0) getParent();
	}
	
	protected RelationshipMapping2_0Annotation getAnnotation() {
		return getMapping().getMappingAnnotation();
	}
	
	public boolean isAll() {
		return this.all;
	}

	public void setAll(boolean newAll) {
		boolean oldAll = this.all;
		this.all = newAll;
		getAnnotation().setCascadeAll(newAll);
		firePropertyChanged(Cascade.ALL_PROPERTY, oldAll, newAll);
	}
	
	protected void setAll_(boolean newAll) {
		boolean oldAll = this.all;
		this.all = newAll;
		firePropertyChanged(Cascade.ALL_PROPERTY, oldAll, newAll);
	}
	
	public boolean isPersist() {
		return this.persist;
	}
	
	public void setPersist(boolean newPersist) {
		boolean oldPersist = this.persist;
		this.persist = newPersist;
		getAnnotation().setCascadePersist(newPersist);
		firePropertyChanged(Cascade.PERSIST_PROPERTY, oldPersist, newPersist);
	}
	
	protected void setPersist_(boolean newPersist) {
		boolean oldPersist = this.persist;
		this.persist = newPersist;
		firePropertyChanged(Cascade.PERSIST_PROPERTY, oldPersist, newPersist);
	}
	
	public boolean isMerge() {
		return this.merge;
	}
	
	public void setMerge(boolean newMerge) {
		boolean oldMerge = this.merge;
		this.merge = newMerge;
		getAnnotation().setCascadeMerge(newMerge);
		firePropertyChanged(Cascade.MERGE_PROPERTY, oldMerge, newMerge);
	}
	
	protected void setMerge_(boolean newMerge) {
		boolean oldMerge = this.merge;
		this.merge = newMerge;
		firePropertyChanged(Cascade.MERGE_PROPERTY, oldMerge, newMerge);
	}
	
	public boolean isRemove() {
		return this.remove;
	}
	
	public void setRemove(boolean newRemove) {
		boolean oldRemove = this.remove;
		this.remove = newRemove;
		getAnnotation().setCascadeRemove(newRemove);
		firePropertyChanged(Cascade.REMOVE_PROPERTY, oldRemove, newRemove);
	}
	
	protected void setRemove_(boolean newRemove) {
		boolean oldRemove = this.remove;
		this.remove = newRemove;
		firePropertyChanged(Cascade.REMOVE_PROPERTY, oldRemove, newRemove);
	}
	
	public boolean isRefresh() {
		return this.refresh;
	}
	
	public void setRefresh(boolean newRefresh) {
		boolean oldRefresh = this.refresh;
		this.refresh = newRefresh;
		getAnnotation().setCascadeRefresh(newRefresh);
		firePropertyChanged(Cascade.REFRESH_PROPERTY, oldRefresh, newRefresh);
	}
	
	protected void setRefresh_(boolean newRefresh) {
		boolean oldRefresh = this.refresh;
		this.refresh = newRefresh;
		firePropertyChanged(Cascade.REFRESH_PROPERTY, oldRefresh, newRefresh);
	}
	
	public boolean isDetach() {
		return this.detach;
	}
	
	public void setDetach(boolean newDetach) {
		boolean oldDetach = this.detach;
		this.detach = newDetach;
		getAnnotation().setCascadeDetach(newDetach);
		firePropertyChanged(Cascade2_0.DETACH_PROPERTY, oldDetach, newDetach);
	}
	
	protected void setDetach_(boolean newDetach) {
		boolean oldDetach = this.detach;
		this.detach = newDetach;
		firePropertyChanged(Cascade2_0.DETACH_PROPERTY, oldDetach, newDetach);
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getAnnotation().getCascadeTextRange(astRoot);
	}
	
	public void initialize() {
		this.all = getAnnotation().isCascadeAll();
		this.persist = getAnnotation().isCascadePersist();
		this.merge = getAnnotation().isCascadeMerge();
		this.remove = getAnnotation().isCascadeRemove();
		this.refresh = getAnnotation().isCascadeRefresh();
		this.detach = getAnnotation().isCascadeDetach();
	}
	
	public void update() {
		this.setAll_(getAnnotation().isCascadeAll());
		this.setPersist_(getAnnotation().isCascadePersist());
		this.setMerge_(getAnnotation().isCascadeMerge());
		this.setRemove_(getAnnotation().isCascadeRemove());
		this.setRefresh_(getAnnotation().isCascadeRefresh());
		this.setDetach_(getAnnotation().isCascadeDetach());
	}
}
