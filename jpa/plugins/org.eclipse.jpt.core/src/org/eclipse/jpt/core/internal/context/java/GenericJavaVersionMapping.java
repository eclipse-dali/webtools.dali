/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.Temporal;
import org.eclipse.jpt.core.resource.java.Version;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaVersionMapping extends AbstractJavaAttributeMapping implements JavaVersionMapping
{
	protected final JavaColumn column;
	
	protected TemporalType temporal;

	public GenericJavaVersionMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = createJavaColumn();
	}

	protected JavaColumn createJavaColumn() {
		return jpaFactory().buildJavaColumn(this, this);
	}
	
	@Override
	public void initializeFromResource(JavaResourcePersistentAttribute persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.column.initializeFromResource(this.columnResource());
		this.temporal = this.temporal(this.temporalResource());
	}
	
	protected Temporal temporalResource() {
		return (Temporal) this.persistentAttributeResource.nonNullAnnotation(Temporal.ANNOTATION_NAME);
	}
	
	public ColumnAnnotation columnResource() {
		return (ColumnAnnotation) this.persistentAttributeResource.nonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	//************** IJavaAttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}

	public String annotationName() {
		return Version.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.COLUMN,
			JPA.TEMPORAL);
	}

	//************** INamedColumn.Owner implementation ***************

	public String defaultColumnName() {
		return attributeName();
	}
	
	public String defaultTableName() {
		return typeMapping().tableName();
	}

	//************** IVersionMapping implementation ***************
	
	public JavaColumn getColumn() {
		return this.column;
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.temporalResource().setValue(TemporalType.toJavaResourceModel(newTemporal));
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}

	@Override
	public void update(JavaResourcePersistentAttribute persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.column.update(this.columnResource());
		this.setTemporal(this.temporal(this.temporalResource()));
	}
	
	protected TemporalType temporal(Temporal temporal) {
		return TemporalType.fromJavaResourceModel(temporal.getValue());
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
	
	//***********  Validation  ******************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		addColumnMessages(messages, astRoot);
	}
	
	protected void addColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		JavaColumn column = this.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.connectionProfileIsActive();
		
		if (doContinue && this.typeMapping().tableNameIsInvalid(table)) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.tableTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.nameTextRange(astRoot))
				);
		}
	}
	
}
