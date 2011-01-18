/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaTable;
import org.eclipse.jpt.core.internal.jpa1.context.SecondaryTablePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java secondary table
 */
public class GenericJavaSecondaryTable
	extends AbstractJavaTable<SecondaryTableAnnotation>
	implements JavaSecondaryTable
{
	/** @see AbstractJavaTable#AbstractJavaTable(org.eclipse.jpt.core.context.java.JavaJpaContextNode, org.eclipse.jpt.core.context.Table.Owner, org.eclipse.jpt.core.resource.java.BaseTableAnnotation) */
	protected /* final */ SecondaryTableAnnotation tableAnnotation;

	protected final Vector<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns = new Vector<JavaPrimaryKeyJoinColumn>();
	protected final SpecifiedPrimaryKeyJoinColumnContainerAdapter specifiedPrimaryKeyJoinColumnContainerAdapter = new SpecifiedPrimaryKeyJoinColumnContainerAdapter();
	protected final JavaBaseJoinColumn.Owner primaryKeyJoinColumnOwner;

	protected JavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;


	public GenericJavaSecondaryTable(JavaEntity parent, Owner owner, SecondaryTableAnnotation tableAnnotation) {
		super(parent, owner, tableAnnotation);
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.initializeSpecifiedPrimaryKeyJoinColumns();
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
		this.updateNodes(this.getSpecifiedPrimaryKeyJoinColumns());
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

	public ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<JavaPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int primaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.getDefaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********

	public ListIterator<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumns().iterator();
	}

	public ListIterable<JavaPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<JavaPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	protected boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.size() != 0;
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn() {
		return this.addSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.size());
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.tableAnnotation.addPkJoinColumn(index);
		return this.addSpecifiedPrimaryKeyJoinColumn_(index, annotation);
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		this.tableAnnotation.removePkJoinColumn(index);
		this.removeTableAnnotationIfUnset();
		this.removeSpecifiedPrimaryKeyJoinColumn_(index);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.tableAnnotation.movePkJoinColumn(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected void initializeSpecifiedPrimaryKeyJoinColumns() {
		for (PrimaryKeyJoinColumnAnnotation annotation : this.getPrimaryKeyJoinColumnAnnotations()) {
			this.specifiedPrimaryKeyJoinColumns.add(this.buildPrimaryKeyJoinColumn(annotation));
		}
	}

	protected void syncSpecifiedPrimaryKeyJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedPrimaryKeyJoinColumnContainerAdapter);
	}

	protected Iterable<PrimaryKeyJoinColumnAnnotation> getPrimaryKeyJoinColumnAnnotations() {
		return CollectionTools.iterable(this.tableAnnotation.pkJoinColumns());
	}

	protected void moveSpecifiedPrimaryKeyJoinColumn_(int index, JavaPrimaryKeyJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn_(int index, PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation) {
		JavaPrimaryKeyJoinColumn joinColumn = this.buildPrimaryKeyJoinColumn(pkJoinColumnAnnotation);
		this.addItemToList(index, joinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		return joinColumn;
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn_(this.specifiedPrimaryKeyJoinColumns.indexOf(joinColumn));
	}

	/**
	 * specified primary key join column container adapter
	 */
	protected class SpecifiedPrimaryKeyJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		public Iterable<JavaPrimaryKeyJoinColumn> getContextElements() {
			return GenericJavaSecondaryTable.this.getSpecifiedPrimaryKeyJoinColumns();
		}
		public Iterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return GenericJavaSecondaryTable.this.getPrimaryKeyJoinColumnAnnotations();
		}
		public PrimaryKeyJoinColumnAnnotation getResourceElement(JavaPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
		public void moveContextElement(int index, JavaPrimaryKeyJoinColumn element) {
			GenericJavaSecondaryTable.this.moveSpecifiedPrimaryKeyJoinColumn_(index, element);
		}
		public void addContextElement(int index, PrimaryKeyJoinColumnAnnotation resourceElement) {
			GenericJavaSecondaryTable.this.addSpecifiedPrimaryKeyJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(JavaPrimaryKeyJoinColumn element) {
			GenericJavaSecondaryTable.this.removeSpecifiedPrimaryKeyJoinColumn_(element);
		}
	}

	protected JavaBaseJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
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

	@Override
	public JavaEntity getParent() {
		return (JavaEntity) super.getParent();
	}

	protected JavaEntity getEntity() {
		return this.getParent();
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
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaPrimaryKeyJoinColumn column : CollectionTools.iterable(this.primaryKeyJoinColumns())) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		boolean continueValidating = this.buildTableValidator(astRoot).validate(messages, reporter);

		//join column validation will handle the check for whether to validate against the database
		//some validation messages are not database specific. If the database validation for the
		//table fails we will stop there and not validate the join columns at all
		if (continueValidating) {
			for (Iterator<JavaPrimaryKeyJoinColumn> stream = this.primaryKeyJoinColumns(); stream.hasNext(); ) {
				stream.next().validate(messages, reporter, astRoot);
			}
		}
	}

	public boolean validatesAgainstDatabase() {
		return this.connectionProfileIsActive();
	}


	// ********** primary key join column owner adapter **********

	protected class PrimaryKeyJoinColumnOwner
		implements JavaBaseJoinColumn.Owner
	{
		protected JavaEntity getEntity() {
			return GenericJavaSecondaryTable.this.getEntity();
		}

		public TypeMapping getTypeMapping() {
			return this.getEntity();
		}

		public String getDefaultTableName() {
			return GenericJavaSecondaryTable.this.getName();
		}

		public Table resolveDbTable(String tableName) {
			return GenericJavaSecondaryTable.this.getDbTable();
		}

		public Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		public int joinColumnsSize() {
			return GenericJavaSecondaryTable.this.primaryKeyJoinColumnsSize();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericJavaSecondaryTable.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}

		public String getDefaultColumnName() {
			if (this.joinColumnsSize() != 1) {
				return null;
			}
			return this.getEntity().getPrimaryKeyColumnName();
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaSecondaryTable.this.getValidationTextRange(astRoot);
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new SecondaryTablePrimaryKeyJoinColumnValidator(GenericJavaSecondaryTable.this, (BaseJoinColumn) column, this, (BaseJoinColumnTextRangeResolver) textRangeResolver);
		}
	}
}
