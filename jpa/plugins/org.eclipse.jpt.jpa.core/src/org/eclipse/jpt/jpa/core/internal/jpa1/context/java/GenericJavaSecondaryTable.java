/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
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
	extends AbstractJavaTable<JavaEntity, JavaSpecifiedSecondaryTable.ParentAdapter, SecondaryTableAnnotation>
	implements JavaSpecifiedSecondaryTable
{
	/** @see AbstractJavaTable#AbstractJavaTable(org.eclipse.jpt.jpa.core.context.Table.ParentAdapter, org.eclipse.jpt.jpa.core.resource.java.BaseTableAnnotation) */
	protected /* final */ SecondaryTableAnnotation tableAnnotation;

	protected final ContextListContainer<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> specifiedPrimaryKeyJoinColumnContainer;
	protected final BaseJoinColumn.ParentAdapter primaryKeyJoinColumnParentAdapter;

	protected JavaSpecifiedPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;


	public GenericJavaSecondaryTable(JavaSpecifiedSecondaryTable.ParentAdapter parentAdapter, SecondaryTableAnnotation tableAnnotation) {
		super(parentAdapter, tableAnnotation);
		this.primaryKeyJoinColumnParentAdapter = this.buildPrimaryKeyJoinColumnParentAdapter();
		this.specifiedPrimaryKeyJoinColumnContainer = this.buildSpecifiedPrimaryKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncSpecifiedPrimaryKeyJoinColumns(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getSpecifiedPrimaryKeyJoinColumns(), monitor);
		this.updateDefaultPrimaryKeyJoinColumn(monitor);
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

	public ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumnsSize() : this.getDefaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********


	public ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumnContainer;
	}

	public JavaSpecifiedPrimaryKeyJoinColumn getSpecifiedPrimaryKeyJoinColumn(int index) {
		return this.specifiedPrimaryKeyJoinColumnContainer.get(index);
	}

	public int getSpecifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumnContainer.size();
	}

	protected boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumnsSize() != 0;
	}

	public JavaSpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn() {
		return this.addSpecifiedPrimaryKeyJoinColumn(this.getSpecifiedPrimaryKeyJoinColumnsSize());
	}

	public JavaSpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.tableAnnotation.addPkJoinColumn(index);
		return this.specifiedPrimaryKeyJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumnContainer.indexOf((JavaSpecifiedPrimaryKeyJoinColumn) joinColumn));
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		this.tableAnnotation.removePkJoinColumn(index);
		this.removeTableAnnotationIfUnset();
		this.specifiedPrimaryKeyJoinColumnContainer.remove(index);
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
		this.specifiedPrimaryKeyJoinColumnContainer.clear();
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.tableAnnotation.movePkJoinColumn(targetIndex, sourceIndex);
		this.specifiedPrimaryKeyJoinColumnContainer.move(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedPrimaryKeyJoinColumns(IProgressMonitor monitor) {
		this.specifiedPrimaryKeyJoinColumnContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<PrimaryKeyJoinColumnAnnotation> getPrimaryKeyJoinColumnAnnotations() {
		return this.tableAnnotation.getPkJoinColumns();
	}

	protected ContextListContainer<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> buildSpecifiedPrimaryKeyJoinColumnContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, new SpecifiedPrimaryKeyJoinColumnContainerAdapter());
	}

	/**
	 * specified primary key join column container adapter
	 */
	public class SpecifiedPrimaryKeyJoinColumnContainerAdapter
		extends AbstractContainerAdapter<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		public JavaSpecifiedPrimaryKeyJoinColumn buildContextElement(PrimaryKeyJoinColumnAnnotation resourceElement) {
			return GenericJavaSecondaryTable.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		public ListIterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return GenericJavaSecondaryTable.this.getPrimaryKeyJoinColumnAnnotations();
		}
		public PrimaryKeyJoinColumnAnnotation extractResourceElement(JavaSpecifiedPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}

	protected BaseJoinColumn.ParentAdapter buildPrimaryKeyJoinColumnParentAdapter() {
		return new PrimaryKeyJoinColumnParentAdapter();
	}


	// ********** default primary key join column **********

	public JavaSpecifiedPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}

	protected void setDefaultPrimaryKeyJoinColumn(JavaSpecifiedPrimaryKeyJoinColumn joinColumn) {
		JavaSpecifiedPrimaryKeyJoinColumn old = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		return (this.defaultPrimaryKeyJoinColumn != null) ?
				new SingleElementListIterable<JavaSpecifiedPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn) :
				EmptyListIterable.<JavaSpecifiedPrimaryKeyJoinColumn>instance();
	}

	protected int getDefaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultPrimaryKeyJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultPrimaryKeyJoinColumn()) {
			if (this.defaultPrimaryKeyJoinColumn == null) {
				this.setDefaultPrimaryKeyJoinColumn(this.buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumnAnnotation(this.tableAnnotation)));
			} else {
				this.defaultPrimaryKeyJoinColumn.update(monitor);
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

	protected JavaSpecifiedPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation) {
		return this.getJpaFactory().buildJavaPrimaryKeyJoinColumn(this.primaryKeyJoinColumnParentAdapter, pkJoinColumnAnnotation);
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
		for (JavaSpecifiedPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
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
			for (JavaSpecifiedPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
				pkJoinColumn.validate(messages, reporter);
			}
		}
	}

	public boolean validatesAgainstDatabase() {
		return this.connectionProfileIsActive();
	}


	// ********** primary key join column parent adapter **********

	public class PrimaryKeyJoinColumnParentAdapter
		implements BaseJoinColumn.ParentAdapter
	{
		protected JavaEntity getEntity() {
			return GenericJavaSecondaryTable.this.getEntity();
		}

		public JpaContextModel getColumnParent() {
			return GenericJavaSecondaryTable.this;
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

		public String getDefaultColumnName(NamedColumn column) {
			if (this.getJoinColumnsSize() != 1) {
				return null;
			}
			return this.getEntity().getPrimaryKeyColumnName();
		}

		public TextRange getValidationTextRange() {
			return GenericJavaSecondaryTable.this.getValidationTextRange();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new SecondaryTablePrimaryKeyJoinColumnValidator(GenericJavaSecondaryTable.this, (BaseJoinColumn) column, this);
		}
	}
}
