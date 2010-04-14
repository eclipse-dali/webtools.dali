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
import org.eclipse.jpt.core.context.Orderable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.resource.orm.XmlOrderable;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * ORM multi-relationship (m:m, 1:m) mapping
 */
public class GenericOrmOrderable
	extends AbstractOrmXmlContextNode
	implements OrmOrderable2_0
{

	protected String specifiedOrderBy;
	protected boolean noOrdering = false;
	protected boolean pkOrdering = false;
	protected boolean customOrdering = false;
	
	//JPA 2.0
	protected final Orderable.Owner owner; //the owner is only used for 2.0 projects
	protected boolean orderColumnOrdering = false;
	protected final OrmOrderColumn2_0 orderColumn;
	
	public GenericOrmOrderable(OrmAttributeMapping parent, Orderable.Owner owner) {
		super(parent);
		this.owner = owner;
		this.orderColumn = getXmlContextNodeFactory().buildOrmOrderColumn(this, new OrderColumnOwner());
		this.initializeOrdering();
	}

	public void update() {
		this.updateOrdering();
	}

	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	protected OrmPersistentAttribute getPersistentAttribute() {
		return getParent().getPersistentAttribute();
	}

	protected XmlOrderable getResourceOrderable() {
		return (XmlOrderable) getParent().getResourceAttributeMapping();
	}

	// **************** order by ***********************************************
	
	public String getSpecifiedOrderBy() {
		return this.specifiedOrderBy;
	}

	public void setSpecifiedOrderBy(String orderBy) {
		String old = this.specifiedOrderBy;
		this.specifiedOrderBy = orderBy;
		this.getResourceOrderable().setOrderBy(orderBy);
		this.firePropertyChanged(SPECIFIED_ORDER_BY_PROPERTY, old, orderBy);
	}
	
	protected void setSpecifiedOrderBy_(String orderBy) {
		String old = this.specifiedOrderBy;
		this.specifiedOrderBy = orderBy;
		this.firePropertyChanged(SPECIFIED_ORDER_BY_PROPERTY, old, orderBy);
	}

	protected void initializeOrdering() {
		this.specifiedOrderBy = this.getXmlOrderBy();
		XmlOrderColumn resourceOrderColumn = getXmlOrderColumn();
		if (this.specifiedOrderBy == null && resourceOrderColumn == null) { 
			this.noOrdering = true;
		} else if (this.specifiedOrderBy != null && this.specifiedOrderBy.equals("")) { //$NON-NLS-1$
			this.pkOrdering = true;
		} else if (resourceOrderColumn == null) {
			this.customOrdering = true;
		} else {
			this.orderColumnOrdering = true;
		}
	}
	
	protected void updateOrdering() {
		this.setSpecifiedOrderBy_(this.getXmlOrderBy());
		XmlOrderColumn resourceOrderColumn = getXmlOrderColumn();
		if (this.specifiedOrderBy == null && resourceOrderColumn == null) { 
			this.setNoOrdering_(true);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(false);
			this.setOrderColumnOrdering_(false);
		} else if (this.specifiedOrderBy != null && this.specifiedOrderBy.equals("")) { //$NON-NLS-1$
			this.setNoOrdering_(false);
			this.setPkOrdering_(true);
			this.setCustomOrdering_(false);
			this.setOrderColumnOrdering_(false);
		} else if (resourceOrderColumn == null){
			this.setNoOrdering_(false);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(true);
			this.setOrderColumnOrdering_(false);
		} else {
			this.setNoOrdering_(false);
			this.setPkOrdering_(false);
			this.setCustomOrdering_(false);
			this.setOrderColumnOrdering_(true);
		}
		this.orderColumn.update(this.getResourceOrderable());
	}
	
	protected String getXmlOrderBy() {
		return this.getResourceOrderable().getOrderBy();
	}
	
	protected XmlOrderColumn getXmlOrderColumn() {
		return ((XmlOrderable_2_0) this.getResourceOrderable()).getOrderColumn();
	}
	
	// **************** no ordering ***********************************************
		
	public boolean isNoOrdering() {
		return this.noOrdering;
	}

	public void setNoOrdering(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		if (noOrdering) {
			this.getResourceOrderable().setOrderBy(null);
			this.removeXmlOrderColumn();
		}
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);			
	}
	
	protected void setNoOrdering_(boolean noOrdering) {
		boolean old = this.noOrdering;
		this.noOrdering = noOrdering;
		this.firePropertyChanged(NO_ORDERING_PROPERTY, old, noOrdering);			
	}
	
	
	// **************** pk ordering ***********************************************
		
	public boolean isPkOrdering() {
		return this.pkOrdering;
	}
	
	public void setPkOrdering(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		if (pkOrdering) {
			this.getResourceOrderable().setOrderBy(""); //$NON-NLS-1$
			this.removeXmlOrderColumn();
		}
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);	
	}
	
	protected void setPkOrdering_(boolean pkOrdering) {
		boolean old = this.pkOrdering;
		this.pkOrdering = pkOrdering;
		this.firePropertyChanged(PK_ORDERING_PROPERTY, old, pkOrdering);	
	}
	
	
	// **************** custom ordering ***********************************************
		
	public boolean isCustomOrdering() {
		return this.customOrdering;
	}

	public void setCustomOrdering(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		if (customOrdering) {
			this.setSpecifiedOrderBy(""); //$NON-NLS-1$
			this.removeXmlOrderColumn();
		}
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
	}
	
	protected void setCustomOrdering_(boolean customOrdering) {
		boolean old = this.customOrdering;
		this.customOrdering = customOrdering;
		this.firePropertyChanged(CUSTOM_ORDERING_PROPERTY, old, customOrdering);
	}

	// ********** Orderable2_0 implementation **********  
	// **************** order column ordering ***********************************************
	
	public boolean isOrderColumnOrdering() {
		return this.orderColumnOrdering;
	}

	public void setOrderColumnOrdering(boolean orderColumnOrdering) {
		boolean old = this.orderColumnOrdering;
		this.orderColumnOrdering = orderColumnOrdering;
		if (orderColumnOrdering) {
			this.getResourceOrderable().setOrderBy(null);
			addXmlOrderColumn();
		}
		this.firePropertyChanged(ORDER_COLUMN_ORDERING_PROPERTY, old, orderColumnOrdering);			
	}
	
	protected void setOrderColumnOrdering_(boolean orderColumnOrdering) {
		boolean old = this.orderColumnOrdering;
		this.orderColumnOrdering = orderColumnOrdering;
		this.firePropertyChanged(ORDER_COLUMN_ORDERING_PROPERTY, old, orderColumnOrdering);			
	}
	
	public OrmOrderColumn2_0 getOrderColumn() {
		return this.orderColumn;
	}
	
	protected void addXmlOrderColumn() {
		((XmlOrderable_2_0) getResourceOrderable()).setOrderColumn(OrmFactory.eINSTANCE.createXmlOrderColumn());
	}
	
	protected void removeXmlOrderColumn() {
		((XmlOrderable_2_0) getResourceOrderable()).setOrderColumn(null);
	}

	public String getDefaultTableName() {
		return getOwner().getTableName();
	}
	
	/**
	 * Only call this for 2.0 projects
	 */
	protected Orderable2_0.Owner getOwner() {
		return (Orderable2_0.Owner) this.owner;
	}
	
	
	// ********** Validation **********  

	public TextRange getValidationTextRange() {
		TextRange textRange = getResourceOrderable().getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();	
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		//order-column and order-by both specified is handled with schema validation
		if (isOrderColumnOrdering()) {
			//TODO validation message if type is not List
			this.getOrderColumn().validate(messages, reporter);
		}
	}

	public String getUnresolvedNameMessage() {
		return JpaValidationMessages.ORDER_COLUMN_UNRESOLVED_NAME;
	}


	// ********** OrmNamedColumn implementation **********  

	class OrderColumnOwner implements OrmNamedColumn.Owner {
			
		public String getDefaultTableName() {
			return GenericOrmOrderable.this.getDefaultTableName();
		}

		public Table getDbTable(String tableName) {
			return getOwner().getDbTable(tableName);
		}

		public String getDefaultColumnName() {
			return getPersistentAttribute().getName() + "_ORDER"; //$NON-NLS-1$
		}

		public TypeMapping getTypeMapping() {
			return getPersistentAttribute().getOwningTypeMapping();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmOrderable.this.getValidationTextRange();
		}

		public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			if (isPersistentAttributeVirtual()) {
				return this.buildVirtualUnresolvedNameMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.ORDER_COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column,
				textRange
			);
		}

		protected IMessage buildVirtualUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ORDER_COLUMN_UNRESOLVED_NAME,
				new String[] {getPersistentAttributeName(), column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}

		protected boolean isPersistentAttributeVirtual() {
			return getPersistentAttribute().isVirtual();
		}

		protected String getPersistentAttributeName() {
			return getPersistentAttribute().getName();
		}
	}
}
