/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.RelationshipMapping2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.CascadeType;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;

/**
 * javax.persistence.ManyToMany
 * javax.persistence.ManyToOne
 * javax.persistence.OneToMany
 * javax.persistence.OneToOne
 */
abstract class BinaryRelationshipMappingAnnotation
	extends BinaryAnnotation
	implements RelationshipMapping2_0Annotation
{
	String targetEntity;
	FetchType fetch;
	boolean cascadeAll;
	boolean cascadeMerge;
	boolean cascadePersist;
	boolean cascadeRefresh;
	boolean cascadeRemove;
	boolean cascadeDetach; //added in JPA 2.0


	BinaryRelationshipMappingAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.targetEntity = this.buildTargetEntity();
		this.fetch = this.buildFetch();
		this.initializeCascadeTypes(this.buildCascadeTypes());
	}
	
	protected void initializeCascadeTypes(CascadeType[] cascadeTypes) {
		this.cascadeAll = ArrayTools.contains(cascadeTypes, CascadeType.ALL);
		this.cascadeMerge = ArrayTools.contains(cascadeTypes, CascadeType.MERGE);
		this.cascadePersist = ArrayTools.contains(cascadeTypes, CascadeType.PERSIST);
		this.cascadeRefresh = ArrayTools.contains(cascadeTypes, CascadeType.REFRESH);
		this.cascadeRemove = ArrayTools.contains(cascadeTypes, CascadeType.REMOVE);
		this.cascadeDetach = ArrayTools.contains(cascadeTypes, CascadeType.DETACH);
	}

	@Override
	public void update() {
		super.update();
		this.setTargetEntity_(this.buildTargetEntity());
		this.setFetch_(this.buildFetch());
		this.updateCascadeTypes(this.buildCascadeTypes());
	}

	protected void updateCascadeTypes(CascadeType[] cascadeTypes) {
		this.setCascadeAll_(ArrayTools.contains(cascadeTypes, CascadeType.ALL));
		this.setCascadeMerge_(ArrayTools.contains(cascadeTypes, CascadeType.MERGE));
		this.setCascadePersist_(ArrayTools.contains(cascadeTypes, CascadeType.PERSIST));
		this.setCascadeRefresh_(ArrayTools.contains(cascadeTypes, CascadeType.REFRESH));
		this.setCascadeRemove_(ArrayTools.contains(cascadeTypes, CascadeType.REMOVE));
		this.setCascadeDetach_(ArrayTools.contains(cascadeTypes, CascadeType.DETACH));
	}

	// ********** RelationshipMappingAnnotation implementation **********

	// ***** target entity
	public String getTargetEntity() {
		return this.targetEntity;
	}

	public void setTargetEntity(String targetEntity) {
		throw new UnsupportedOperationException();
	}

	private void setTargetEntity_(String targetEntity) {
		String old = this.targetEntity;
		this.targetEntity = targetEntity;
		this.firePropertyChanged(TARGET_ENTITY_PROPERTY, old, targetEntity);
	}

	private String buildTargetEntity() {
		return (String) this.getJdtMemberValue(this.getTargetEntityElementName());
	}

	abstract String getTargetEntityElementName();

	public TextRange getTargetEntityTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified target entity class name
	public String getFullyQualifiedTargetEntityClassName() {
		return this.targetEntity;
	}

	// ***** fetch
	public FetchType getFetch() {
		return this.fetch;
	}

	public void setFetch(FetchType fetch) {
		throw new UnsupportedOperationException();
	}

	private void setFetch_(FetchType fetch) {
		FetchType old = this.fetch;
		this.fetch = fetch;
		this.firePropertyChanged(FETCH_PROPERTY, old, fetch);
	}

	private FetchType buildFetch() {
		return FetchType.fromJavaAnnotationValue(this.getJdtMemberValue(this.getFetchElementName()));
	}

	abstract String getFetchElementName();

	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** cascade
	private CascadeType[] buildCascadeTypes() {
		return CascadeType.fromJavaAnnotationValues(this.getJdtMemberValues(this.getCascadeElementName()));
	}

	abstract String getCascadeElementName();

	public TextRange getCascadeTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** cascade all
	public boolean isCascadeAll() {
		return this.cascadeAll;
	}

	public void setCascadeAll(boolean cascadeAll) {
		throw new UnsupportedOperationException();
	}

	private void setCascadeAll_(boolean cascadeAll) {
		boolean old = this.cascadeAll;
		this.cascadeAll = cascadeAll;
		this.firePropertyChanged(CASCADE_ALL_PROPERTY, old, cascadeAll);
	}

	// ***** cascade merge
	public boolean isCascadeMerge() {
		return this.cascadeMerge;
	}

	public void setCascadeMerge(boolean cascadeMerge) {
		throw new UnsupportedOperationException();
	}

	private void setCascadeMerge_(boolean cascadeMerge) {
		boolean old = this.cascadeMerge;
		this.cascadeMerge = cascadeMerge;
		this.firePropertyChanged(CASCADE_MERGE_PROPERTY, old, cascadeMerge);
	}

	// ***** cascade persist
	public boolean isCascadePersist() {
		return this.cascadePersist;
	}

	public void setCascadePersist(boolean cascadePersist) {
		throw new UnsupportedOperationException();
	}

	private void setCascadePersist_(boolean cascadePersist) {
		boolean old = this.cascadePersist;
		this.cascadePersist = cascadePersist;
		this.firePropertyChanged(CASCADE_PERSIST_PROPERTY, old, cascadePersist);
	}

	// ***** cascade refresh
	public boolean isCascadeRefresh() {
		return this.cascadeRefresh;
	}

	public void setCascadeRefresh(boolean cascadeRefresh) {
		throw new UnsupportedOperationException();
	}

	private void setCascadeRefresh_(boolean cascadeRefresh) {
		boolean old = this.cascadeRefresh;
		this.cascadeRefresh = cascadeRefresh;
		this.firePropertyChanged(CASCADE_REFRESH_PROPERTY, old, cascadeRefresh);
	}

	// ***** cascade remove
	public boolean isCascadeRemove() {
		return this.cascadeRemove;
	}

	public void setCascadeRemove(boolean cascadeRemove) {
		throw new UnsupportedOperationException();
	}

	private void setCascadeRemove_(boolean cascadeRemove) {
		boolean old = this.cascadeRemove;
		this.cascadeRemove = cascadeRemove;
		this.firePropertyChanged(CASCADE_REMOVE_PROPERTY, old, cascadeRemove);
	}

	// ***** cascade detach - JPA 2.0
	public boolean isCascadeDetach() {
		return this.cascadeDetach;
	}

	public void setCascadeDetach(boolean cascadeDetach) {
		throw new UnsupportedOperationException();
	}

	private void setCascadeDetach_(boolean cascadeDetach) {
		boolean old = this.cascadeDetach;
		this.cascadeDetach = cascadeDetach;
		this.firePropertyChanged(CASCADE_DETACH_PROPERTY, old, cascadeDetach);
	}

}
