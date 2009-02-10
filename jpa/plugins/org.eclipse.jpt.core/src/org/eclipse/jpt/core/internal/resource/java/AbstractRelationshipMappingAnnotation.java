/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

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
 * 
 */
public abstract class AbstractRelationshipMappingAnnotation
	extends AbstractResourceAnnotation<Attribute>
	implements RelationshipMappingAnnotation
{
	// hold this so we can get the 'targetEntity' text range
	private final DeclarationAnnotationElementAdapter<String> targetEntityDeclarationAdapter;
	
	// hold this so we can get the 'fetch' text range
	private final DeclarationAnnotationElementAdapter<String> fetchDeclarationAdapter;
	
	// hold this so we can get the 'cascade' text range
	private final DeclarationAnnotationElementAdapter<String[]> cascadeDeclarationAdapter;
	
	private final AnnotationElementAdapter<String> targetEntityAdapter;

	private final AnnotationElementAdapter<String> fetchAdapter;

	private final AnnotationElementAdapter<String[]> cascadeAdapter;

	private String targetEntity;

	private String fullyQualifiedTargetEntity;

	private FetchType fetch;
	
	protected boolean cascadeAll;

	protected boolean cascadePersist;

	protected boolean cascadeMerge;

	protected boolean cascadeRemove;

	protected boolean cascadeRefresh;

	
	public AbstractRelationshipMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
		this.targetEntityDeclarationAdapter = getTargetEntityAdapter();
		this.targetEntityAdapter = buildAnnotationElementAdapter(this.targetEntityDeclarationAdapter);
		this.fetchDeclarationAdapter = getFetchAdapter();
		this.fetchAdapter = buildAnnotationElementAdapter(this.fetchDeclarationAdapter);
		this.cascadeDeclarationAdapter = getCascadeAdapter();
		this.cascadeAdapter = new ShortCircuitArrayAnnotationElementAdapter<String>(attribute, this.cascadeDeclarationAdapter);
	}
	
	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.getMember(), daea);
	}
	
	protected AnnotationElementAdapter<Boolean> buildBooleanAnnotationElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new ShortCircuitAnnotationElementAdapter<Boolean>(this.getMember(), daea);
	}

	/**
	 * return the Java adapter's 'targetEntity' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> getTargetEntityAdapter();

	/**
	 * return the Java adapter's 'cascade' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String[]> getCascadeAdapter();

	/**
	 * return the Java adapter's 'fetch' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> getFetchAdapter();

	public void initialize(CompilationUnit astRoot) {
		this.targetEntity = this.targetEntity(astRoot);
		this.fullyQualifiedTargetEntity = this.fullyQualifiedTargetEntity(astRoot);
		this.fetch = this.fetch(astRoot);
		this.initializeCascadeTypes(astRoot);
	}
	
	protected void initializeCascadeTypes(CompilationUnit astRoot) {
		String[] javaValue = this.cascadeAdapter.getValue(astRoot);
		CascadeType[] cascadeTypes = CascadeType.fromJavaAnnotationValue(javaValue);
		
		this.cascadeAll = CollectionTools.contains(cascadeTypes, CascadeType.ALL);
		this.cascadeMerge = CollectionTools.contains(cascadeTypes, CascadeType.MERGE);
		this.cascadePersist = CollectionTools.contains(cascadeTypes, CascadeType.PERSIST);
		this.cascadeRefresh = CollectionTools.contains(cascadeTypes, CascadeType.REFRESH);
		this.cascadeRemove = CollectionTools.contains(cascadeTypes, CascadeType.REMOVE);
	}
	
	public String getTargetEntity() {
		return this.targetEntity;
	}
	
	public void setTargetEntity(String newTargetEntity) {
		if (attributeValueHasNotChanged(this.targetEntity, newTargetEntity)) {
			return;
		}
		String oldTargetEntity = this.targetEntity;
		this.targetEntity = newTargetEntity;
		this.targetEntityAdapter.setValue(newTargetEntity);
		firePropertyChanged(TARGET_ENTITY_PROPERTY, oldTargetEntity, newTargetEntity);
	}
	
	public String getFullyQualifiedTargetEntity() {
		return this.fullyQualifiedTargetEntity;
	}
	
	protected void setFullyQualifiedTargetEntity(String newTargetEntity) {
		String oldTargetEntity = this.fullyQualifiedTargetEntity;
		this.fullyQualifiedTargetEntity = newTargetEntity;
		firePropertyChanged(FULLY_QUALFIEID_TARGET_ENTITY_PROPERTY, oldTargetEntity, newTargetEntity);
	}
	
	public FetchType getFetch() {
		return this.fetch;
	}
	
	public void setFetch(FetchType newFetch) {
		if (attributeValueHasNotChanged(this.fetch, newFetch)) {
			return;
		}
		FetchType oldFetch = this.fetch;
		this.fetch = newFetch;
		this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(newFetch));
		firePropertyChanged(FETCH_PROPERTY, oldFetch, newFetch);
	}
		
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
	
	private void setCascadeTypes(CascadeType[] cascadeTypes) {
		this.cascadeAdapter.setValue(CascadeType.toJavaAnnotationValue(cascadeTypes));
	}
	
	private void setCascade(CascadeType cascadeType, boolean set) {
		String[] javaValues = this.cascadeAdapter.getValue();
		CascadeType[] cascadeTypes = CascadeType.fromJavaAnnotationValue(javaValues);	

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
	
	public TextRange getTargetEntityTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.targetEntityDeclarationAdapter, astRoot);
	}
	
	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.fetchDeclarationAdapter, astRoot);
	}
	
	public TextRange getCascadeTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.cascadeDeclarationAdapter, astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setFetch(this.fetch(astRoot));
		this.setTargetEntity(this.targetEntity(astRoot));
		this.setFullyQualifiedTargetEntity(this.fullyQualifiedTargetEntity(astRoot));
		this.updateCascadeFromJava(astRoot);
	}
	
	protected FetchType fetch(CompilationUnit astRoot) {
		return FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot));
	}
	
	protected String targetEntity(CompilationUnit astRoot) {
		return this.targetEntityAdapter.getValue(astRoot);
	}
	
	private void updateCascadeFromJava(CompilationUnit astRoot) {
		String[] javaValue = this.cascadeAdapter.getValue(astRoot);
		CascadeType[] cascadeTypes = CascadeType.fromJavaAnnotationValue(javaValue);
		setCascadeAll(CollectionTools.contains(cascadeTypes, CascadeType.ALL));
		setCascadeMerge(CollectionTools.contains(cascadeTypes, CascadeType.MERGE));
		setCascadePersist(CollectionTools.contains(cascadeTypes, CascadeType.PERSIST));
		setCascadeRefresh(CollectionTools.contains(cascadeTypes, CascadeType.REFRESH));
		setCascadeRemove(CollectionTools.contains(cascadeTypes, CascadeType.REMOVE));
	}
	
	private String fullyQualifiedTargetEntity(CompilationUnit astRoot) {
		if (getTargetEntity() == null) {
			return null;
		}
		return JDTTools.resolveFullyQualifiedName(this.targetEntityAdapter.getExpression(astRoot));
	}

	// ********** static methods **********
	
	protected static DeclarationAnnotationElementAdapter<String> buildTargetEntityAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		// TODO what about QualifiedType?
		return buildAnnotationElementAdapter(annotationAdapter, elementName, SimpleTypeStringExpressionConverter.instance());
	}
	
	protected static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, converter);
	}
	
	protected static DeclarationAnnotationElementAdapter<String> buildFetchAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}
	
	protected static DeclarationAnnotationElementAdapter<String[]> buildEnumArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumArrayDeclarationAnnotationElementAdapter(annotationAdapter, elementName, false);
	}

}
