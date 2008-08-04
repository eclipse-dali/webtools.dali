/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractResourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.TimeOfDayAnnotation;


public class CacheImpl extends AbstractResourceAnnotation<Type> implements CacheAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> typeAdapter;
	private final AnnotationElementAdapter<Integer> sizeAdapter;
	private final AnnotationElementAdapter<Boolean> sharedAdapter;
	private final AnnotationElementAdapter<Integer> expiryAdapter;
	private final AnnotationElementAdapter<Boolean> alwaysRefreshAdapter;
	private final AnnotationElementAdapter<Boolean> refreshOnlyIfNewerAdapter;
	private final AnnotationElementAdapter<Boolean> disableHitsAdapter;
	private final AnnotationElementAdapter<String> coordinationTypeAdapter;
	private final MemberAnnotationAdapter expiryTimeOfDayAdapter;
	
	private static final DeclarationAnnotationElementAdapter<String> TYPE_ADAPTER = buildTypeAdapter();
	private static final DeclarationAnnotationElementAdapter<Integer> SIZE_ADAPTER = buildSizeAdapter();
	private static final DeclarationAnnotationElementAdapter<Boolean> SHARED_ADAPTER = buildSharedAdapter();
	private static final DeclarationAnnotationElementAdapter<Integer> EXPIRY_ADAPTER = buildExpiryAdapter();
	private static final DeclarationAnnotationElementAdapter<Boolean> ALWAYS_REFRESH_ADAPTER = buildAlwaysRefreshAdapter();
	private static final DeclarationAnnotationElementAdapter<Boolean> REFRESH_ONLY_IF_NEWER_ADAPTER = buildRefreshOnlyIfNewerAdapter();
	private static final DeclarationAnnotationElementAdapter<Boolean> DISABLE_HITS_ADAPTER = buildDisableHitsAdapter();
	private static final DeclarationAnnotationElementAdapter<String> COORDINATION_TYPE_ADAPTER = buildCoordinationTypeAdapter();
	private static final NestedDeclarationAnnotationAdapter EXPIRY_TIME_OF_DAY_ADAPTER = buildExpiryTimeOfDayAnnotationAdapter();
	
	
	private CacheType type;
	private Integer size;
	private Boolean shared;
	private Integer expiry;
	private TimeOfDayAnnotation expiryTimeOfDay;
	private Boolean alwaysRefresh;
	private Boolean refreshOnlyIfNewer;
	private Boolean disableHits;
	private CacheCoordinationType coordinationType;
	
	protected CacheImpl(JavaResourcePersistentType parent, Type type) {
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
	
	//*************** CacheAnnotation implementation ****************

	public CacheType getType() {
		return this.type;
	}
	
	public void setType(CacheType newType) {
		if (attributeValueHasNotChanged(this.type, newType)) {
			return;
		}
		CacheType oldType = this.type;
		this.type = newType;
		this.typeAdapter.setValue(CacheType.toJavaAnnotationValue(newType));
		firePropertyChanged(TYPE_PROPERTY, oldType, newType);
	}
	
	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer newSize) {
		if (attributeValueHasNotChanged(this.size, newSize)) {
			return;
		}
		Integer oldSize = this.size;
		this.size = newSize;
		this.sizeAdapter.setValue(newSize);
		firePropertyChanged(SIZE_PROPERTY, oldSize, newSize);
	}

	public Boolean getShared() {
		return this.shared;
	}
	
	public void setShared(Boolean newShared) {
		if (attributeValueHasNotChanged(this.shared, newShared)) {
			return;
		}
		Boolean oldShared = this.shared;
		this.shared = newShared;
		this.sharedAdapter.setValue(newShared);
		firePropertyChanged(SHARED_PROPERTY, oldShared, newShared);
	}
	
	public Integer getExpiry() {
		return this.expiry;
	}

	public void setExpiry(Integer newExpiry) {
		if (attributeValueHasNotChanged(this.expiry, newExpiry)) {
			return;
		}
		Integer oldExpiry = this.expiry;
		this.expiry = newExpiry;
		this.expiryAdapter.setValue(newExpiry);
		firePropertyChanged(EXPIRY_PROPERTY, oldExpiry, newExpiry);
	}
	
	public TimeOfDayAnnotation getExpiryTimeOfDay() {
		return this.expiryTimeOfDay;
	}
	
	protected void setExpiryTimeOfDay(TimeOfDayAnnotation newExpiryTimeOfDay) {
		TimeOfDayAnnotation oldExpiryTimeOfDay = this.expiryTimeOfDay;
		this.expiryTimeOfDay = newExpiryTimeOfDay;
		firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, oldExpiryTimeOfDay, newExpiryTimeOfDay);
	}
	
	public Boolean getAlwaysRefresh() {
		return this.alwaysRefresh;
	}
	
	public void setAlwaysRefresh(Boolean newAlwaysRefresh) {
		if (attributeValueHasNotChanged(this.alwaysRefresh, newAlwaysRefresh)) {
			return;
		}
		Boolean oldAlwaysRefresh = this.alwaysRefresh;
		this.alwaysRefresh = newAlwaysRefresh;
		this.alwaysRefreshAdapter.setValue(newAlwaysRefresh);
		firePropertyChanged(ALWAYS_REFRESH_PROPERTY, oldAlwaysRefresh, newAlwaysRefresh);
	}
	
	public Boolean getRefreshOnlyIfNewer() {
		return this.refreshOnlyIfNewer;
	}
	
	public void setRefreshOnlyIfNewer(Boolean newRefreshOnlyIfNewer) {
		if (attributeValueHasNotChanged(this.refreshOnlyIfNewer, newRefreshOnlyIfNewer)) {
			return;
		}
		Boolean oldRefreshOnlyIfNewer = this.refreshOnlyIfNewer;
		this.refreshOnlyIfNewer = newRefreshOnlyIfNewer;
		this.refreshOnlyIfNewerAdapter.setValue(newRefreshOnlyIfNewer);
		firePropertyChanged(REFRESH_ONLY_IF_NEWER_PROPERTY, oldRefreshOnlyIfNewer, newRefreshOnlyIfNewer);
	}
	
	public Boolean getDisableHits() {
		return this.disableHits;
	}
	
	public void setDisableHits(Boolean newDisableHits) {
		if (attributeValueHasNotChanged(this.disableHits, newDisableHits)) {
			return;
		}
		Boolean oldDisableHits = this.disableHits;
		this.disableHits = newDisableHits;
		this.disableHitsAdapter.setValue(newDisableHits);
		firePropertyChanged(DISABLE_HITS_PROPERTY, oldDisableHits, newDisableHits);
	}
	
	public CacheCoordinationType getCoordinationType() {
		return this.coordinationType;
	}
	
	public void setCoordinationType(CacheCoordinationType newCoordinationType) {
		if (attributeValueHasNotChanged(this.coordinationType, newCoordinationType)) {
			return;
		}
		CacheCoordinationType oldCoordinationType = this.coordinationType;
		this.coordinationType = newCoordinationType;
		this.coordinationTypeAdapter.setValue(CacheCoordinationType.toJavaAnnotationValue(newCoordinationType));
		firePropertyChanged(TYPE_PROPERTY, oldCoordinationType, newCoordinationType);
	}
	
	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(TYPE_ADAPTER, astRoot);
	}
	
	public TextRange getSizeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SIZE_ADAPTER, astRoot);
	}
	
	public TextRange getSharedTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SHARED_ADAPTER, astRoot);
	}
	
	public TextRange getExpiryTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(EXPIRY_ADAPTER, astRoot);
	}
	
	public TextRange getExpiryTimeOfDayTextRange(CompilationUnit astRoot) {
		return null;//TODO return this.getElementTextRange(EXPIRY_TIME_OF_DAY_ADAPTER, astRoot);
	}
	
	public TextRange getAlwaysRefreshTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(ALWAYS_REFRESH_ADAPTER, astRoot);
	}
	
	public TextRange getRefreshOnlyIfNewerTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(REFRESH_ONLY_IF_NEWER_ADAPTER, astRoot);
	}
	
	public TextRange getDisablesHitsTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(DISABLE_HITS_ADAPTER, astRoot);
	}
	
	public TextRange getCoordinationTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(COORDINATION_TYPE_ADAPTER, astRoot);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.type = this.type(astRoot);
		this.size = this.size(astRoot);
		this.shared = this.shared(astRoot);
		this.expiry = this.expiry(astRoot);
		this.initializeExpiryTimeOfDay(astRoot);
		this.alwaysRefresh = this.alwaysRefresh(astRoot);
		this.refreshOnlyIfNewer = this.refreshOnlyIfNewer(astRoot);
		this.disableHits = this.disableHits(astRoot);
		this.coordinationType = this.coordinationType(astRoot);
	}

	protected void initializeExpiryTimeOfDay(CompilationUnit astRoot) {
		if (this.expiryTimeOfDayAdapter.getAnnotation(astRoot) != null) {
			this.expiryTimeOfDay = createTimeOfDayAnnotation();
			this.expiryTimeOfDay.initialize(astRoot);
		}
	}

	public void update(CompilationUnit astRoot) {
		this.setType(this.type(astRoot));
		this.setSize(this.size(astRoot));
		this.setShared(this.shared(astRoot));
		this.setExpiry(this.expiry(astRoot));
		this.updateExpiryTimeOfDay(astRoot);
		this.setAlwaysRefresh(this.alwaysRefresh(astRoot));
		this.setRefreshOnlyIfNewer(this.refreshOnlyIfNewer(astRoot));
		this.setDisableHits(this.disableHits(astRoot));
		this.setCoordinationType(this.coordinationType(astRoot));
	}
	
	protected void updateExpiryTimeOfDay(CompilationUnit astRoot) {
		if (this.expiryTimeOfDayAdapter.getAnnotation(astRoot) == null) {
			this.setExpiryTimeOfDay(null);
		}
		else {
			if (getExpiryTimeOfDay() != null) {
				getExpiryTimeOfDay().update(astRoot);
			}
			else {
				TimeOfDayAnnotation expiryTimeOfDay = createTimeOfDayAnnotation();
				expiryTimeOfDay.initialize(astRoot);
				setExpiryTimeOfDay(expiryTimeOfDay);
			}
		}
	}
	
	protected CacheType type(CompilationUnit astRoot) {
		return CacheType.fromJavaAnnotationValue(this.typeAdapter.getValue(astRoot));
	}
	
	protected Integer size(CompilationUnit astRoot) {
		return this.sizeAdapter.getValue(astRoot);
	}
	
	protected Boolean shared(CompilationUnit astRoot) {
		return this.sharedAdapter.getValue(astRoot);
	}
	
	protected Integer expiry(CompilationUnit astRoot) {
		return this.expiryAdapter.getValue(astRoot);
	}
	
	protected TimeOfDayAnnotation createTimeOfDayAnnotation() {
		return new TimeOfDayImpl(this, getMember(), EXPIRY_TIME_OF_DAY_ADAPTER);
	}

	protected Boolean alwaysRefresh(CompilationUnit astRoot) {
		return this.alwaysRefreshAdapter.getValue(astRoot);
	}
	
	protected Boolean refreshOnlyIfNewer(CompilationUnit astRoot) {
		return this.refreshOnlyIfNewerAdapter.getValue(astRoot);
	}
	
	protected Boolean disableHits(CompilationUnit astRoot) {
		return this.disableHitsAdapter.getValue(astRoot);
	}
	
	protected CacheCoordinationType coordinationType(CompilationUnit astRoot) {
		return CacheCoordinationType.fromJavaAnnotationValue(this.coordinationTypeAdapter.getValue(astRoot));
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

	private static NestedDeclarationAnnotationAdapter buildExpiryTimeOfDayAnnotationAdapter() {
		return new NestedDeclarationAnnotationAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__EXPIRY_TIME_OF_DAY, EclipseLinkJPA.TIME_OF_DAY);
	}
	
	public static class CacheAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final CacheAnnotationDefinition INSTANCE = new CacheAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static CacheAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private CacheAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new CacheImpl((JavaResourcePersistentType) parent, (Type) member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new NullCacheAnnotation((JavaResourcePersistentType) parent);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
