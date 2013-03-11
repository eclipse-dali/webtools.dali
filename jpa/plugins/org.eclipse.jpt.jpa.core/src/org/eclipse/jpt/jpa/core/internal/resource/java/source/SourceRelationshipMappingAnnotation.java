/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumArrayDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.RelationshipMappingAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.CascadeType;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;

/**
 * <code><ul>
 * <li>javax.persistence.ManyToMany
 * <li>javax.persistence.ManyToOne
 * <li>javax.persistence.OneToMany
 * <li>javax.persistence.OneToOne
 * </ul></code>
 */
abstract class SourceRelationshipMappingAnnotation
	extends SourceAnnotation
	implements RelationshipMappingAnnotation2_0
{
	final DeclarationAnnotationElementAdapter<String> targetEntityDeclarationAdapter;
	final AnnotationElementAdapter<String> targetEntityAdapter;
	String targetEntity;
	TextRange targetEntityTextRange;

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceIdClassAnnotation#fullyQualifiedClassName
	 */
	private String fullyQualifiedTargetEntityClassName;
	// we need a flag since the f-q name can be null
	private boolean fqTargetEntityClassNameStale = true;

	final DeclarationAnnotationElementAdapter<String> fetchDeclarationAdapter;
	final AnnotationElementAdapter<String> fetchAdapter;
	FetchType fetch;
	TextRange fetchTextRange;

	final DeclarationAnnotationElementAdapter<String[]> cascadeDeclarationAdapter;
	final AnnotationElementAdapter<String[]> cascadeAdapter;
	CascadeType[] cascadeTypes = EMPTY_CASCADE_TYPE_ARRAY;  // this should never be null
	TextRange cascadeTextRange;
	private static final CascadeType[] EMPTY_CASCADE_TYPE_ARRAY = new CascadeType[0];


	SourceRelationshipMappingAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
		this.targetEntityDeclarationAdapter = this.getTargetEntityAdapter();
		this.targetEntityAdapter = this.buildAnnotationElementAdapter(this.targetEntityDeclarationAdapter);
		this.fetchDeclarationAdapter = this.getFetchAdapter();
		this.fetchAdapter = this.buildAnnotationElementAdapter(this.fetchDeclarationAdapter);
		this.cascadeDeclarationAdapter = this.getCascadeAdapter();
		this.cascadeAdapter = new AnnotatedElementAnnotationElementAdapter<String[]>(element, this.cascadeDeclarationAdapter);
	}

	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	protected AnnotationElementAdapter<Boolean> buildBooleanAnnotationElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Boolean>(this.annotatedElement, daea);
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.targetEntity = this.buildTargetEntity(astAnnotation);
		this.targetEntityTextRange = this.buildTargetEntityTextRange(astAnnotation);

		this.fetch = this.buildFetch(astAnnotation);
		this.fetchTextRange = this.buildFetchTextRange(astAnnotation);

		this.cascadeTypes = this.buildCascadeTypes(astAnnotation);
		this.cascadeTextRange = this.buildCascadeTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncFetch(this.buildFetch(astAnnotation));
		this.targetEntityTextRange = this.buildTargetEntityTextRange(astAnnotation);

		this.syncTargetEntity(this.buildTargetEntity(astAnnotation));
		this.fetchTextRange = this.buildFetchTextRange(astAnnotation);

		this.syncCascadeTypes(this.buildCascadeTypes(astAnnotation));
		this.cascadeTextRange = this.buildCascadeTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.targetEntity == null) &&
				(this.fetch == null) &&
				(this.cascadeTypes.length == 0);
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
			this.fqTargetEntityClassNameStale = true;
			this.targetEntityAdapter.setValue(targetEntity);
		}
	}

	private void syncTargetEntity(String astTargetEntity) {
		if (this.attributeValueHasChanged(this.targetEntity, astTargetEntity)) {
			this.syncTargetEntity_(astTargetEntity);
		}
	}

	private void syncTargetEntity_(String astTargetEntity) {
		String old = this.targetEntity;
		this.targetEntity = astTargetEntity;
		this.fqTargetEntityClassNameStale = true;
		this.firePropertyChanged(TARGET_ENTITY_PROPERTY, old, astTargetEntity);
	}

	private String buildTargetEntity(Annotation astAnnotation) {
		return this.targetEntityAdapter.getValue(astAnnotation);
	}

	public TextRange getTargetEntityTextRange() {
		return this.targetEntityTextRange;
	}

	private TextRange buildTargetEntityTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.targetEntityDeclarationAdapter, astAnnotation);
	}

	/**
	 * return the Java adapter's 'targetEntity' element adapter config
	 */
	abstract DeclarationAnnotationElementAdapter<String> getTargetEntityAdapter();

	// ***** fully-qualified target entity class name
	public String getFullyQualifiedTargetEntityClassName() {
		if (this.fqTargetEntityClassNameStale) {
			this.fullyQualifiedTargetEntityClassName = this.buildFullyQualifiedTargetEntityClassName();
			this.fqTargetEntityClassNameStale = false;
		}
		return this.fullyQualifiedTargetEntityClassName;
	}

	private String buildFullyQualifiedTargetEntityClassName() {
		return (this.targetEntity == null) ? null : this.buildFullyQualifiedTargetEntityClassName_();
	}

	private String buildFullyQualifiedTargetEntityClassName_() {
		return ASTTools.resolveFullyQualifiedName(this.targetEntityAdapter.getExpression(this.buildASTRoot()));
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

	private FetchType buildFetch(Annotation astAnnotation) {
		return FetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astAnnotation));
	}

	public TextRange getFetchTextRange() {
		return this.fetchTextRange;
	}

	private TextRange buildFetchTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.fetchDeclarationAdapter, astAnnotation);
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

	private CascadeType[] buildCascadeTypes(Annotation astAnnotation) {
		return CascadeType.fromJavaAnnotationValues(this.cascadeAdapter.getValue(astAnnotation));
	}

	private boolean cascadeTypeIsTrue(CascadeType cascadeType) {
		return ArrayTools.contains(this.cascadeTypes, cascadeType);
	}

	public TextRange getCascadeTextRange() {
		return this.cascadeTextRange;
	}

	private TextRange buildCascadeTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.cascadeDeclarationAdapter, astAnnotation);
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
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}

	static DeclarationAnnotationElementAdapter<String> buildFetchAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumDeclarationAnnotationElementAdapter(annotationAdapter, elementName);
	}

	static DeclarationAnnotationElementAdapter<String[]> buildEnumArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new EnumArrayDeclarationAnnotationElementAdapter(annotationAdapter, elementName);
	}

}
