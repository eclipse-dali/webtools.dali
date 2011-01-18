/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkCacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTimeOfDayAnnotation;

/**
 * org.eclipse.persistence.annotations.Cache
 */
public final class SourceEclipseLinkCacheAnnotation
	extends SourceAnnotation<Type>
	implements EclipseLinkCacheAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> TYPE_ADAPTER = buildTypeAdapter();
	private final AnnotationElementAdapter<String> typeAdapter;
	private CacheType type;

	private static final DeclarationAnnotationElementAdapter<Integer> SIZE_ADAPTER = buildSizeAdapter();
	private final AnnotationElementAdapter<Integer> sizeAdapter;
	private Integer size;

	private static final DeclarationAnnotationElementAdapter<Boolean> SHARED_ADAPTER = buildSharedAdapter();
	private final AnnotationElementAdapter<Boolean> sharedAdapter;
	private Boolean shared;

	private static final DeclarationAnnotationElementAdapter<Boolean> ALWAYS_REFRESH_ADAPTER = buildAlwaysRefreshAdapter();
	private final AnnotationElementAdapter<Boolean> alwaysRefreshAdapter;
	private Boolean alwaysRefresh;

	private static final DeclarationAnnotationElementAdapter<Boolean> REFRESH_ONLY_IF_NEWER_ADAPTER = buildRefreshOnlyIfNewerAdapter();
	private final AnnotationElementAdapter<Boolean> refreshOnlyIfNewerAdapter;
	private Boolean refreshOnlyIfNewer;

	private static final DeclarationAnnotationElementAdapter<Boolean> DISABLE_HITS_ADAPTER = buildDisableHitsAdapter();
	private final AnnotationElementAdapter<Boolean> disableHitsAdapter;
	private Boolean disableHits;

	private static final DeclarationAnnotationElementAdapter<String> COORDINATION_TYPE_ADAPTER = buildCoordinationTypeAdapter();
	private final AnnotationElementAdapter<String> coordinationTypeAdapter;
	private CacheCoordinationType coordinationType;

	private static final DeclarationAnnotationElementAdapter<Integer> EXPIRY_ADAPTER = buildExpiryAdapter();
	private final AnnotationElementAdapter<Integer> expiryAdapter;
	private Integer expiry;

	private static final NestedDeclarationAnnotationAdapter EXPIRY_TIME_OF_DAY_ADAPTER = buildExpiryTimeOfDayAdapter();
	private final ElementAnnotationAdapter expiryTimeOfDayAdapter;
	private EclipseLinkTimeOfDayAnnotation expiryTimeOfDay;


	public SourceEclipseLinkCacheAnnotation(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.typeAdapter = new AnnotatedElementAnnotationElementAdapter<String>(type, TYPE_ADAPTER);
		this.sizeAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(type, SIZE_ADAPTER);
		this.sharedAdapter = new AnnotatedElementAnnotationElementAdapter<Boolean>(type, SHARED_ADAPTER);
		this.alwaysRefreshAdapter = new AnnotatedElementAnnotationElementAdapter<Boolean>(type, ALWAYS_REFRESH_ADAPTER);
		this.refreshOnlyIfNewerAdapter = new AnnotatedElementAnnotationElementAdapter<Boolean>(type, REFRESH_ONLY_IF_NEWER_ADAPTER);
		this.disableHitsAdapter = new AnnotatedElementAnnotationElementAdapter<Boolean>(type, DISABLE_HITS_ADAPTER);
		this.coordinationTypeAdapter = new AnnotatedElementAnnotationElementAdapter<String>(type, COORDINATION_TYPE_ADAPTER);
		this.expiryAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(type, EXPIRY_ADAPTER);
		this.expiryTimeOfDayAdapter = new ElementAnnotationAdapter(type, EXPIRY_TIME_OF_DAY_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.type = this.buildType(astRoot);
		this.size = this.buildSize(astRoot);
		this.shared = this.buildShared(astRoot);
		this.alwaysRefresh = this.buildAlwaysRefresh(astRoot);
		this.refreshOnlyIfNewer = this.buildRefreshOnlyIfNewer(astRoot);
		this.disableHits = this.buildDisableHits(astRoot);
		this.coordinationType = this.buildCoordinationType(astRoot);
		this.expiry = this.buildExpiry(astRoot);
		this.initializeExpiryTimeOfDay(astRoot);
	}

	private void initializeExpiryTimeOfDay(CompilationUnit astRoot) {
		if (this.expiryTimeOfDayAdapter.getAnnotation(astRoot) != null) {
			this.expiryTimeOfDay = this.buildExpiryTimeOfDay();
			this.expiryTimeOfDay.initialize(astRoot);
		}
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncType(this.buildType(astRoot));
		this.syncSize(this.buildSize(astRoot));
		this.syncShared(this.buildShared(astRoot));
		this.syncAlwaysRefresh(this.buildAlwaysRefresh(astRoot));
		this.syncRefreshOnlyIfNewer(this.buildRefreshOnlyIfNewer(astRoot));
		this.syncDisableHits(this.buildDisableHits(astRoot));
		this.syncCoordinationType(this.buildCoordinationType(astRoot));
		this.syncExpiry(this.buildExpiry(astRoot));
		this.syncExpiryTimeOfDay(astRoot);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.type == null) &&
				(this.size == null) &&
				(this.shared == null) &&
				(this.alwaysRefresh == null) &&
				(this.refreshOnlyIfNewer == null) &&
				(this.disableHits == null) &&
				(this.coordinationType == null) &&
				(this.expiry == null) &&
				(this.expiryTimeOfDay == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.type);
	}


	// ********** CacheAnnotation implementation **********

	// ***** type
	public CacheType getType() {
		return this.type;
	}

	public void setType(CacheType type) {
		if (this.attributeValueHasChanged(this.type, type)) {
			this.type = type;
			this.typeAdapter.setValue(CacheType.toJavaAnnotationValue(type));
		}
	}

	private void syncType(CacheType astType) {
		CacheType old = this.type;
		this.type = astType;
		this.firePropertyChanged(TYPE_PROPERTY, old, astType);
	}

	private CacheType buildType(CompilationUnit astRoot) {
		return CacheType.fromJavaAnnotationValue(this.typeAdapter.getValue(astRoot));
	}

	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(TYPE_ADAPTER, astRoot);
	}

	// ***** size
	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		if (this.attributeValueHasChanged(this.size, size)) {
			this.size = size;
			this.sizeAdapter.setValue(size);
		}
	}

	private void syncSize(Integer astSize) {
		Integer old = this.size;
		this.size = astSize;
		this.firePropertyChanged(SIZE_PROPERTY, old, astSize);
	}

	private Integer buildSize(CompilationUnit astRoot) {
		return this.sizeAdapter.getValue(astRoot);
	}

	public TextRange getSizeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SIZE_ADAPTER, astRoot);
	}

	// ***** shared
	public Boolean getShared() {
		return this.shared;
	}

	public void setShared(Boolean shared) {
		if (this.attributeValueHasChanged(this.shared, shared)) {
			this.shared = shared;
			this.sharedAdapter.setValue(shared);
		}
	}

	private void syncShared(Boolean astShared) {
		Boolean old = this.shared;
		this.shared = astShared;
		this.firePropertyChanged(SHARED_PROPERTY, old, astShared);
	}

	private Boolean buildShared(CompilationUnit astRoot) {
		return this.sharedAdapter.getValue(astRoot);
	}

	public TextRange getSharedTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SHARED_ADAPTER, astRoot);
	}

	// ***** always refresh
	public Boolean getAlwaysRefresh() {
		return this.alwaysRefresh;
	}

	public void setAlwaysRefresh(Boolean alwaysRefresh) {
		if (this.attributeValueHasChanged(this.alwaysRefresh, alwaysRefresh)) {
			this.alwaysRefresh = alwaysRefresh;
			this.alwaysRefreshAdapter.setValue(alwaysRefresh);
		}
	}

	private void syncAlwaysRefresh(Boolean astAlwaysRefresh) {
		Boolean old = this.alwaysRefresh;
		this.alwaysRefresh = astAlwaysRefresh;
		this.firePropertyChanged(ALWAYS_REFRESH_PROPERTY, old, astAlwaysRefresh);
	}

	private Boolean buildAlwaysRefresh(CompilationUnit astRoot) {
		return this.alwaysRefreshAdapter.getValue(astRoot);
	}

	public TextRange getAlwaysRefreshTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(ALWAYS_REFRESH_ADAPTER, astRoot);
	}

	// ***** refresh only if newer
	public Boolean getRefreshOnlyIfNewer() {
		return this.refreshOnlyIfNewer;
	}

	public void setRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer) {
		if (this.attributeValueHasChanged(this.refreshOnlyIfNewer, refreshOnlyIfNewer)) {
			this.refreshOnlyIfNewer = refreshOnlyIfNewer;
			this.refreshOnlyIfNewerAdapter.setValue(refreshOnlyIfNewer);
		}
	}

	private void syncRefreshOnlyIfNewer(Boolean astRefreshOnlyIfNewer) {
		Boolean old = this.refreshOnlyIfNewer;
		this.refreshOnlyIfNewer = astRefreshOnlyIfNewer;
		this.firePropertyChanged(REFRESH_ONLY_IF_NEWER_PROPERTY, old, astRefreshOnlyIfNewer);
	}

	private Boolean buildRefreshOnlyIfNewer(CompilationUnit astRoot) {
		return this.refreshOnlyIfNewerAdapter.getValue(astRoot);
	}

	public TextRange getRefreshOnlyIfNewerTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(REFRESH_ONLY_IF_NEWER_ADAPTER, astRoot);
	}

	// ***** disable hits
	public Boolean getDisableHits() {
		return this.disableHits;
	}

	public void setDisableHits(Boolean disableHits) {
		if (this.attributeValueHasChanged(this.disableHits, disableHits)) {
			this.disableHits = disableHits;
			this.disableHitsAdapter.setValue(disableHits);
		}
	}

	private void syncDisableHits(Boolean astDisableHits) {
		Boolean old = this.disableHits;
		this.disableHits = astDisableHits;
		this.firePropertyChanged(DISABLE_HITS_PROPERTY, old, astDisableHits);
	}

	private Boolean buildDisableHits(CompilationUnit astRoot) {
		return this.disableHitsAdapter.getValue(astRoot);
	}

	public TextRange getDisablesHitsTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(DISABLE_HITS_ADAPTER, astRoot);
	}

	// ***** coordination type
	public CacheCoordinationType getCoordinationType() {
		return this.coordinationType;
	}

	public void setCoordinationType(CacheCoordinationType coordinationType) {
		if (this.attributeValueHasChanged(this.coordinationType, coordinationType)) {
			this.coordinationType = coordinationType;
			this.coordinationTypeAdapter.setValue(CacheCoordinationType.toJavaAnnotationValue(coordinationType));
		}
	}

	private void syncCoordinationType(CacheCoordinationType astCoordinationType) {
		CacheCoordinationType old = this.coordinationType;
		this.coordinationType = astCoordinationType;
		this.firePropertyChanged(TYPE_PROPERTY, old, astCoordinationType);
	}

	private CacheCoordinationType buildCoordinationType(CompilationUnit astRoot) {
		return CacheCoordinationType.fromJavaAnnotationValue(this.coordinationTypeAdapter.getValue(astRoot));
	}

	public TextRange getCoordinationTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(COORDINATION_TYPE_ADAPTER, astRoot);
	}

	// ***** expiry
	public Integer getExpiry() {
		return this.expiry;
	}

	public void setExpiry(Integer expiry) {
		if (this.attributeValueHasChanged(this.expiry, expiry)) {
			this.expiry = expiry;
			this.expiryAdapter.setValue(expiry);
		}
	}

	private void syncExpiry(Integer astExpiry) {
		Integer old = this.expiry;
		this.expiry = astExpiry;
		this.firePropertyChanged(EXPIRY_PROPERTY, old, astExpiry);
	}

	private Integer buildExpiry(CompilationUnit astRoot) {
		return this.expiryAdapter.getValue(astRoot);
	}

	public TextRange getExpiryTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(EXPIRY_ADAPTER, astRoot);
	}

	// ***** expiry time of day
	public EclipseLinkTimeOfDayAnnotation getExpiryTimeOfDay() {
		return this.expiryTimeOfDay;
	}

	public EclipseLinkTimeOfDayAnnotation addExpiryTimeOfDay() {
		if (this.expiryTimeOfDay != null) {
			throw new IllegalStateException("'expiryTimeOfDay' element already exists: " + this.expiryTimeOfDay); //$NON-NLS-1$
		}
		this.expiryTimeOfDay = this.buildExpiryTimeOfDay();
		this.expiryTimeOfDay.newAnnotation();
		return this.expiryTimeOfDay;
	}

	public void removeExpiryTimeOfDay() {
		if (this.expiryTimeOfDay == null) {
			throw new IllegalStateException("'expiryTimeOfDay' element does not exist"); //$NON-NLS-1$
		}
		EclipseLinkTimeOfDayAnnotation old = this.expiryTimeOfDay;
		this.expiryTimeOfDay = null;
		old.removeAnnotation();
	}

	private EclipseLinkTimeOfDayAnnotation buildExpiryTimeOfDay() {
		return new SourceEclipseLinkTimeOfDayAnnotation(this, this.annotatedElement, EXPIRY_TIME_OF_DAY_ADAPTER);
	}

	private void syncExpiryTimeOfDay(CompilationUnit astRoot) {
		if (this.expiryTimeOfDayAdapter.getAnnotation(astRoot) == null) {
			this.syncExpiryTimeOfDay_(null);
		} else {
			if (this.expiryTimeOfDay == null) {
				EclipseLinkTimeOfDayAnnotation tod = this.buildExpiryTimeOfDay();
				tod.initialize(astRoot);
				this.syncExpiryTimeOfDay_(tod);
			} else {
				this.expiryTimeOfDay.synchronizeWith(astRoot);
			}
		}
	}

	private void syncExpiryTimeOfDay_(EclipseLinkTimeOfDayAnnotation astExpiryTimeOfDay) {
		EclipseLinkTimeOfDayAnnotation old = this.expiryTimeOfDay;
		this.expiryTimeOfDay = astExpiryTimeOfDay;
		this.firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, old, astExpiryTimeOfDay);
	}

	public TextRange getExpiryTimeOfDayTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.expiryTimeOfDayAdapter.getAstNode(astRoot));
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__TYPE);
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildSizeAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__SIZE, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildSharedAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__SHARED, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildExpiryAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__EXPIRY, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildAlwaysRefreshAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__ALWAYS_REFRESH, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildRefreshOnlyIfNewerAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__REFRESH_ONLY_IF_NEWER, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildDisableHitsAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__DISABLE_HITS, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildCoordinationTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__COORDINATION_TYPE);
	}

	private static NestedDeclarationAnnotationAdapter buildExpiryTimeOfDayAdapter() {
		return new NestedDeclarationAnnotationAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLink.CACHE__EXPIRY_TIME_OF_DAY, EclipseLink.TIME_OF_DAY);
	}

}
