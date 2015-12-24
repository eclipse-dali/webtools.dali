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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.OrderBy;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.OrderColumnValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOrderBy;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOrderable;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> ordering
 * <p>
 *<strong>(JPA 2.0 only) NB:</strong> If both the "order-by" and the
 * "order-column" elements are present (which is prohibited by the JPA spec),
 * order by is ignored (although the model is still there).
 */
public class GenericOrmOrderable
		extends AbstractOrmXmlContextModel<OrmAttributeMapping>
		implements OrmOrderable2_0 {
	
	protected final OrmOrderable2_0.ParentAdapter parentAdapter;
	
	protected boolean noOrdering = false;
	
	protected boolean orderByOrdering = false;
	// never null
	protected OrderBy orderBy;
	
	protected boolean orderColumnOrdering = false;
	// this is null for JPA 1.0 mappings
	protected OrmSpecifiedOrderColumn2_0 orderColumn;  
	

	/**
	 * JPA 1.0
	 */
	public GenericOrmOrderable(OrmAttributeMapping parent) {
		this(new OrmOrderable2_0.ParentAdapter.Null(parent));
	}
	
	/**
	 * JPA 2.0
	 */
	public GenericOrmOrderable(OrmOrderable2_0.ParentAdapter parentAdapter) {
		super(parentAdapter.getOrderableParent());
		
		this.parentAdapter = parentAdapter;
		
		initNoOrdering();
		initOrderBy();
		initOrderColumn();
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		
		synchNoOrdering();
		syncOrderBy(monitor);
		syncOrderColumn(monitor);
	}
	
	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		updateOrderBy(monitor);
		updateOrderColumn(monitor);
	}
	
	
	// ***** no ordering *****
	
	public boolean isNoOrdering() {
		return this.noOrdering;
	}
	
	public void setNoOrdering() {
		setNoOrdering_(true);
		setOrderByOrdering_(false);
		setOrderColumnOrdering_(false);
		
		removeXmlOrderBy();
		removeXmlOrderColumn();
	}
	
	protected void setNoOrdering_(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);
	}
	
	protected void initNoOrdering() {
		this.noOrdering = buildNoOrdering();
	}
	
	protected void synchNoOrdering() {
		setNoOrdering_(buildNoOrdering());
	}
	
	protected boolean buildNoOrdering() {
		return isJpa2_0Compatible() ? buildNoOrdering2_0() : buildNoOrdering1_0();
	}
	
	/**
	 * both elements are missing
	 */
	protected boolean buildNoOrdering2_0() {
		return getXmlOrderBy() == null && getXmlOrderColumn() == null;
	}
	
	/**
	 * the order-by element is missing
	 */
	protected boolean buildNoOrdering1_0() {
		return this.getXmlOrderBy() == null;
	}
	
	
	// ***** order-by ordering *****
	
	public boolean isOrderByOrdering() {
		return this.orderByOrdering;
	}
	
	public void setOrderByOrdering() {
		setNoOrdering_(false);
		setOrderByOrdering_(true);
		setOrderColumnOrdering_(false);
		
		addXmlOrderBy();
		removeXmlOrderColumn();
	}

	protected void setOrderByOrdering_(boolean newOrderByOrdering) {
		boolean old = this.orderByOrdering;
		this.orderByOrdering = newOrderByOrdering;
		this.firePropertyChanged(ORDER_BY_ORDERING_PROPERTY, old, newOrderByOrdering);
	}
	
	public OrderBy getOrderBy() {
		return this.orderBy;
	}
	
	protected void initOrderBy() {
		this.orderByOrdering = buildOrderByOrdering();
		this.orderBy = buildOrderBy();
	}
	
	protected void syncOrderBy(IProgressMonitor monitor) {
		setOrderByOrdering_(buildOrderByOrdering());
		this.orderBy.synchronizeWithResourceModel(monitor);
	}
	
	protected void updateOrderBy(IProgressMonitor monitor) {
		this.orderBy.update(monitor);
	}
	
	protected boolean buildOrderByOrdering() {
		return isJpa2_0Compatible() ? buildOrderByOrdering2_0() : buildOrderByOrdering1_0();
	}
	
	/**
	 * order-by is present but order-column is not
	 */
	protected boolean buildOrderByOrdering2_0() {
		return getXmlOrderBy() != null && getXmlOrderColumn() == null;
	}
	
	/**
	 * order-by is present
	 */
	protected boolean buildOrderByOrdering1_0() {
		return getXmlOrderBy() != null;
	}
	
	protected OrderBy buildOrderBy() {
		return new GenericOrmOrderBy(this, new OrderByContext());
	}
	
	
	protected class OrderByContext
			implements GenericOrmOrderBy.Context {
		
		public XmlOrderBy getXmlOrderBy(boolean addIfAbsent) {
			XmlOrderBy xmlOrderBy = GenericOrmOrderable.this.getXmlOrderBy();
			if (xmlOrderBy == null && addIfAbsent) {
				xmlOrderBy = addXmlOrderBy();
			}
			return xmlOrderBy;
		}
	}
	
	
	// ***** order-column ordering *****
	
	public boolean isOrderColumnOrdering() {
		return this.orderColumnOrdering;
	}
	
	public void setOrderColumnOrdering() {
		setNoOrdering_(false);
		setOrderByOrdering_(false);
		setOrderColumnOrdering_(true);
		
		removeXmlOrderBy();
		addXmlOrderColumn();
	}
	
	protected void setOrderColumnOrdering_(boolean orderColumnOrdering) {
		boolean old = this.orderColumnOrdering;
		this.orderColumnOrdering = orderColumnOrdering;
		this.firePropertyChanged(ORDER_COLUMN_ORDERING_PROPERTY, old, orderColumnOrdering);
	}
	
	public OrmSpecifiedOrderColumn2_0 getOrderColumn() {
		return this.orderColumn;
	}
	
	protected void initOrderColumn() {
		this.orderColumnOrdering = buildOrderColumnOrdering();
		this.orderColumn = buildOrderColumn();
	}
	
	protected void syncOrderColumn(IProgressMonitor monitor) {
		setOrderColumnOrdering_(buildOrderColumnOrdering());
		if (this.orderColumn != null) {
			this.orderColumn.synchronizeWithResourceModel(monitor);
		}
	}
	
	protected void updateOrderColumn(IProgressMonitor monitor) {
		if (this.orderColumn != null) {
			this.orderColumn.update(monitor);
		}
	}
	
	/**
	 * Only true if JPA 2.0 *and* order-column is present
	 */
	protected boolean buildOrderColumnOrdering() {
		return isJpa2_0Compatible() ? getXmlOrderColumn() != null : false;
	}
	
	/**
	 * non-null for JPA 2.0(+) only
	 */
	protected OrmSpecifiedOrderColumn2_0 buildOrderColumn() {
		return this.isOrmXml2_0Compatible() ?
				this.getContextModelFactory2_0().buildOrmOrderColumn(new OrderColumnParentAdapter()) :
				null;
	}
	
	
	// ********** xml order by **********

	protected XmlOrderBy getXmlOrderBy() {
		return this.getXmlOrderable().getOrderBy();
	}
	
	protected XmlOrderBy addXmlOrderBy() {
		XmlOrderBy xmlOrderBy = OrmFactory.eINSTANCE.createXmlOrderBy();
		getXmlOrderable().setOrderBy(xmlOrderBy);
		return xmlOrderBy;
	}
	
	protected void removeXmlOrderBy() {
		getXmlOrderable().setOrderBy(null);
	}
	
	
	// ********** xml order column **********

	protected XmlOrderColumn getXmlOrderColumn() {
		return this.getXmlOrderable().getOrderColumn();
	}
	
	protected XmlOrderColumn addXmlOrderColumn() {
		XmlOrderColumn xmlColumn = OrmFactory.eINSTANCE.createXmlOrderColumn();
		GenericOrmOrderable.this.getXmlOrderable().setOrderColumn(xmlColumn);
		return xmlColumn;
	}
	
	protected void removeXmlOrderColumn() {
		getXmlOrderable().setOrderColumn(null);
	}
	
	
	// ********** misc **********

	protected OrmAttributeMapping getAttributeMapping() {
		return this.parent;
	}

	protected OrmSpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getAttributeMapping().getPersistentAttribute();
	}

	protected XmlOrderable getXmlOrderable() {
		return (XmlOrderable) this.getAttributeMapping().getXmlAttributeMapping();
	}

	// JPA 2.0
	public String getDefaultTableName() {
		return this.parentAdapter.getTableName();
	}

	// JPA 2.0
	protected Table resolveDbTable(String tableName) {
		return this.parentAdapter.resolveDbTable(tableName);
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlOrderable().getValidationTextRange();
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// order-column and order-by both specified is handled with schema validation
		if (this.orderColumnOrdering) {
			// TODO validation message if type is not List
			this.orderColumn.validate(messages, reporter);
		}
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		// orderColumn is null for JPA 1.0 mappings so adding a null
		// check here to prevent NPE - Bug 375670
		if (this.orderColumn != null) {
			result = this.orderColumn.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	// ********** order column parent adapter (JPA 2.0) **********

	public class OrderColumnParentAdapter
		implements OrmSpecifiedOrderColumn2_0.ParentAdapter
	{
		public OrmOrderable2_0 getColumnParent() {
			return GenericOrmOrderable.this;
		}

		public String getDefaultTableName() {
			return GenericOrmOrderable.this.getDefaultTableName();
		}

		public Table resolveDbTable(String tableName) {
			return GenericOrmOrderable.this.resolveDbTable(tableName);
		}

		public String getDefaultColumnName(NamedColumn column) {
			return this.getPersistentAttribute().getName() + "_ORDER"; //$NON-NLS-1$
		}

		public TextRange getValidationTextRange() {
			return GenericOrmOrderable.this.getValidationTextRange();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new OrderColumnValidator(this.getPersistentAttribute(), (SpecifiedOrderColumn2_0) column);
		}

		public XmlOrderColumn getXmlColumn() {
			return GenericOrmOrderable.this.getXmlOrderColumn();
		}

		public XmlOrderColumn buildXmlColumn() {
			return GenericOrmOrderable.this.addXmlOrderColumn();
		}

		public void removeXmlColumn() {
			GenericOrmOrderable.this.removeXmlOrderColumn();
		}

		protected OrmSpecifiedPersistentAttribute getPersistentAttribute() {
			return GenericOrmOrderable.this.getPersistentAttribute();
		}
	}
}
