/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.utility.internal.Filter;


public class JavaBasicMapping extends JavaAttributeMapping implements IJavaBasicMapping
{
	protected FetchType specifiedFetch;

	
//	protected static final DefaultTrueBoolean OPTIONAL_EDEFAULT = DefaultTrueBoolean.DEFAULT;
//
//	protected DefaultTrueBoolean optional = OPTIONAL_EDEFAULT;
//
//	protected IColumn column;
//
//	protected static final boolean LOB_EDEFAULT = false;
//
//	protected boolean lob = LOB_EDEFAULT;
//
//	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.NULL;
//	protected TemporalType temporal = TEMPORAL_EDEFAULT;
//
//	protected static final EnumType ENUMERATED_EDEFAULT = EnumType.DEFAULT;
//
//	protected EnumType enumerated = ENUMERATED_EDEFAULT;

	public JavaBasicMapping(IJavaPersistentAttribute parent) {
		super(parent);
//		this.column = JavaColumn.createColumnMappingColumn(buildColumnOwner(), getAttribute());
//		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_BASIC__COLUMN, null, null);
//		this.optionalAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, OPTIONAL_ADAPTER);
//		this.fetchAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, FETCH_ADAPTER);
//		this.lobAdapter = new SimpleBooleanAnnotationAdapter(new MemberAnnotationAdapter(attribute, LOB_ADAPTER));
//		this.temporalAnnotationAdapter = new MemberAnnotationAdapter(this.getAttribute(), TEMPORAL_ADAPTER);
//		this.temporalValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, TEMPORAL_VALUE_ADAPTER);
//		this.enumeratedAnnotationAdapter = new MemberAnnotationAdapter(this.getAttribute(), ENUMERATED_ADAPTER);
//		this.enumeratedValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, ENUMERATED_VALUE_ADAPTER);
	}

	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		Basic basicResource = this.basicResource();
		this.specifiedFetch = this.fetchType(basicResource);
	}
	
	protected Basic basicResource() {
		return (Basic) this.persistentAttributeResource.nonNullMappingAnnotation(annotationName());
	}

	//************** IJavaAttributeMapping implementation ***************
	public String getKey() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}


	public String annotationName() {
		return Basic.ANNOTATION_NAME;
	}

	//************** IBasicMapping implementation ***************

//	public IColumn getColumn() {
//		return column;
//	}
//
//	public NotificationChain basicSetColumn(IColumn newColumn, NotificationChain msgs) {
//		IColumn oldColumn = column;
//		column = newColumn;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__COLUMN, oldColumn, newColumn);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
	
	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return IBasicMapping.DEFAULT_FETCH_TYPE;
	}
		
	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.basicResource().setFetch(FetchType.toJavaResourceModel(newSpecifiedFetch));
		firePropertyChanged(IBasicMapping.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	
//	public DefaultTrueBoolean getOptional() {
//		return optional;
//	}
//
//	public void setOptional(DefaultTrueBoolean newOptional) {
//		DefaultTrueBoolean oldOptional = optional;
//		optional = newOptional == null ? OPTIONAL_EDEFAULT : newOptional;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__OPTIONAL, oldOptional, optional));
//	}
//
//	public boolean isLob() {
//		return lob;
//	}
//
//	public void setLob(boolean newLob) {
//		boolean oldLob = lob;
//		lob = newLob;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__LOB, oldLob, lob));
//	}
//
//	public TemporalType getTemporal() {
//		return temporal;
//	}
//
//	public void setTemporal(TemporalType newTemporal) {
//		TemporalType oldTemporal = temporal;
//		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__TEMPORAL, oldTemporal, temporal));
//	}
//
//
//	public EnumType getEnumerated() {
//		return enumerated;
//	}
//
//	public void setEnumerated(EnumType newEnumerated) {
//		EnumType oldEnumerated = enumerated;
//		enumerated = newEnumerated == null ? ENUMERATED_EDEFAULT : newEnumerated;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_BASIC__ENUMERATED, oldEnumerated, enumerated));
//	}
	
	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		Basic basicResource = basicResource();
		this.setSpecifiedFetch(this.fetchType(basicResource));
	}
	
	
	protected FetchType fetchType(Basic basic) {
		return FetchType.fromJavaResourceModel(basic.getFetch());
	}

//	@Override
//	public void updateFromJava(CompilationUnit astRoot) {
//		super.updateFromJava(astRoot);
//		this.setOptional(DefaultTrueBoolean.fromJavaAnnotationValue(this.optionalAdapter.getValue(astRoot)));
//		this.setFetch(DefaultEagerFetchType.fromJavaAnnotationValue(this.fetchAdapter.getValue(astRoot)));
//		this.getJavaColumn().updateFromJava(astRoot);
//		this.setLob(this.lobAdapter.getValue(astRoot));
//		this.updateTemporalFromJava(astRoot);
//		this.updateEnumeratedFromJava(astRoot);
//	}
//
//	private JavaColumn getJavaColumn() {
//		return (JavaColumn) this.column;
//	}
//
//	/*
//	 * The @Temporal annotation is a bit different than most JPA annotations.
//	 * For some indecipherable reason it has no default value (e.g. TIMESTAMP).
//	 * Also, it is *required* for any attribute declared with a type of
//	 * java.util.Date or java.util.Calendar; otherwise, it is *prohibited*.
//	 * As a result we allow a Basic mapping to have a null 'temporal',
//	 * indicating that the annotation is completely missing, as opposed
//	 * to the annotation being present but its value is invalid (e.g.
//	 * @Temporal(FRIDAY)).
//	 * 
//	 * TODO this comment is wrong now, revisit this with Brian at some point
//	 */
//	private void updateTemporalFromJava(CompilationUnit astRoot) {
//		if (this.temporalAnnotationAdapter.getAnnotation(astRoot) == null) {
//			this.setTemporal(TemporalType.NULL);
//		}
//		else {
//			this.setTemporal(TemporalType.fromJavaAnnotationValue(this.temporalValueAdapter.getValue(astRoot)));
//		}
//	}
//
//	private void updateEnumeratedFromJava(CompilationUnit astRoot) {
//		if (this.enumeratedAnnotationAdapter.getAnnotation(astRoot) == null) {
//			this.setEnumerated(EnumType.DEFAULT);
//		}
//		else {
//			this.setEnumerated(EnumType.fromJavaAnnotationValue(this.enumeratedValueAdapter.getValue(astRoot)));
//		}
//	}


	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
//		result = this.getJavaColumn().candidateValuesFor(pos, filter, astRoot);
//		if (result != null) {
//			return result;
//		}
		return null;
	}
}
