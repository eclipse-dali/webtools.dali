/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaReferenceTable;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaTable;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ReferenceTableAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java join table or collection table
 */
public abstract class GenericJavaReferenceTable<P extends JpaContextModel, A extends ReferenceTableAnnotation>
	extends AbstractJavaTable<P, A>
	implements JavaReferenceTable
{
	protected final ContextListContainer<JavaSpecifiedJoinColumn, JoinColumnAnnotation> specifiedJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner joinColumnOwner;

	protected JavaSpecifiedJoinColumn defaultJoinColumn;


	protected GenericJavaReferenceTable(P parent, Owner owner) {
		super(parent, owner);
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.specifiedJoinColumnContainer = this.buildSpecifiedJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedJoinColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updateModels(this.getSpecifiedJoinColumns());
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterable<JavaSpecifiedJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}

	public void convertDefaultJoinColumnToSpecified() {
		MappingTools.convertReferenceTableDefaultToSpecifiedJoinColumn(this);
	}


	// ********** specified join columns **********

	public ListIterable<JavaSpecifiedJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public JavaSpecifiedJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.getContextElement(index);
	}

	public JavaSpecifiedJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.getSpecifiedJoinColumnsSize());
	}

	public JavaSpecifiedJoinColumn addSpecifiedJoinColumn(int index) {
		JoinColumnAnnotation annotation = this.getTableAnnotation().addJoinColumn(index);
		return this.specifiedJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumnContainer.indexOfContextElement((JavaSpecifiedJoinColumn) joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.getTableAnnotation().removeJoinColumn(index);
		this.removeTableAnnotationIfUnset();
		this.specifiedJoinColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.getTableAnnotation().moveJoinColumn(targetIndex, sourceIndex);
		this.specifiedJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	public void clearSpecifiedJoinColumns() {
		// for now, we have to remove annotations one at a time...
		for (int index = this.getSpecifiedJoinColumnsSize(); --index >= 0; ) {
			this.getTableAnnotation().removeJoinColumn(index);
		}
		this.removeTableAnnotationIfUnset();
		this.specifiedJoinColumnContainer.clearContextList();
	}

	protected void syncSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<JoinColumnAnnotation> getJoinColumnAnnotations() {
		return this.getTableAnnotation().getJoinColumns();
	}


	/**
	 * join column container
	 */
	protected class SpecifiedJoinColumnContainer
		extends ContextListContainer<JavaSpecifiedJoinColumn, JoinColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaSpecifiedJoinColumn buildContextElement(JoinColumnAnnotation resourceElement) {
			return GenericJavaReferenceTable.this.buildJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<JoinColumnAnnotation> getResourceElements() {
			return GenericJavaReferenceTable.this.getJoinColumnAnnotations();
		}
		@Override
		protected JoinColumnAnnotation getResourceElement(JavaSpecifiedJoinColumn contextElement) {
			return (JoinColumnAnnotation) contextElement.getColumnAnnotation();
		}
	}

	protected abstract ReadOnlyJoinColumn.Owner buildJoinColumnOwner();

	protected ContextListContainer<JavaSpecifiedJoinColumn, JoinColumnAnnotation> buildSpecifiedJoinColumnContainer(){
		SpecifiedJoinColumnContainer container = new SpecifiedJoinColumnContainer();
		container.initialize();
		return container;
	}

	// ********** default join column **********

	public JavaSpecifiedJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaSpecifiedJoinColumn joinColumn) {
		JavaSpecifiedJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<JavaSpecifiedJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<JavaSpecifiedJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<JavaSpecifiedJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn() {
		if (this.buildsDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(new NullJoinColumnAnnotation(this.getTableAnnotation())));
			} else {
				this.defaultJoinColumn.update();
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean buildsDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns();
	}


	// ********** misc **********

	protected void initializeFrom(ReadOnlyReferenceTable oldTable) {
		super.initializeFrom(oldTable);
		for (ReadOnlyJoinColumn joinColumn : oldTable.getSpecifiedJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	protected void initializeFromVirtual(ReadOnlyReferenceTable virtualTable) {
		super.initializeFromVirtual(virtualTable);
		for (ReadOnlyJoinColumn joinColumn : virtualTable.getJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	protected JavaSpecifiedJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.buildJoinColumn(this.joinColumnOwner, joinColumnAnnotation);
	}

	protected JavaSpecifiedJoinColumn buildJoinColumn(ReadOnlyJoinColumn.Owner jcOwner, JoinColumnAnnotation joinColumnAnnotation) {
		return this.getJpaFactory().buildJavaJoinColumn(this, jcOwner, joinColumnAnnotation);
	}

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaSpecifiedJoinColumn column : this.getJoinColumns()) {
			result = column.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		boolean continueValidating = this.buildTableValidator().validate(messages, reporter);

		//join column validation will handle the check for whether to validate against the database
		//some validation messages are not database specific. If the database validation for the
		//table fails we will stop there and not validate the join columns at all
		if (continueValidating) {
			this.validateJoinColumns(messages, reporter);
		}
	}

	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter) {
		this.validateModels(this.getJoinColumns(), messages, reporter);
	}
}

