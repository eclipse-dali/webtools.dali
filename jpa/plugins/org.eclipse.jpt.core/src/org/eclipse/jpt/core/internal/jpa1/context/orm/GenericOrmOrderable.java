/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.jpa2.context.OrderColumnValidator;
import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.resource.orm.XmlOrderable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> ordering
 * <p>
 * <strong>NB:</strong> Setting any flag to <code>false</code> (or setting the
 * specified "order by" to <code>null</code>) can be a bit unpredictable. The
 * intent is to set a flag to <code>true</code> (or set the specified "order by"
 * to a non-<code>null</code> value).
 * <p>
 * <strong>(JPA 2.0 only) NB:</strong> If both the "order-by" and the
 * "order-column" elements are present (which is prohibited by the JPA spec),
 * both are ignored.
 */
public class GenericOrmOrderable
	extends AbstractOrmXmlContextNode
	implements OrmOrderable2_0
{
	protected String specifiedOrderBy;
	protected boolean noOrdering = false;
	protected boolean pkOrdering = false;
	protected boolean customOrdering = false;

	// JPA 2.0
	protected final Owner owner;  // this is null for JPA 1.0 mappings
	protected boolean orderColumnOrdering = false;
	protected final OrmOrderColumn2_0 orderColumn;  // this is null for JPA 1.0 mappings


	/**
	 * JPA 1.0
	 */
	public GenericOrmOrderable(OrmAttributeMapping parent) {
		this(parent, null);
	}

	/**
	 * JPA 2.0
	 */
	public GenericOrmOrderable(OrmAttributeMapping parent, Owner owner) {
		super(parent);

		this.specifiedOrderBy = this.buildSpecifiedOrderBy();
		this.noOrdering = this.buildNoOrdering();
		this.pkOrdering = this.buildPkOrdering();
		this.customOrdering = this.buildCustomOrdering();

		this.owner = owner;
		this.orderColumnOrdering = this.buildOrderColumnOrdering();
		this.orderColumn = this.buildOrderColumn();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();

		this.setSpecifiedOrderBy_(this.buildSpecifiedOrderBy());
		this.setNoOrdering_(this.buildNoOrdering());
		this.setPkOrdering_(this.buildPkOrdering());
		this.setCustomOrdering_(this.buildCustomOrdering());

		this.setOrderColumnOrdering_(this.buildOrderColumnOrdering());
		if (this.orderColumn != null) {
			this.orderColumn.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		if (this.orderColumn != null) {
			this.orderColumn.update();
		}
	}


	// ********** specified order by **********

	public String getSpecifiedOrderBy() {
		return this.specifiedOrderBy;
	}

	public void setSpecifiedOrderBy(String orderBy) {
		if (orderBy != null) {
			this.setSpecifiedOrderBy_(orderBy);
			this.setNoOrdering_(false);
			this.setPkOrdering_(orderBy.length() == 0);
			this.setCustomOrdering_(orderBy.length() != 0);
			this.setOrderColumnOrdering_(false);

			this.removeXmlOrderColumn();
			this.getXmlOrderable().setOrderBy(orderBy);
		} else {
			this.setNoOrdering(true);  // hmmm...
		}
	}

	protected void setSpecifiedOrderBy_(String orderBy) {
		String old = this.specifiedOrderBy;
		this.specifiedOrderBy = orderBy;
		this.firePropertyChanged(SPECIFIED_ORDER_BY_PROPERTY, old, orderBy);
	}

	protected String buildSpecifiedOrderBy() {
		if (this.xmlOrderColumnIsPresent()) {
			return null;
		}
		return this.getXmlOrderBy();
	}


	// ********** no ordering **********

	public boolean isNoOrdering() {
		return this.noOrdering;
	}

	public void setNoOrdering(boolean noOrdering) {
		if (noOrdering) {
			this.setSpecifiedOrderBy_(null);
			this.setNoOrdering_(true);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(false);
			this.setOrderColumnOrdering_(false);

			this.removeXmlOrderColumn();
			this.getXmlOrderable().setOrderBy(null);
		} else {
			this.setPkOrdering(true);  // hmmm...
		}
	}

	protected void setNoOrdering_(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);
	}

	protected boolean buildNoOrdering() {
		return this.isJpa2_0Compatible() ? this.buildNoOrdering2_0() : this.buildNoOrdering1_0();
	}

	/**
	 * both elements are missing <em>or</em> both are present
	 */
	protected boolean buildNoOrdering2_0() {
		boolean orderByMissing = (this.getXmlOrderBy() == null);
		boolean orderByPresent = ! orderByMissing;
		boolean orderColumnMissing = (this.getXmlOrderColumn() == null);
		boolean orderColumnPresent = ! orderColumnMissing;
		return (orderByMissing && orderColumnMissing) || (orderByPresent && orderColumnPresent);
	}

	/**
	 * the order-by element is missing
	 */
	protected boolean buildNoOrdering1_0() {
		return this.getXmlOrderBy() == null;
	}


	// ********** pk ordering **********

	public boolean isPkOrdering() {
		return this.pkOrdering;
	}

	public void setPkOrdering(boolean pkOrdering) {
		if (pkOrdering) {
			this.setSpecifiedOrderBy(""); //$NON-NLS-1$
		} else {
			this.setNoOrdering(true);  // hmmm...
		}
	}

	protected void setPkOrdering_(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);
	}

	/**
	 * the order-by element is present but no value specified
	 */
	protected boolean buildPkOrdering() {
		if (this.xmlOrderColumnIsPresent()) {
			return false;
		}
		String xmlOrderBy = this.getXmlOrderBy();
		return (xmlOrderBy != null) && (xmlOrderBy.length() == 0);
	}


	// ********** custom ordering **********

	public boolean isCustomOrdering() {
		return this.customOrdering;
	}

	/**
	 * Unfortunately, setting the "custom ordering" flag directly is a bit hacky:
	 * The "specified order-by" is initially set to an empty string, which is
	 * the same as a "primary key ordering" state....
	 */
	public void setCustomOrdering(boolean customOrdering) {
		if (customOrdering) {
			this.setSpecifiedOrderBy_("");  // hmmm... //$NON-NLS-1$
			this.setNoOrdering_(false);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(true);
			this.setOrderColumnOrdering_(false);

			this.removeXmlOrderColumn();
			this.getXmlOrderable().setOrderBy(""); //$NON-NLS-1$
		} else {
			this.setNoOrdering(true);  // hmmm...
		}
	}

	protected void setCustomOrdering_(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
	}

	/**
	 * the order-by element is present and it has a specified value
	 */
	protected boolean buildCustomOrdering() {
		if (this.xmlOrderColumnIsPresent()) {
			return false;
		}
		String xmlOrderBy = this.getXmlOrderBy();
		return (xmlOrderBy != null) && (xmlOrderBy.length() != 0);
	}


	// ********** order column ordering **********

	public boolean isOrderColumnOrdering() {
		return this.orderColumnOrdering;
	}

	public void setOrderColumnOrdering(boolean orderColumnOrdering) {
		if (orderColumnOrdering) {
			this.setSpecifiedOrderBy_(null);
			this.setNoOrdering_(false);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(false);
			this.setOrderColumnOrdering_(true);

			this.getXmlOrderable().setOrderBy(null);
			this.buildXmlOrderColumn();
		} else {
			this.setNoOrdering(true);  // hmmm...
		}
	}

	protected void setOrderColumnOrdering_(boolean orderColumnOrdering) {
		boolean old = this.orderColumnOrdering;
		this.orderColumnOrdering = orderColumnOrdering;
		this.firePropertyChanged(ORDER_COLUMN_ORDERING_PROPERTY, old, orderColumnOrdering);
	}

	/**
	 * JPA 2.0 only;
	 * the <code>order-column</code> element is present <em>and</em>
	 * the <code>order-by</code> element is missing
	 */
	protected boolean buildOrderColumnOrdering() {
		return this.xmlOrderColumnIsPresent() &&
				(this.getXmlOrderBy() == null);
	}


	// ********** order column **********

	public OrmOrderColumn2_0 getOrderColumn() {
		return this.orderColumn;
	}

	/**
	 * JPA 2.0 only
	 */
	protected OrmOrderColumn2_0 buildOrderColumn() {
		return this.isJpa2_0Compatible() ? this.buildOrderColumn_() : null;
	}

	protected OrmOrderColumn2_0 buildOrderColumn_() {
		return this.getContextNodeFactory2_0().buildOrmOrderColumn(this, new OrderColumnOwner());
	}


	// ********** xml order by **********

	protected String getXmlOrderBy() {
		return this.getXmlOrderable().getOrderBy();
	}


	// ********** xml order column **********

	protected XmlOrderColumn getXmlOrderColumn() {
		return this.getXmlOrderable().getOrderColumn();
	}

	/**
	 * NB: Only return <code>true</code> for JPA 2.0 mappings.
	 */
	protected boolean xmlOrderColumnIsPresent() {
		return this.isJpa2_0Compatible() && (this.getXmlOrderColumn() != null);
	}

	protected XmlOrderColumn buildXmlOrderColumn() {
		XmlOrderColumn xmlColumn = OrmFactory.eINSTANCE.createXmlOrderColumn();
		GenericOrmOrderable.this.getXmlOrderable().setOrderColumn(xmlColumn);
		return xmlColumn;
	}

	protected void removeXmlOrderColumn() {
		if (this.xmlOrderColumnIsPresent()) {
			this.getXmlOrderable().setOrderColumn(null);
		}
	}


	// ********** misc **********

	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	protected OrmAttributeMapping getAttributeMapping() {
		return this.getParent();
	}

	protected OrmPersistentAttribute getPersistentAttribute() {
		return this.getAttributeMapping().getPersistentAttribute();
	}

	protected XmlOrderable getXmlOrderable() {
		return (XmlOrderable) this.getAttributeMapping().getXmlAttributeMapping();
	}

	// JPA 2.0
	public String getDefaultTableName() {
		return this.owner.getTableName();
	}

	// JPA 2.0
	protected Table resolveDbTable(String tableName) {
		return this.owner.resolveDbTable(tableName);
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


	// ********** order column owner (JPA 2.0) **********

	protected class OrderColumnOwner
		implements OrmOrderColumn2_0.Owner
	{
		public String getDefaultTableName() {
			return GenericOrmOrderable.this.getDefaultTableName();
		}

		public Table resolveDbTable(String tableName) {
			return GenericOrmOrderable.this.resolveDbTable(tableName);
		}

		public String getDefaultColumnName() {
			return this.getPersistentAttribute().getName() + "_ORDER"; //$NON-NLS-1$
		}

		public TypeMapping getTypeMapping() {
			return this.getPersistentAttribute().getOwningTypeMapping();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmOrderable.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new OrderColumnValidator(this.getPersistentAttribute(), (OrderColumn2_0) column, textRangeResolver);
		}

		public XmlOrderColumn getXmlColumn() {
			return GenericOrmOrderable.this.getXmlOrderColumn();
		}

		public XmlOrderColumn buildXmlColumn() {
			return GenericOrmOrderable.this.buildXmlOrderColumn();
		}

		public void removeXmlColumn() {
			GenericOrmOrderable.this.removeXmlOrderColumn();
		}

		protected OrmPersistentAttribute getPersistentAttribute() {
			return GenericOrmOrderable.this.getPersistentAttribute();
		}
	}
}
