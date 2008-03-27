/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.LobAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaBasicMapping extends AbstractJavaAttributeMapping<BasicAnnotation> implements JavaBasicMapping
{
	protected FetchType specifiedFetch;

	protected Boolean specifiedOptional;
	
	protected EnumType specifiedEnumerated;
	
	protected final JavaColumn column;
	
	protected boolean lob;

	protected TemporalType temporal;

	public GenericJavaBasicMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = createJavaColumn();
	}

	protected JavaColumn createJavaColumn() {
		return getJpaFactory().buildJavaColumn(this, this);
	}

	@Override
	public void initializeFromResource(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super.initializeFromResource(resourcePersistentAttribute);
		this.column.initializeFromResource(this.getColumnResource());
		this.specifiedEnumerated = this.specifiedEnumerated(this.getEnumeratedResource());
		this.lob = this.lob(resourcePersistentAttribute);
		this.temporal = this.temporal(this.getTemporalResource());
	}
	
	@Override
	protected void initialize(BasicAnnotation basicResource) {
		this.specifiedFetch = this.specifiedFetchType(basicResource);
		this.specifiedOptional = this.specifiedOptional(basicResource);
	}
	
	protected EnumeratedAnnotation getEnumeratedResource() {
		return (EnumeratedAnnotation) getResourcePersistentAttribute().getNonNullAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
	}
	
	protected TemporalAnnotation getTemporalResource() {
		return (TemporalAnnotation) getResourcePersistentAttribute().getNonNullAnnotation(TemporalAnnotation.ANNOTATION_NAME);
	}

	public ColumnAnnotation getColumnResource() {
		return (ColumnAnnotation) getResourcePersistentAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}
	
	//************** IJavaAttributeMapping implementation ***************
	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return BasicAnnotation.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.COLUMN,
			JPA.LOB,
			JPA.TEMPORAL,
			JPA.ENUMERATED);
	}
	
	public String getDefaultColumnName() {
		return getAttributeName();
	}

	public String getDefaultTableName() {
		return getTypeMapping().getTableName();
	}
	
	//************** IBasicMapping implementation ***************

	public JavaColumn getColumn() {
		return this.column;
	}
	
	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return BasicMapping.DEFAULT_FETCH_TYPE;
	}
		
	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.getMappingResource().setFetch(FetchType.toJavaResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
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
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	public Boolean getOptional() {
		return (this.getSpecifiedOptional() == null) ? this.getDefaultOptional() : this.getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		this.getMappingResource().setOptional(newSpecifiedOptional);
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}

	protected void setSpecifiedOptional_(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}

	public boolean isLob() {
		return this.lob;
	}

	public void setLob(boolean newLob) {
		boolean oldLob = this.lob;
		this.lob = newLob;
		if (newLob) {
			if (lobResource(getResourcePersistentAttribute()) == null) {
				getResourcePersistentAttribute().addAnnotation(LobAnnotation.ANNOTATION_NAME);
			}
		}
		else {
			if (lobResource(getResourcePersistentAttribute()) != null) {
				getResourcePersistentAttribute().removeAnnotation(LobAnnotation.ANNOTATION_NAME);
			}
		}
		firePropertyChanged(BasicMapping.LOB_PROPERTY, oldLob, newLob);
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.getTemporalResource().setValue(TemporalType.toJavaResourceModel(newTemporal));
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
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
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	public EnumType getEnumerated() {
		return (this.getSpecifiedEnumerated() == null) ? this.getDefaultEnumerated() : this.getSpecifiedEnumerated();
	}
	
	public EnumType getDefaultEnumerated() {
		return BasicMapping.DEFAULT_ENUMERATED;
	}
	
	public EnumType getSpecifiedEnumerated() {
		return this.specifiedEnumerated;
	}
	
	public void setSpecifiedEnumerated(EnumType newSpecifiedEnumerated) {
		EnumType oldEnumerated = this.specifiedEnumerated;
		this.specifiedEnumerated = newSpecifiedEnumerated;
		this.getEnumeratedResource().setValue(EnumType.toJavaResourceModel(newSpecifiedEnumerated));
		firePropertyChanged(BasicMapping.SPECIFIED_ENUMERATED_PROPERTY, oldEnumerated, newSpecifiedEnumerated);
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
		firePropertyChanged(BasicMapping.SPECIFIED_ENUMERATED_PROPERTY, oldEnumerated, newSpecifiedEnumerated);
	}

	@Override
	public void update(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super.update(resourcePersistentAttribute);
		this.column.update(this.getColumnResource());
		this.setSpecifiedEnumerated_(this.specifiedEnumerated(this.getEnumeratedResource()));
		this.setLob(this.lob(resourcePersistentAttribute));
		this.setTemporal_(this.temporal(this.getTemporalResource()));
	}
	
	@Override
	protected void update(BasicAnnotation basicResource) {
		this.setSpecifiedFetch_(this.specifiedFetchType(basicResource));
		this.setSpecifiedOptional_(this.specifiedOptional(basicResource));
	}
	
	protected FetchType specifiedFetchType(BasicAnnotation basic) {
		return FetchType.fromJavaResourceModel(basic.getFetch());
	}
	
	protected Boolean specifiedOptional(BasicAnnotation basic) {
		return basic.getOptional();
	}
	
	protected EnumType specifiedEnumerated(EnumeratedAnnotation enumerated) {
		return EnumType.fromJavaResourceModel(enumerated.getValue());
	}
	
	protected boolean lob(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		return lobResource(resourcePersistentAttribute) != null;
	}
	
	protected LobAnnotation lobResource(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		return (LobAnnotation) resourcePersistentAttribute.getAnnotation(LobAnnotation.ANNOTATION_NAME);
	}
	
	protected TemporalType temporal(TemporalAnnotation temporal) {
		return TemporalType.fromJavaResourceModel(temporal.getValue());
	}


	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	// ************** Validation *************************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages ,astRoot);
		
		addColumnMessages(messages, astRoot);
	}
	
	protected void addColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		JavaColumn column = this.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.connectionProfileIsActive();
		
		if (doContinue && this.getTypeMapping().tableNameIsInvalid(table)) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.getTableTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.getNameTextRange(astRoot))
				);
		}
	}
}
