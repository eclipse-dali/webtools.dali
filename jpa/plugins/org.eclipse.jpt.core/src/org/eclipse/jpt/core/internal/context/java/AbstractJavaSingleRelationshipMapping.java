/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.MapsId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaMapsId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java single relationship (1:1, m:1) mapping
 */
public abstract class AbstractJavaSingleRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<T>
	implements JavaSingleRelationshipMapping2_0
{
	protected Boolean specifiedOptional;

	protected final JavaDerivedId2_0 derivedId;
	
	protected final JavaMapsId2_0 mapsId;
	
	
	protected AbstractJavaSingleRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.derivedId = buildDerivedId();
		this.mapsId = buildMapsId();
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.specifiedOptional = this.getResourceOptional();
		this.derivedId.initialize();
		this.mapsId.initialize();
	}
	
	@Override
	protected void update() {
		super.update();
		this.setSpecifiedOptional_(this.getResourceOptional());
		this.derivedId.update();
		this.mapsId.update();
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.JOIN_COLUMN);
		names.add(JPA.JOIN_COLUMNS);
		names.add(JPA.JOIN_TABLE);
		if (this.getJpaPlatformVersion().isCompatibleWithJpaVersion(JptCorePlugin.JPA_FACET_VERSION_2_0)) {
			names.add(JPA.ID);
			names.add(JPA2_0.MAPS_ID);
		}
	}
	
	
	// ********** optional **********
	
	public boolean isOptional() {
		return (this.specifiedOptional != null) ? this.specifiedOptional.booleanValue() : this.isDefaultOptional();
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.setResourceOptional(optional);
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	protected void setSpecifiedOptional_(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	public boolean isDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}
	
	protected abstract Boolean getResourceOptional();
	
	protected abstract void setResourceOptional(Boolean newOptional);
	
	
	// ********** 2.0 derived id **********
	
	protected JavaDerivedId2_0 buildDerivedId() {
		return ((JpaFactory2_0) getJpaFactory()).buildJavaDerivedId(this);
	}
	
	public JavaDerivedId2_0 getDerivedId() {
		return this.derivedId;
	}
	
	@Override
	public boolean isIdMapping() {
		return this.derivedId.getValue();
	}
	
	
	// ********** 2.0 maps id **********
	
	protected JavaMapsId2_0 buildMapsId() {
		return ((JpaFactory2_0) getJpaFactory()).buildJavaMapsId(this);
	}
	
	public MapsId2_0 getMapsId() {
		return this.mapsId;
	}
	
	
	// ********** AbstractJavaRelationshipMapping implementation **********
	
	@Override
	protected String buildDefaultTargetEntity() {
		return this.getPersistentAttribute().getSingleReferenceEntityTypeName();
	}
	
	
	// ********** Fetchable implementation **********
	
	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.derivedId.validate(messages, reporter, astRoot);
	}
}
