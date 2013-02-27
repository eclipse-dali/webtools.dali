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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.SecondaryTablePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullPrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java secondary table
 */
public class GenericJavaSecondaryTable
	extends AbstractJavaTable<JavaEntity, SecondaryTableAnnotation>
	implements JavaSecondaryTable
{
	/** @see AbstractJavaTable#AbstractJavaTable(org.eclipse.jpt.jpa.core.context.JpaContextModel, org.eclipse.jpt.jpa.core.context.ReadOnlyTable.Owner, org.eclipse.jpt.jpa.core.resource.java.BaseTableAnnotation) */
	protected /* final */ SecondaryTableAnnotation tableAnnotation;

	protected final ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> specifiedPrimaryKeyJoinColumnContainer;
	protected final ReadOnlyBaseJoinColumn.Owner primaryKeyJoinColumnOwner;

	protected JavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;


	public GenericJavaSecondaryTable(JavaEntity parent, Owner owner, SecondaryTableAnnotation tableAnnotation) {
		super(parent, owner, tableAnnotation);
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.specifiedPrimaryKeyJoinColumnContainer = this.buildSpecifiedPrimaryKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedPrimaryKeyJoinColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updateModels(this.getSpecifiedPrimaryKeyJoinColumns());
		this.updateDefaultPrimaryKeyJoinColumn();
	}


	// ********** table annotation **********

	@Override
	public SecondaryTableAnnotation getTableAnnotation() {
		return this.tableAnnotation;
	}

	/**
	 * @see AbstractJavaTable
	 */
	@Override
	protected void setTableAnnotation(SecondaryTableAnnotation tableAnnotation) {
		this.tableAnnotation = tableAnnotation;
	}

	@Override
	protected void removeTableAnnotation() {
		// even though its name is required, we don't remove a secondary table'
		// annotation when it is empty since it is part of a list and it's not
		// obvious whether this would be very user-helpful...
	}

	protected String getAnnotationName() {
		return SecondaryTableAnnotation.ANNOTATION_NAME;
	}


	// ********** primary key join columns **********

	public ListIterable<JavaPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumnsSize() : this.getDefaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********


	public ListIterable<JavaPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumnContainer.getContextElements();
	}

	public JavaPrimaryKeyJoinColumn getSpecifiedPrimaryKeyJoinColumn(int index) {
		return this.specifiedPrimaryKeyJoinColumnContainer.get(index);
	}

	public int getSpecifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumnContainer.getContextElementsSize();
	}

	protected boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumnsSize() != 0;
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn() {
		return this.addSpecifiedPrimaryKeyJoinColumn(this.getSpecifiedPrimaryKeyJoinColumnsSize());
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.tableAnnotation.addPkJoinColumn(index);
		return this.specifiedPrimaryKeyJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumnContainer.indexOfContextElement((JavaPrimaryKeyJoinColumn) joinColumn));
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		this.tableAnnotation.removePkJoinColumn(index);
		this.removeTableAnnotationIfUnset();
		this.specifiedPrimaryKeyJoinColumnContainer.removeContextElement(index);
	}

	//default PK join column will get set in the update
	public void convertDefaultPrimaryKeyJoinColumnsToSpecified() {
		if (this.defaultPrimaryKeyJoinColumn == null) {
			throw new IllegalStateException("default PK join column is null"); //$NON-NLS-1$
		}
		// Add a PK join column by creating a specified one using the default one
		String columnName = this.defaultPrimaryKeyJoinColumn.getDefaultName();
		String referencedColumnName = this.defaultPrimaryKeyJoinColumn.getDefaultReferencedColumnName();

		SpecifiedPrimaryKeyJoinColumn pkJoinColumn = this.addSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumn.setSpecifiedName(columnName);
		pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
	}

	public void clearSpecifiedPrimaryKeyJoinColumns() {
		for (int index = this.getSpecifiedPrimaryKeyJoinColumnsSize(); --index >= 0; ) {
			this.tableAnnotation.removePkJoinColumn(index);
		}
		this.removeTableAnnotationIfUnset();
		this.specifiedPrimaryKeyJoinColumnContainer.clearContextList();
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.tableAnnotation.movePkJoinColumn(targetIndex, sourceIndex);
		this.specifiedPrimaryKeyJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedPrimaryKeyJoinColumns() {
		this.specifiedPrimaryKeyJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<PrimaryKeyJoinColumnAnnotation> getPrimaryKeyJoinColumnAnnotations() {
		return this.tableAnnotation.getPkJoinColumns();
	}

	/**
	 * specified primary key join column container
	 */
	protected class SpecifiedPrimaryKeyJoinColumnContainer
		extends ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaPrimaryKeyJoinColumn buildContextElement(PrimaryKeyJoinColumnAnnotation resourceElement) {
			return GenericJavaSecondaryTable.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return GenericJavaSecondaryTable.this.getPrimaryKeyJoinColumnAnnotations();
		}
		@Override
		protected PrimaryKeyJoinColumnAnnotation getResourceElement(JavaPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}

	protected ReadOnlyBaseJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> buildSpecifiedPrimaryKeyJoinColumnContainer() {
		SpecifiedPrimaryKeyJoinColumnContainer container = new SpecifiedPrimaryKeyJoinColumnContainer();
		container.initialize();
		return container;
	}

	// ********** default primary key join column **********

	public JavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}

	protected void setDefaultPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn joinColumn) {
		JavaPrimaryKeyJoinColumn old = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<JavaPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		return (this.defaultPrimaryKeyJoinColumn != null) ?
				new SingleElementListIterable<JavaPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn) :
				EmptyListIterable.<JavaPrimaryKeyJoinColumn>instance();
	}

	protected int getDefaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultPrimaryKeyJoinColumn() {
		if (this.buildsDefaultPrimaryKeyJoinColumn()) {
			if (this.defaultPrimaryKeyJoinColumn == null) {
				this.setDefaultPrimaryKeyJoinColumn(this.buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumnAnnotation(this.tableAnnotation)));
			} else {
				this.defaultPrimaryKeyJoinColumn.update();
			}
		} else {
			this.setDefaultPrimaryKeyJoinColumn(null);
		}
	}

	protected boolean buildsDefaultPrimaryKeyJoinColumn() {
		return ! this.hasSpecifiedPrimaryKeyJoinColumns();
	}


	// ********** misc **********

	protected JavaEntity getEntity() {
		return this.parent;
	}

	public boolean isVirtual() {
		return false;
	}

	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation) {
		return this.getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, pkJoinColumnAnnotation);
	}


	// ********** defaults **********

	/**
	 * a secondary table doesn't have a default name
	 */
	@Override
	protected String buildDefaultName() {
		return null;
	}

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}


	// ********** code completion **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
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
			for (JavaPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
				pkJoinColumn.validate(messages, reporter);
			}
		}
	}

	public boolean validatesAgainstDatabase() {
		return this.connectionProfileIsActive();
	}


	// ********** primary key join column owner adapter **********

	protected class PrimaryKeyJoinColumnOwner
		implements ReadOnlyBaseJoinColumn.Owner
	{
		protected JavaEntity getEntity() {
			return GenericJavaSecondaryTable.this.getEntity();
		}

		public String getDefaultTableName() {
			return GenericJavaSecondaryTable.this.getName();
		}

		public Table resolveDbTable(String tableName) {
			return GenericJavaSecondaryTable.this.getDbTable();
		}

		public Table getReferencedColumnDbTable() {
			return this.getEntity().getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericJavaSecondaryTable.this.getPrimaryKeyJoinColumnsSize();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			if (this.getJoinColumnsSize() != 1) {
				return null;
			}
			return this.getEntity().getPrimaryKeyColumnName();
		}

		public TextRange getValidationTextRange() {
			return GenericJavaSecondaryTable.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new SecondaryTablePrimaryKeyJoinColumnValidator(GenericJavaSecondaryTable.this, (ReadOnlyBaseJoinColumn) column, this);
		}
	}
}
