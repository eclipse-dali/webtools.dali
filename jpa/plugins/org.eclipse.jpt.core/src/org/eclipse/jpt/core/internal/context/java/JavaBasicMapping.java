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
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Enumerated;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.Lob;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.utility.internal.Filter;


public class JavaBasicMapping extends JavaAttributeMapping implements IJavaBasicMapping
{
	protected FetchType specifiedFetch;

	protected Boolean specifiedOptional;
	
	protected EnumType specifiedEnumerated;
	
//	protected IColumn column;
	
	protected boolean lob;

	protected TemporalType temporal;

	public JavaBasicMapping(IJavaPersistentAttribute parent) {
		super(parent);
//		this.column = JavaColumn.createColumnMappingColumn(buildColumnOwner(), getAttribute());
//		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_BASIC__COLUMN, null, null);
	}

	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		Basic basicResource = this.basicResource();
		this.specifiedFetch = this.specifiedFetchType(basicResource);
		this.specifiedOptional = this.specifiedOptional(basicResource);
		this.specifiedEnumerated = this.specifiedEnumerated(enumeratedResource());
		this.lob = this.lob(persistentAttributeResource);
		this.temporal = this.temporal(temporalResource());
	}
	
	protected Basic basicResource() {
		return (Basic) this.persistentAttributeResource.nonNullMappingAnnotation(annotationName());
	}
	
	protected Enumerated enumeratedResource() {
		return (Enumerated) this.persistentAttributeResource.nonNullAnnotation(Enumerated.ANNOTATION_NAME);
	}
	
	protected Temporal temporalResource() {
		return (Temporal) this.persistentAttributeResource.nonNullAnnotation(Temporal.ANNOTATION_NAME);
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

	public Boolean getOptional() {
		return (this.getSpecifiedOptional() == null) ? this.getDefaultOptional() : this.getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return IBasicMapping.DEFAULT_OPTIONAL;
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		this.basicResource().setOptional(newSpecifiedOptional);
		firePropertyChanged(IBasicMapping.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}


	public boolean isLob() {
		return this.lob;
	}

	public void setLob(boolean newLob) {
		boolean oldLob = this.lob;
		this.lob = newLob;
		if (newLob) {
			if (lobResource(this.persistentAttributeResource) == null) {
				this.persistentAttributeResource.addAnnotation(Lob.ANNOTATION_NAME);
			}
		}
		else {
			if (lobResource(this.persistentAttributeResource) != null) {
				this.persistentAttributeResource.removeAnnotation(Lob.ANNOTATION_NAME);
			}
		}
		firePropertyChanged(IBasicMapping.LOB_PROPERTY, oldLob, newLob);
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.temporalResource().setValue(TemporalType.toJavaResourceModel(newTemporal));
		firePropertyChanged(IBasicMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	public EnumType getEnumerated() {
		return (this.getSpecifiedEnumerated() == null) ? this.getDefaultEnumerated() : this.getSpecifiedEnumerated();
	}
	
	public EnumType getDefaultEnumerated() {
		return IBasicMapping.DEFAULT_ENUMERATED;
	}
	
	public EnumType getSpecifiedEnumerated() {
		return this.specifiedEnumerated;
	}
	
	public void setSpecifiedEnumerated(EnumType newSpecifiedEnumerated) {
		EnumType oldEnumerated = this.specifiedEnumerated;
		this.specifiedEnumerated = newSpecifiedEnumerated;
		this.enumeratedResource().setValue(EnumType.toJavaResourceModel(newSpecifiedEnumerated));
		firePropertyChanged(IBasicMapping.SPECIFIED_ENUMERATED_PROPERTY, oldEnumerated, newSpecifiedEnumerated);
	}

	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		Basic basicResource = basicResource();
		this.setSpecifiedFetch(this.specifiedFetchType(basicResource));
		this.setSpecifiedOptional(this.specifiedOptional(basicResource));
		this.setSpecifiedEnumerated(this.specifiedEnumerated(enumeratedResource()));
		this.setLob(this.lob(persistentAttributeResource));
		this.setTemporal(this.temporal(temporalResource()));
	}
	
	
	protected FetchType specifiedFetchType(Basic basic) {
		return FetchType.fromJavaResourceModel(basic.getFetch());
	}
	
	protected Boolean specifiedOptional(Basic basic) {
		return basic.getOptional();
	}
	
	protected EnumType specifiedEnumerated(Enumerated enumerated) {
		return EnumType.fromJavaResourceModel(enumerated.getValue());
	}
	
	protected boolean lob(JavaPersistentAttributeResource persistentAttributeResource) {
		return lobResource(persistentAttributeResource) != null;
	}
	
	protected Lob lobResource(JavaPersistentAttributeResource persistentAttributeResource) {
		return (Lob) persistentAttributeResource.annotation(Lob.ANNOTATION_NAME);
	}
	
	protected TemporalType temporal(Temporal temporal) {
		return TemporalType.fromJavaResourceModel(temporal.getValue());
	}


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
