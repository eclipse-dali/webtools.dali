/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.jpa2.resource.java.RelationshipMapping2_0Annotation;
import org.eclipse.jpt.core.resource.java.CascadeType;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.utility.internal.ArrayTools;

/**
 * javax.persistence.ManyToMany
 * javax.persistence.ManyToOne
 * javax.persistence.OneToMany
 * javax.persistence.OneToOne
 */
abstract class SourceRelationshipMappingAnnotation
	extends SourceAnnotation<Attribute>
	implements RelationshipMapping2_0Annotation
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
	CascadeType[] cascadeTypes = EMPTY_CASCADE_TYPE_ARRAY;  // this should never be null
	private static final CascadeType[] EMPTY_CASCADE_TYPE_ARRAY = new CascadeType[0];


	SourceRelationshipMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
		this.targetEntityDeclarationAdapter = this.getTargetEntityAdapter();
		this.targetEntityAdapter = this.buildAnnotationElementAdapter(this.targetEntityDeclarationAdapter);
		this.fetchDeclarationAdapter = this.getFetchAdapter();
		this.fetchAdapter = this.buildAnnotationElementAdapter(this.fetchDeclarationAdapter);
		this.cascadeDeclarationAdapter = this.getCascadeAdapter();
		this.cascadeAdapter = new AnnotatedElementAnnotationElementAdapter<String[]>(attribute, this.cascadeDeclarationAdapter);
	}

	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	protected AnnotationElementAdapter<Boolean> buildBooleanAnnotationElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Boolean>(this.annotatedElement, daea);
	}

	public void initialize(CompilationUnit astRoot) {
		this.targetEntity = this.buildTargetEntity(astRoot);
		this.fullyQualifiedTargetEntityClassName = this.buildFullyQualifiedTargetEntityClassName(astRoot);
		this.fetch = this.buildFetch(astRoot);
		this.cascadeTypes = this.buildCascadeTypes(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncFetch(this.buildFetch(astRoot));
		this.syncTargetEntity(this.buildTargetEntity(astRoot));
		this.syncFullyQualifiedTargetEntityClassName(this.buildFullyQualifiedTargetEntityClassName(astRoot));
		this.syncCascadeTypes(this.buildCascadeTypes(astRoot));
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
		if (this.attributeValueHasChanged(this.targetEntity, targetEntity)) {
			this.targetEntity = targetEntity;
			this.targetEntityAdapter.setValue(targetEntity);
		}
	}

	private void syncTargetEntity(String astTargetEntity) {
		String old = this.targetEntity;
		this.targetEntity = astTargetEntity;
		this.firePropertyChanged(TARGET_ENTITY_PROPERTY, old, astTargetEntity);
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

	private void syncFullyQualifiedTargetEntityClassName(String name) {
		String old = this.fullyQualifiedTargetEntityClassName;
		this.fullyQualifiedTargetEntityClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_TARGET_ENTITY_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedTargetEntityClassName(CompilationUnit astRoot) {
		return (this.targetEntity == null) ? null : ASTTools.resolveFullyQualifiedName(this.targetEntityAdapter.getExpression(astRoot));
	}

	// ***** fetch
	public FetchType getFetch() {
		return this.fetch;
	}

	public void setFetch(FetchType fetch) {
		if (this.attributeValueHasChanged(this.fetch, fetch)) {
			this.fetch = fetch;
			this.fetchAdapter.setValue(FetchType.toJavaAnnotationValue(fetch));
		}
	}

	private void syncFetch(FetchType astFetch) {
		FetchType old = this.fetch;
		this.fetch = astFetch;
		this.firePropertyChanged(FETCH_PROPERTY, old, astFetch);
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

	// ***** cascade types
	/**
	 * pre-condition: state of 'cascadeTypes' is to change
	 */
	private void setCascadeType(CascadeType cascadeType, boolean set) {
		this.setCascadeTypes(set ?
			ArrayTools.add(this.cascadeTypes, cascadeType) :
			ArrayTools.remove(this.cascadeTypes, cascadeType)
		);
	}

	/**
	 * pre-condition: state of 'cascadeTypes' is to change
	 */
	private void setCascadeTypes(CascadeType[] cascadeTypes) {
		this.cascadeTypes = cascadeTypes;
		this.cascadeAdapter.setValue(CascadeType.toJavaAnnotationValues(cascadeTypes));
	}

	private void syncCascadeTypes(CascadeType[] astCascadeTypes) {
		CascadeType[] old = this.cascadeTypes;
		this.cascadeTypes = astCascadeTypes;
		this.syncCascadeAll(old);
		this.syncCascadeMerge(old);
		this.syncCascadePersist(old);
		this.syncCascadeRefresh(old);
		this.syncCascadeRemove(old);
		this.syncCascadeDetach(old);
	}

	private CascadeType[] buildCascadeTypes(CompilationUnit astRoot) {
		return CascadeType.fromJavaAnnotationValues(this.cascadeAdapter.getValue(astRoot));
	}

	private boolean cascadeTypeIsTrue(CascadeType cascadeType) {
		return ArrayTools.contains(this.cascadeTypes, cascadeType);
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
		return this.cascadeTypeIsTrue(CascadeType.ALL);
	}

	public void setCascadeAll(boolean cascadeAll) {
		if (this.isCascadeAll() != cascadeAll) {
			this.setCascadeType(CascadeType.ALL, cascadeAll);
		}
	}

	private void syncCascadeAll(CascadeType[] oldCascadeTypes) {
		boolean old = ArrayTools.contains(oldCascadeTypes, CascadeType.ALL);
		this.firePropertyChanged(CASCADE_ALL_PROPERTY, old, this.isCascadeAll());
	}

	// ***** cascade persist
	public boolean isCascadePersist() {
		return this.cascadeTypeIsTrue(CascadeType.PERSIST);
	}

	public void setCascadePersist(boolean cascadePersist) {
		if (this.isCascadePersist() != cascadePersist) {
			this.setCascadeType(CascadeType.PERSIST, cascadePersist);
		}
	}

	private void syncCascadePersist(CascadeType[] oldCascadeTypes) {
		boolean old = ArrayTools.contains(oldCascadeTypes, CascadeType.PERSIST);
		this.firePropertyChanged(CASCADE_PERSIST_PROPERTY, old, this.isCascadePersist());
	}

	// ***** cascade merge
	public boolean isCascadeMerge() {
		return this.cascadeTypeIsTrue(CascadeType.MERGE);
	}

	public void setCascadeMerge(boolean cascadeMerge) {
		if (this.isCascadeMerge() != cascadeMerge) {
			this.setCascadeType(CascadeType.MERGE, cascadeMerge);
		}
	}

	private void syncCascadeMerge(CascadeType[] oldCascadeTypes) {
		boolean old = ArrayTools.contains(oldCascadeTypes, CascadeType.MERGE);
		this.firePropertyChanged(CASCADE_MERGE_PROPERTY, old, this.isCascadeMerge());
	}

	// ***** cascade remove
	public boolean isCascadeRemove() {
		return this.cascadeTypeIsTrue(CascadeType.REMOVE);
	}

	public void setCascadeRemove(boolean cascadeRemove) {
		if (this.isCascadeRemove() != cascadeRemove) {
			this.setCascadeType(CascadeType.REMOVE, cascadeRemove);
		}
	}

	private void syncCascadeRemove(CascadeType[] oldCascadeTypes) {
		boolean old = ArrayTools.contains(oldCascadeTypes, CascadeType.REMOVE);
		this.firePropertyChanged(CASCADE_REMOVE_PROPERTY, old, this.isCascadeRemove());
	}

	// ***** cascade refresh
	public boolean isCascadeRefresh() {
		return this.cascadeTypeIsTrue(CascadeType.REFRESH);
	}

	public void setCascadeRefresh(boolean cascadeRefresh) {
		if (this.isCascadeRefresh() != cascadeRefresh) {
			this.setCascadeType(CascadeType.REFRESH, cascadeRefresh);
		}
	}

	private void syncCascadeRefresh(CascadeType[] oldCascadeTypes) {
		boolean old = ArrayTools.contains(oldCascadeTypes, CascadeType.REFRESH);
		this.firePropertyChanged(CASCADE_REFRESH_PROPERTY, old, this.isCascadeRefresh());
	}

	// ***** cascade detach - JPA 2.0
	public boolean isCascadeDetach() {
		return this.cascadeTypeIsTrue(CascadeType.DETACH);
	}

	public void setCascadeDetach(boolean cascadeDetach) {
		if (this.isCascadeDetach() != cascadeDetach) {
			this.setCascadeType(CascadeType.DETACH, cascadeDetach);
		}
	}

	private void syncCascadeDetach(CascadeType[] oldCascadeTypes) {
		boolean old = ArrayTools.contains(oldCascadeTypes, CascadeType.DETACH);
		this.firePropertyChanged(CASCADE_DETACH_PROPERTY, old, this.isCascadeDetach());
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
