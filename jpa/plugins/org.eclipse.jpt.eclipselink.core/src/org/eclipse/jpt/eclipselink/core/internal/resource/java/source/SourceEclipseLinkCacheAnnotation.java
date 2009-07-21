/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkCacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
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

	private static final DeclarationAnnotationElementAdapter<Integer> EXPIRY_ADAPTER = buildExpiryAdapter();
	private final AnnotationElementAdapter<Integer> expiryAdapter;
	private Integer expiry;

	private static final DeclarationAnnotationElementAdapter<Boolean> ALWAYS_REFRESH_ADAPTER = buildAlwaysRefreshAdapter();
	private final AnnotationElementAdapter<Boolean> alwaysRefreshAdapter;
	private EclipseLinkTimeOfDayAnnotation expiryTimeOfDay;

	private static final DeclarationAnnotationElementAdapter<Boolean> REFRESH_ONLY_IF_NEWER_ADAPTER = buildRefreshOnlyIfNewerAdapter();
	private final AnnotationElementAdapter<Boolean> refreshOnlyIfNewerAdapter;
	private Boolean alwaysRefresh;

	private static final DeclarationAnnotationElementAdapter<Boolean> DISABLE_HITS_ADAPTER = buildDisableHitsAdapter();
	private final AnnotationElementAdapter<Boolean> disableHitsAdapter;
	private Boolean refreshOnlyIfNewer;

	private static final DeclarationAnnotationElementAdapter<String> COORDINATION_TYPE_ADAPTER = buildCoordinationTypeAdapter();
	private final AnnotationElementAdapter<String> coordinationTypeAdapter;
	private Boolean disableHits;

	private static final NestedDeclarationAnnotationAdapter EXPIRY_TIME_OF_DAY_ADAPTER = buildExpiryTimeOfDayAdapter();
	private final MemberAnnotationAdapter expiryTimeOfDayAdapter;
	private CacheCoordinationType coordinationType;


	public SourceEclipseLinkCacheAnnotation(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.typeAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, TYPE_ADAPTER);
		this.sizeAdapter = new ShortCircuitAnnotationElementAdapter<Integer>(type, SIZE_ADAPTER);
		this.sharedAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(type, SHARED_ADAPTER);
		this.expiryAdapter = new ShortCircuitAnnotationElementAdapter<Integer>(type, EXPIRY_ADAPTER);
		this.alwaysRefreshAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(type, ALWAYS_REFRESH_ADAPTER);
		this.refreshOnlyIfNewerAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(type, REFRESH_ONLY_IF_NEWER_ADAPTER);
		this.disableHitsAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(type, DISABLE_HITS_ADAPTER);
		this.coordinationTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, COORDINATION_TYPE_ADAPTER);
		this.expiryTimeOfDayAdapter = new MemberAnnotationAdapter(type, EXPIRY_TIME_OF_DAY_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.type = this.buildType(astRoot);
		this.size = this.buildSize(astRoot);
		this.shared = this.buildShared(astRoot);
		this.expiry = this.buildExpiry(astRoot);
		this.initializeExpiryTimeOfDay(astRoot);
		this.alwaysRefresh = this.buildAlwaysRefresh(astRoot);
		this.refreshOnlyIfNewer = this.buildRefreshOnlyIfNewer(astRoot);
		this.disableHits = this.buildDisableHits(astRoot);
		this.coordinationType = this.buildCoordinationType(astRoot);
	}

	private void initializeExpiryTimeOfDay(CompilationUnit astRoot) {
		if (this.expiryTimeOfDayAdapter.getAnnotation(astRoot) != null) {
			this.expiryTimeOfDay = this.buildExpiryTimeOfDay();
			this.expiryTimeOfDay.initialize(astRoot);
		}
	}

	public void update(CompilationUnit astRoot) {
		this.setType(this.buildType(astRoot));
		this.setSize(this.buildSize(astRoot));
		this.setShared(this.buildShared(astRoot));
		this.setExpiry(this.buildExpiry(astRoot));
		this.updateExpiryTimeOfDay(astRoot);
		this.setAlwaysRefresh(this.buildAlwaysRefresh(astRoot));
		this.setRefreshOnlyIfNewer(this.buildRefreshOnlyIfNewer(astRoot));
		this.setDisableHits(this.buildDisableHits(astRoot));
		this.setCoordinationType(this.buildCoordinationType(astRoot));
	}

	private void updateExpiryTimeOfDay(CompilationUnit astRoot) {
		if (this.expiryTimeOfDayAdapter.getAnnotation(astRoot) == null) {
			this.setExpiryTimeOfDay(null);
		} else {
			if (this.getExpiryTimeOfDay() == null) {
				EclipseLinkTimeOfDayAnnotation etod = this.buildExpiryTimeOfDay();
				etod.initialize(astRoot);
				this.setExpiryTimeOfDay(etod);
			} else {
				this.getExpiryTimeOfDay().update(astRoot);
			}
		}
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
		if (this.attributeValueHasNotChanged(this.type, type)) {
			return;
		}
		CacheType old = this.type;
		this.type = type;
		this.typeAdapter.setValue(CacheType.toJavaAnnotationValue(type));
		this.firePropertyChanged(TYPE_PROPERTY, old, type);
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
		if (this.attributeValueHasNotChanged(this.size, size)) {
			return;
		}
		Integer old = this.size;
		this.size = size;
		this.sizeAdapter.setValue(size);
		this.firePropertyChanged(SIZE_PROPERTY, old, size);
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
		if (this.attributeValueHasNotChanged(this.shared, shared)) {
			return;
		}
		Boolean old = this.shared;
		this.shared = shared;
		this.sharedAdapter.setValue(shared);
		this.firePropertyChanged(SHARED_PROPERTY, old, shared);
	}

	private Boolean buildShared(CompilationUnit astRoot) {
		return this.sharedAdapter.getValue(astRoot);
	}

	public TextRange getSharedTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SHARED_ADAPTER, astRoot);
	}

	// ***** expiry
	public Integer getExpiry() {
		return this.expiry;
	}

	public void setExpiry(Integer expiry) {
		if (this.attributeValueHasNotChanged(this.expiry, expiry)) {
			return;
		}
		Integer old = this.expiry;
		this.expiry = expiry;
		this.expiryAdapter.setValue(expiry);
		this.firePropertyChanged(EXPIRY_PROPERTY, old, expiry);
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
			throw new IllegalStateException("'expiryTimeOfDay' element already exists"); //$NON-NLS-1$
		}
		this.expiryTimeOfDay = this.buildExpiryTimeOfDay();
		this.expiryTimeOfDayAdapter.newMarkerAnnotation();
		this.firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, null, this.expiryTimeOfDay);
		return this.expiryTimeOfDay;
	}

	public void removeExpiryTimeOfDay() {
		if (this.expiryTimeOfDay == null) {
			throw new IllegalStateException("No expiryTimeOfDay element exists"); //$NON-NLS-1$
		}
		this.expiryTimeOfDay = null;
		this.expiryTimeOfDayAdapter.removeAnnotation();
		this.firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, this.expiryTimeOfDay, null);
	}

	private void setExpiryTimeOfDay(EclipseLinkTimeOfDayAnnotation expiryTimeOfDay) {
		EclipseLinkTimeOfDayAnnotation old = this.expiryTimeOfDay;
		this.expiryTimeOfDay = expiryTimeOfDay;
		this.firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, old, expiryTimeOfDay);
	}

	private EclipseLinkTimeOfDayAnnotation buildExpiryTimeOfDay() {
		return new SourceEclipseLinkTimeOfDayAnnotation(this, this.member, EXPIRY_TIME_OF_DAY_ADAPTER);
	}

	public TextRange getExpiryTimeOfDayTextRange(CompilationUnit astRoot) {
		return null;//TODO return this.getElementTextRange(EXPIRY_TIME_OF_DAY_ADAPTER, astRoot);
	}

	// ***** always refresh
	public Boolean getAlwaysRefresh() {
		return this.alwaysRefresh;
	}

	public void setAlwaysRefresh(Boolean alwaysRefresh) {
		if (this.attributeValueHasNotChanged(this.alwaysRefresh, alwaysRefresh)) {
			return;
		}
		Boolean old = this.alwaysRefresh;
		this.alwaysRefresh = alwaysRefresh;
		this.alwaysRefreshAdapter.setValue(alwaysRefresh);
		this.firePropertyChanged(ALWAYS_REFRESH_PROPERTY, old, alwaysRefresh);
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
		if (this.attributeValueHasNotChanged(this.refreshOnlyIfNewer, refreshOnlyIfNewer)) {
			return;
		}
		Boolean old = this.refreshOnlyIfNewer;
		this.refreshOnlyIfNewer = refreshOnlyIfNewer;
		this.refreshOnlyIfNewerAdapter.setValue(refreshOnlyIfNewer);
		this.firePropertyChanged(REFRESH_ONLY_IF_NEWER_PROPERTY, old, refreshOnlyIfNewer);
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
		if (this.attributeValueHasNotChanged(this.disableHits, disableHits)) {
			return;
		}
		Boolean old = this.disableHits;
		this.disableHits = disableHits;
		this.disableHitsAdapter.setValue(disableHits);
		this.firePropertyChanged(DISABLE_HITS_PROPERTY, old, disableHits);
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
		if (this.attributeValueHasNotChanged(this.coordinationType, coordinationType)) {
			return;
		}
		CacheCoordinationType old = this.coordinationType;
		this.coordinationType = coordinationType;
		this.coordinationTypeAdapter.setValue(CacheCoordinationType.toJavaAnnotationValue(coordinationType));
		this.firePropertyChanged(TYPE_PROPERTY, old, coordinationType);
	}

	private CacheCoordinationType buildCoordinationType(CompilationUnit astRoot) {
		return CacheCoordinationType.fromJavaAnnotationValue(this.coordinationTypeAdapter.getValue(astRoot));
	}

	public TextRange getCoordinationTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(COORDINATION_TYPE_ADAPTER, astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__TYPE);
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildSizeAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__SIZE, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildSharedAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__SHARED, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildExpiryAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__EXPIRY, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildAlwaysRefreshAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__ALWAYS_REFRESH, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildRefreshOnlyIfNewerAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__REFRESH_ONLY_IF_NEWER, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Boolean> buildDisableHitsAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__DISABLE_HITS, BooleanExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildCoordinationTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__COORDINATION_TYPE);
	}

	private static NestedDeclarationAnnotationAdapter buildExpiryTimeOfDayAdapter() {
		return new NestedDeclarationAnnotationAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__EXPIRY_TIME_OF_DAY, EclipseLinkJPA.TIME_OF_DAY);
	}

}
