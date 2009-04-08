/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumArrayDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitArrayAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.CascadeType;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * javax.persistence.ManyToMany
 * javax.persistence.ManyToOne
 * javax.persistence.OneToMany
 * javax.persistence.OneToOne
 */
abstract class SourceRelationshipMappingAnnotation
	extends SourceAnnotation<Attribute>
	implements RelationshipMappingAnnotation
{
	final DeclarationAnnotationElementAdapter<String> targetEntityDeclarationAdapter;
	final AnnotationElementAdapter<String> targetEntityAdapter;
	String targetEntity;

	String fullyQualifiedTargetEntityClassName;

	final DeclarationAnnotationElementAdapter<String> fetchDeclarationAdapter;
	final AnnotationElementAdapter<String> fetchAdapter;
	FetchType fetch;

	final DeclarationAnnotationElementAdapter<String[]> cascadeDeclarationAdapter;
	final AnnotationElementAdapter<String[]> cascadeAdapter;
	boolean cascadeAll;
	boolean cascadePersist;
	boolean cascadeMerge;
	boolean cascadeRemove;
	boolean cascadeRefresh;


	SourceRelationshipMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
		this.targetEntityDeclarationAdapter = this.getTargetEntityAdapter();
		this.targetEntityAdapter = this.buildAnnotationElementAdapter(this.targetEntityDeclarationAdapter);
		this.fetchDeclarationAdapter = this.getFetchAdapter();
		this.fetchAdapter = this.buildAnnotationElementAdapter(this.fetchDeclarationAdapter);
		this.cascadeDeclarationAdapter = this.getCascadeAdapter();
		this.cascadeAdapter = new ShortCircuitArrayAnnotationElementAdapter<String>(attribute, this.cascadeDeclarationAdapter);
	}

	AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.member, daea);
	}

	AnnotationElementAdapter<Boolean> buildBooleanAnnotationElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new ShortCircuitAnnotationElementAdapter<Boolean>(this.member, daea);
	}

	public void initialize(CompilationUnit astRoot) {
		this.targetEntity = this.buildTargetEntity(astRoot);
		this.fullyQualifiedTargetEntityClassName = this.buildFullyQualifiedTargetEntityClassName(astRoot);
		this.fetch = this.buildFetch(astRoot);
		this.initializeCascadeTypes(astRoot);
	}

	private void initializeCascadeTypes(CompilationUnit astRoot) {
		CascadeType[] cascadeTypes = CascadeType.fromJavaAnnotationValues(this.cascadeAdapter.getValue(astRoot));
		this.cascadeAll = CollectionTools.contains(cascadeTypes, CascadeType.ALL);
		this.cascadeMerge = CollectionTools.contains(cascadeTypes, CascadeType.MERGE);
		this.cascadePersist = CollectionTools.contains(cascadeTypes, CascadeType.PERSIST);
		this.cascadeRefresh = CollectionTools.contains(cascadeTypes, CascadeType.REFRESH);
		this.cascadeRemove = CollectionTools.contains(cascadeTypes, CascadeType.REMOVE);
	}

	public void update(CompilationUnit astRoot) {
		this.setFetch(this.buildFetch(astRoot));
		this.setTargetEntity(this.buildTargetEntity(astRoot));
		this.setFullyQualifiedTargetEntityClassName(this.buildFullyQualifiedTargetEntityClassName(astRoot));
		this.updateCascade(astRoot);
	}

	private void updateCascade(CompilationUnit astRoot) {
		CascadeType[] cascadeTypes = CascadeType.fromJavaAnnotationValues(this.cascadeAdapter.getValue(astRoot));
		this.setCascadeAll(CollectionTools.contains(cascadeTypes, CascadeType.ALL));
		this.setCascadeMerge(CollectionTools.contains(cascadeTypes, CascadeType.MERGE));
		this.setCascadePersist(CollectionTools.contains(cascadeTypes, CascadeType.PERSIST));
		this.setCascadeRefresh(CollectionTools.contains(cascadeTypes, CascadeType.REFRESH));
		this.setCascadeRemove(CollectionTools.contains(cascadeTypes, CascadeType.REMOVE));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.targetEntity);
	}


	// ********** RelationshipMappingAnnotation implementation **********

	// ***** target entity
	public String getTargetEntity() {
		return this.targetEntity;
	}

	public void setTargetEntity(String targetEntity) {
		if (this.attributeValueHasNotChanged(this.targetEntity, targetEntity)) {
			return;
		}
		String old = this.targetEntity;
		this.targetEntity = targetEntity;
		this.targetEntityAdapter.setValue(targetEntity);
		this.firePropertyChanged(TARGET_ENTITY_PROPERTY, old, targetEntity);
	}

	private String buildTargetEntity(CompilationUnit astRoot) {
		return this.targetEntityAdapter.getValue(astRoot);
	}

	public TextRange getTargetEntityTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.targetEntityDeclarationAdapter, astRoot);
	}

	/**
	 * return the Java adapter's 'targetEntity' element adapter config
	 */
	abstract DeclarationAnnotationElementAdapter<String> getTargetEntityAdapter();

	// ***** fully-qualified target entity class name
	public String getFullyQualifiedTargetEntityClassName() {
		return this.fullyQualifiedTargetEntityClassName;
	}

	private void setFullyQualifiedTargetEntityClassName(String name) {
		String old = this.fullyQualifiedTargetEntityClassName;
		this.fullyQualifiedTargetEntityClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_TARGET_ENTITY_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedTargetEntityClassName(CompilationUnit astRoot) {
		return (this.targetEntity == null) ? null : JDTTools.resolveFullyQualifiedName(this.targetEntityAdapter.getExpression(astRoot));
	}

	// ***** fetch
	public FetchType getFetch() {
		return this.fetch;
	}

	public void setFetch(FetchType fetch) {
		if (this.attributeValueHasNotChanged(this.fetch, fetch)) {
			return;
		}
		FetchType old = this.fetch;
		this.fetch = fetch;
		this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(fetch));
		this.firePropertyChanged(FETCH_PROPERTY, old, fetch);
	}

	private FetchType buildFetch(CompilationUnit astRoot) {
		return FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot));
	}

	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.fetchDeclarationAdapter, astRoot);
	}

	/**
	 * return the Java adapter's 'fetch' element adapter config
	 */
	abstract DeclarationAnnotationElementAdapter<String> getFetchAdapter();

	// ***** cascade
	private void setCascadeTypes(CascadeType[] cascadeTypes) {
		this.cascadeAdapter.setValue(CascadeType.toJavaAnnotationValues(cascadeTypes));
	}

	private void setCascade(CascadeType cascadeType, boolean set) {
		String[] javaValues = this.cascadeAdapter.getValue();
		CascadeType[] cascadeTypes = CascadeType.fromJavaAnnotationValues(javaValues);

		boolean present = CollectionTools.contains(cascadeTypes, cascadeType);
		if (set) {
			if ( ! present) {
				this.setCascadeTypes(CollectionTools.add(cascadeTypes, cascadeType));
			}
		} else {
			if (present) {
				this.setCascadeTypes(CollectionTools.remove(cascadeTypes, cascadeType));
			}
		}
	}

	public TextRange getCascadeTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.cascadeDeclarationAdapter, astRoot);
	}

	/**
	 * return the Java adapter's 'cascade' element adapter config
	 */
	abstract DeclarationAnnotationElementAdapter<String[]> getCascadeAdapter();

	// ***** cascade all
	public boolean isCascadeAll() {
		return this.cascadeAll;
	}

	public void setCascadeAll(boolean cascadeAll) {
		if (this.cascadeAll == cascadeAll) {
			return;
		}
		boolean old = this.cascadeAll;
		this.cascadeAll = cascadeAll;
		this.setCascade(CascadeType.ALL, cascadeAll);
		this.firePropertyChanged(CASCADE_ALL_PROPERTY, old, cascadeAll);
	}

	// ***** cascade persist
	public boolean isCascadePersist() {
		return this.cascadePersist;
	}

	public void setCascadePersist(boolean cascadePersist) {
		if (this.cascadePersist == cascadePersist) {
			return;
		}
		boolean old = this.cascadePersist;
		this.cascadePersist = cascadePersist;
		this.setCascade(CascadeType.PERSIST, cascadePersist);
		this.firePropertyChanged(CASCADE_PERSIST_PROPERTY, old, cascadePersist);
	}

	// ***** cascade merge
	public boolean isCascadeMerge() {
		return this.cascadeMerge;
	}

	public void setCascadeMerge(boolean cascadeMerge) {
		if (this.cascadeMerge == cascadeMerge) {
			return;
		}
		boolean old = this.cascadeMerge;
		this.cascadeMerge = cascadeMerge;
		this.setCascade(CascadeType.MERGE, cascadeMerge);
		this.firePropertyChanged(CASCADE_MERGE_PROPERTY, old, cascadeMerge);
	}

	// ***** cascade remove
	public boolean isCascadeRemove() {
		return this.cascadeRemove;
	}

	public void setCascadeRemove(boolean cascadeRemove) {
		if (this.cascadeRemove == cascadeRemove) {
			return;
		}
		boolean old = this.cascadeRemove;
		this.cascadeRemove = cascadeRemove;
		this.setCascade(CascadeType.REMOVE, cascadeRemove);
		this.firePropertyChanged(CASCADE_REMOVE_PROPERTY, old, cascadeRemove);
	}

	// ***** cascade refresh
	public boolean isCascadeRefresh() {
		return this.cascadeRefresh;
	}

	public void setCascadeRefresh(boolean cascadeRefresh) {
		if (this.cascadeRefresh == cascadeRefresh) {
			return;
		}
		boolean old = this.cascadeRefresh;
		this.cascadeRefresh = cascadeRefresh;
		this.setCascade(CascadeType.REFRESH, cascadeRefresh);
		this.firePropertyChanged(CASCADE_REFRESH_PROPERTY, old, cascadeRefresh);
	}


	// ********** static methods **********

	static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		// TODO what about QualifiedType?
		return buildAnnotationElementAdapter(annotationAdapter, elementName, SimpleTypeStringExpressionConverter.instance());
	}

	static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, converter);
	}

	static DeclarationAnnotationElementAdapter<String> buildFetchAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}

	static DeclarationAnnotationElementAdapter<String[]> buildEnumArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumArrayDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}

}
