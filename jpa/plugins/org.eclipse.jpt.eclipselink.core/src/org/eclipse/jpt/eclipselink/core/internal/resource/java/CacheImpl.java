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
import org.eclipse.jpt.eclipselink.core.resource.java.CacheType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;


public class CacheImpl extends AbstractResourceAnnotation<Type> implements CacheAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> typeAdapter;
	private final AnnotationElementAdapter<Boolean> sharedAdapter;
	private final AnnotationElementAdapter<Boolean> alwaysRefreshAdapter;
	private final AnnotationElementAdapter<Boolean> refreshOnlyIfNewerAdapter;
	private final AnnotationElementAdapter<Boolean> disableHitsAdapter;
	
	private static final DeclarationAnnotationElementAdapter<String> TYPE_ADAPTER = buildTypeAdapter();
	private static final DeclarationAnnotationElementAdapter<Boolean> SHARED_ADAPTER = buildSharedAdapter();
	private static final DeclarationAnnotationElementAdapter<Boolean> ALWAYS_REFRESH_ADAPTER = buildAlwaysRefreshAdapter();
	private static final DeclarationAnnotationElementAdapter<Boolean> REFRESH_ONLY_IF_NEWER_ADAPTER = buildRefreshOnlyIfNewerAdapter();
	private static final DeclarationAnnotationElementAdapter<Boolean> DISABLE_HITS_ADAPTER = buildDisableHitsAdapter();
	
	
	private CacheType type;
	private Boolean shared;
	private Boolean alwaysRefresh;
	private Boolean refreshOnlyIfNewer;
	private Boolean disableHits;
	
	protected CacheImpl(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.typeAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, TYPE_ADAPTER);
		this.sharedAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(type, SHARED_ADAPTER);
		this.alwaysRefreshAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(type, ALWAYS_REFRESH_ADAPTER);
		this.refreshOnlyIfNewerAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(type, REFRESH_ONLY_IF_NEWER_ADAPTER);
		this.disableHitsAdapter = new ShortCircuitAnnotationElementAdapter<Boolean>(type, DISABLE_HITS_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.type = this.type(astRoot);
		this.shared = this.shared(astRoot);
		this.alwaysRefresh = this.alwaysRefresh(astRoot);
		this.refreshOnlyIfNewer = this.refreshOnlyIfNewer(astRoot);
		this.disableHits = this.disableHits(astRoot);
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
	
	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(TYPE_ADAPTER, astRoot);
	}
	
	public TextRange getSharedTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SHARED_ADAPTER, astRoot);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		this.setType(this.type(astRoot));
		this.setShared(this.shared(astRoot));
		this.setAlwaysRefresh(this.alwaysRefresh(astRoot));
		this.setRefreshOnlyIfNewer(this.refreshOnlyIfNewer(astRoot));
		this.setDisableHits(this.disableHits(astRoot));
	}
	
	protected CacheType type(CompilationUnit astRoot) {
		return CacheType.fromJavaAnnotationValue(this.typeAdapter.getValue(astRoot));
	}
	
	protected Boolean shared(CompilationUnit astRoot) {
		return this.sharedAdapter.getValue(astRoot);
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
	
	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__TYPE, false);
	}
	
	private static DeclarationAnnotationElementAdapter<Boolean> buildSharedAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__SHARED, false, BooleanExpressionConverter.instance());
	}
	
	private static DeclarationAnnotationElementAdapter<Boolean> buildAlwaysRefreshAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__ALWAYS_REFRESH, false, BooleanExpressionConverter.instance());
	}
	
	private static DeclarationAnnotationElementAdapter<Boolean> buildRefreshOnlyIfNewerAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__REFRESH_ONLY_IF_NEWER, false, BooleanExpressionConverter.instance());
	}
	
	private static DeclarationAnnotationElementAdapter<Boolean> buildDisableHitsAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.CACHE__DISABLE_HITS, false, BooleanExpressionConverter.instance());
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
			return new NullCacheAnnotation(parent);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
