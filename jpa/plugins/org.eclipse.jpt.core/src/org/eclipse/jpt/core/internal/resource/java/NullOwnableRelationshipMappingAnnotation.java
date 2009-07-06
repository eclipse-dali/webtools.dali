/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.OneToMany
 * javax.persistence.OneToOne
 */
public abstract class NullOwnableRelationshipMappingAnnotation
	extends NullAnnotation
	implements OwnableRelationshipMappingAnnotation
{
	protected NullOwnableRelationshipMappingAnnotation(JavaResourcePersistentAttribute parent) {
		super(parent);
	}

	@Override
	protected OwnableRelationshipMappingAnnotation setMappingAnnotation() {
		return (OwnableRelationshipMappingAnnotation) super.setMappingAnnotation();
	}

	// ***** target entity
	public String getTargetEntity() {
		return null;
	}

	public void setTargetEntity(String targetEntity) {
		if (targetEntity != null) {
			this.setMappingAnnotation().setTargetEntity(targetEntity);
		}
	}

	public TextRange getTargetEntityTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** fully-qualified target entity class name
	public String getFullyQualifiedTargetEntityClassName() {
		return null;
	}

	// ***** mapped by
	public String getMappedBy() {
		return null;
	}

	public void setMappedBy(String mappedBy) {
		if (mappedBy != null) {
			this.setMappingAnnotation().setMappedBy(mappedBy);
		}
	}

	public TextRange getMappedByTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	// ***** fetch
	public FetchType getFetch() {
		return null;
	}

	public void setFetch(FetchType fetch) {
		if (fetch != null) {
			this.setMappingAnnotation().setFetch(fetch);
		}
	}

	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** cascade all
	public boolean isCascadeAll() {
		return false;
	}

	public void setCascadeAll(boolean all) {
		this.setMappingAnnotation().setCascadeAll(all);
	}

	// ***** cascade merge
	public boolean isCascadeMerge() {
		return false;
	}

	public void setCascadeMerge(boolean merge) {
		this.setMappingAnnotation().setCascadeMerge(merge);
	}

	// ***** cascade persist
	public boolean isCascadePersist() {
		return false;
	}

	public void setCascadePersist(boolean persist) {
		this.setMappingAnnotation().setCascadePersist(persist);
	}

	// ***** cascade refresh
	public boolean isCascadeRefresh() {
		return false;
	}

	public void setCascadeRefresh(boolean refresh) {
		this.setMappingAnnotation().setCascadeRefresh(refresh);
	}

	// ***** cascade remove
	public boolean isCascadeRemove() {
		return false;
	}

	public void setCascadeRemove(boolean remove) {
		this.setMappingAnnotation().setCascadeRemove(remove);
	}

	public TextRange getCascadeTextRange(CompilationUnit astRoot) {
		return null;
	}

}
