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
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IFetchable;
import org.eclipse.jpt.core.internal.context.base.INullable;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.Enumerated;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.Lob;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class JavaBasicMapping extends JavaAttributeMapping implements IJavaBasicMapping
{
	protected FetchType specifiedFetch;

	protected Boolean specifiedOptional;
	
	protected EnumType specifiedEnumerated;
	
	protected final IJavaColumn column;
	
	protected boolean lob;

	protected TemporalType temporal;

	public JavaBasicMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.column = createJavaColumn();
	}

	protected IJavaColumn createJavaColumn() {
		return jpaFactory().createJavaColumn(this, this);
	}

	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.column.initializeFromResource(this.columnResource());
		Basic basicResource = this.basicResource();
		this.specifiedFetch = this.specifiedFetchType(basicResource);
		this.specifiedOptional = this.specifiedOptional(basicResource);
		this.specifiedEnumerated = this.specifiedEnumerated(this.enumeratedResource());
		this.lob = this.lob(persistentAttributeResource);
		this.temporal = this.temporal(this.temporalResource());
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

	public Column columnResource() {
		return (Column) this.persistentAttributeResource.nonNullAnnotation(Column.ANNOTATION_NAME);
	}
	
	//************** IJavaAttributeMapping implementation ***************
	public String getKey() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public String annotationName() {
		return Basic.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.COLUMN,
			JPA.LOB,
			JPA.TEMPORAL,
			JPA.ENUMERATED);
	}
	
	public String defaultColumnName() {
		return attributeName();
	}

	public String defaultTableName() {
		return typeMapping().getTableName();
	}
	
	//************** IBasicMapping implementation ***************

	public IJavaColumn getColumn() {
		return this.column;
	}
	
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
		firePropertyChanged(IFetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(IFetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	public Boolean getOptional() {
		return (this.getSpecifiedOptional() == null) ? this.getDefaultOptional() : this.getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return INullable.DEFAULT_OPTIONAL;
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		this.basicResource().setOptional(newSpecifiedOptional);
		firePropertyChanged(INullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
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
		firePropertyChanged(IColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setTemporal_(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		firePropertyChanged(IColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
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
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedEnumerated_(EnumType newSpecifiedEnumerated) {
		EnumType oldEnumerated = this.specifiedEnumerated;
		this.specifiedEnumerated = newSpecifiedEnumerated;
		firePropertyChanged(IBasicMapping.SPECIFIED_ENUMERATED_PROPERTY, oldEnumerated, newSpecifiedEnumerated);
	}

	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.column.update(this.columnResource());
		Basic basicResource = this.basicResource();
		this.setSpecifiedFetch_(this.specifiedFetchType(basicResource));
		this.setSpecifiedOptional(this.specifiedOptional(basicResource));
		this.setSpecifiedEnumerated_(this.specifiedEnumerated(this.enumeratedResource()));
		this.setLob(this.lob(persistentAttributeResource));
		this.setTemporal_(this.temporal(this.temporalResource()));
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
		result = this.getColumn().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	// ************** Validation *************************************
	
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages ,astRoot);
		
		addColumnMessages(messages, astRoot);
	}
	
	protected void addColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		IJavaColumn column = this.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.isConnected();
		
		if (doContinue && this.typeMapping().tableNameIsInvalid(table)) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.tableTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.nameTextRange(astRoot))
				);
		}
	}
}
