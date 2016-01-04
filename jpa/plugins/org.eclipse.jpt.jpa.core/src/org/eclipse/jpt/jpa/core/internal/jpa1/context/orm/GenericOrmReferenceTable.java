/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualReferenceTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedReferenceTable;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmTable;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlReferenceTable;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> join table or collection table
 */
public abstract class GenericOrmReferenceTable<P extends JpaContextModel, PA extends Table.ParentAdapter<P>, X extends AbstractXmlReferenceTable>
	extends AbstractOrmTable<P, PA, X>
	implements OrmSpecifiedReferenceTable
{
	protected final ContextListContainer<OrmSpecifiedJoinColumn, XmlJoinColumn> specifiedJoinColumnContainer;
	protected final JoinColumn.ParentAdapter joinColumnParentAdapter;

	protected OrmSpecifiedJoinColumn defaultJoinColumn;


	protected GenericOrmReferenceTable(PA parentAdapter) {
		super(parentAdapter);
		this.joinColumnParentAdapter = this.buildJoinColumnParentAdapter();
		this.specifiedJoinColumnContainer = this.buildSpecifiedJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncSpecifiedJoinColumns(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getSpecifiedJoinColumns(), monitor);
		this.updateDefaultJoinColumn(monitor);
	}


	// ********** join columns **********

	public ListIterable<OrmSpecifiedJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}

	public void convertDefaultJoinColumnToSpecified() {
		MappingTools.convertReferenceTableDefaultToSpecifiedJoinColumn(this);
	}


	// ********** specified join columns **********

	public ListIterable<OrmSpecifiedJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer;
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public OrmSpecifiedJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.get(index);
	}

	public OrmSpecifiedJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.getSpecifiedJoinColumnsSize());
	}

	public OrmSpecifiedJoinColumn addSpecifiedJoinColumn(int index) {
		X xmlTable = this.getXmlTableForUpdate();
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmSpecifiedJoinColumn joinColumn = this.specifiedJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		xmlTable.getJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlJoinColumn buildXmlJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlJoinColumn();
	}

	public void removeSpecifiedJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumnContainer.indexOf((OrmSpecifiedJoinColumn) joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.specifiedJoinColumnContainer.remove(index);
		this.getXmlTable().getJoinColumns().remove(index);
		this.removeXmlTableIfUnset();
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.specifiedJoinColumnContainer.move(targetIndex, sourceIndex);
		this.getXmlTable().getJoinColumns().move(targetIndex, sourceIndex);
	}

	public void clearSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.clear();
		this.getXmlTable().getJoinColumns().clear();
	}

	protected void syncSpecifiedJoinColumns(IProgressMonitor monitor) {
		this.specifiedJoinColumnContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlJoinColumn> getXmlJoinColumns() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ?
				EmptyListIterable.<XmlJoinColumn>instance() :
				// clone to reduce chance of concurrency problems
				IterableTools.cloneLive(xmlTable.getJoinColumns());
	}

	protected ContextListContainer<OrmSpecifiedJoinColumn, XmlJoinColumn> buildSpecifiedJoinColumnContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_JOIN_COLUMNS_LIST, new SpecifiedJoinColumnContainerAdapter());
	}

	/**
	 * specified join column container adapter
	 */
	public class SpecifiedJoinColumnContainerAdapter
		extends AbstractContainerAdapter<OrmSpecifiedJoinColumn, XmlJoinColumn>
	{
		public OrmSpecifiedJoinColumn buildContextElement(XmlJoinColumn resourceElement) {
			return GenericOrmReferenceTable.this.buildJoinColumn(resourceElement);
		}
		public ListIterable<XmlJoinColumn> getResourceElements() {
			return GenericOrmReferenceTable.this.getXmlJoinColumns();
		}
		public XmlJoinColumn extractResourceElement(OrmSpecifiedJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected abstract JoinColumn.ParentAdapter buildJoinColumnParentAdapter();


	// ********** default join column **********

	public OrmSpecifiedJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(OrmSpecifiedJoinColumn joinColumn) {
		OrmSpecifiedJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<OrmSpecifiedJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<OrmSpecifiedJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<OrmSpecifiedJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(null));
			} else {
				this.defaultJoinColumn.update(monitor);
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean buildsDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns();
	}


	// ********** misc **********

	protected void initializeFrom(OrmSpecifiedReferenceTable oldTable) {
		super.initializeFrom(oldTable);
		for (OrmSpecifiedJoinColumn joinColumn : oldTable.getSpecifiedJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	protected void initializeFrom(VirtualReferenceTable virtualTable) {
		super.initializeFrom(virtualTable);
		for (VirtualJoinColumn joinColumn : virtualTable.getJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	protected OrmSpecifiedJoinColumn buildJoinColumn(XmlJoinColumn xmlJoinColumn) {
		return this.getContextModelFactory().buildOrmJoinColumn(this.joinColumnParentAdapter, xmlJoinColumn);
	}

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
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

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmSpecifiedJoinColumn column : this.getJoinColumns()) {
			result = column.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
