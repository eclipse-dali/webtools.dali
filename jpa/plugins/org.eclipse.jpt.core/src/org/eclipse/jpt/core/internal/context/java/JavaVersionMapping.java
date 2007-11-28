/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.resource.java.Version;
import org.eclipse.jpt.utility.internal.Filter;


public class JavaVersionMapping extends JavaAttributeMapping implements IJavaVersionMapping
{
	protected final IJavaColumn column;
	
	protected TemporalType temporal;

	public JavaVersionMapping(IJavaPersistentAttribute parent) {
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
		this.temporal = this.temporal(this.temporalResource());
	}
	
	protected Temporal temporalResource() {
		return (Temporal) this.persistentAttributeResource.nonNullAnnotation(Temporal.ANNOTATION_NAME);
	}
	
	public Column columnResource() {
		return (Column) this.persistentAttributeResource.nonNullAnnotation(Column.ANNOTATION_NAME);
	}

	//************** IJavaAttributeMapping implementation ***************

	public String getKey() {
		return IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}

	public String annotationName() {
		return Version.ANNOTATION_NAME;
	}
	
	//************** INamedColumn.Owner implementation ***************

	public String defaultColumnName() {
		return attributeName();
	}
	
	public String defaultTableName() {
		return typeMapping().getTableName();
	}

	//************** IVersionMapping implementation ***************
	
	public IJavaColumn getColumn() {
		return this.column;
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.temporalResource().setValue(TemporalType.toJavaResourceModel(newTemporal));
		firePropertyChanged(IVersionMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}

	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.column.update(this.columnResource());
		this.setTemporal(this.temporal(this.temporalResource()));
	}
	
	protected TemporalType temporal(Temporal temporal) {
		return TemporalType.fromJavaResourceModel(temporal.getValue());
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
}
