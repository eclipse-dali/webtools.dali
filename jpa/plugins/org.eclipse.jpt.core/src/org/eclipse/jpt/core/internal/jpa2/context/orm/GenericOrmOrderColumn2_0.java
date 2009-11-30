/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmNamedColumn;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0;
import org.eclipse.jpt.core.utility.TextRange;


public class GenericOrmOrderColumn2_0
	extends AbstractOrmNamedColumn<XmlOrderColumn>
	implements OrmOrderColumn2_0
{
	protected Boolean specifiedNullable;
		
	protected Boolean specifiedInsertable;
		
	protected Boolean specifiedUpdatable;

	protected XmlOrderable_2_0 xmlOrderable;	
	
	public GenericOrmOrderColumn2_0(OrmOrderable2_0 parent, OrmNamedColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	public XmlOrderColumn getResourceColumn() {
		return this.xmlOrderable.getOrderColumn();
	}
	
	@Override
	protected void addResourceColumn() {
		this.xmlOrderable.setOrderColumn(OrmFactory.eINSTANCE.createXmlOrderColumn());		
	}
	
	@Override
	protected void removeResourceColumn() {
		this.xmlOrderable.setOrderColumn(null);
	}
	
	public boolean isResourceSpecified() {
		return getResourceColumn() != null;
	}
	
	@Override
	protected void removeResourceColumnIfFeaturesUnset() {
		//override to do nothing
		//don't want to remove the order-column element if it's features are all set to null
	}

	public boolean isNullable() {
		return (this.getSpecifiedNullable() == null) ? this.isDefaultNullable() : this.getSpecifiedNullable().booleanValue();
	}
	
	public boolean isDefaultNullable() {
		return BaseColumn.DEFAULT_NULLABLE;
	}
	
	public Boolean getSpecifiedNullable() {
		return this.specifiedNullable;
	}
	
	public void setSpecifiedNullable(Boolean newSpecifiedNullable) {
		Boolean oldSpecifiedNullable = this.specifiedNullable;
		this.specifiedNullable = newSpecifiedNullable;
		if (this.valuesAreDifferent(oldSpecifiedNullable, newSpecifiedNullable)) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setNullable(newSpecifiedNullable);						
				this.removeResourceColumnIfFeaturesUnset();
			}
			else if (newSpecifiedNullable != null) {
				addResourceColumn();
				getResourceColumn().setNullable(newSpecifiedNullable);
			}
		}
		firePropertyChanged(BaseColumn.SPECIFIED_NULLABLE_PROPERTY, oldSpecifiedNullable, newSpecifiedNullable);
	}

	protected void setSpecifiedNullable_(Boolean newSpecifiedNullable) {
		Boolean oldSpecifiedNullable = this.specifiedNullable;
		this.specifiedNullable = newSpecifiedNullable;
		firePropertyChanged(BaseColumn.SPECIFIED_NULLABLE_PROPERTY, oldSpecifiedNullable, newSpecifiedNullable);
	}

	public boolean isInsertable() {
		return (this.getSpecifiedInsertable() == null) ? this.isDefaultInsertable() : this.getSpecifiedInsertable().booleanValue();
	}
	
	public boolean isDefaultInsertable() {
		return BaseColumn.DEFAULT_INSERTABLE;
	}
	
	public Boolean getSpecifiedInsertable() {
		return this.specifiedInsertable;
	}
	
	public void setSpecifiedInsertable(Boolean newSpecifiedInsertable) {
		Boolean oldSpecifiedInsertable = this.specifiedInsertable;
		this.specifiedInsertable = newSpecifiedInsertable;
		if (this.valuesAreDifferent(oldSpecifiedInsertable, newSpecifiedInsertable)) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setInsertable(newSpecifiedInsertable);						
				this.removeResourceColumnIfFeaturesUnset();
			}
			else if (newSpecifiedInsertable != null) {
				addResourceColumn();
				getResourceColumn().setInsertable(newSpecifiedInsertable);
			}
		}
		firePropertyChanged(BaseColumn.SPECIFIED_INSERTABLE_PROPERTY, oldSpecifiedInsertable, newSpecifiedInsertable);
	}
	
	protected void setSpecifiedInsertable_(Boolean newSpecifiedInsertable) {
		Boolean oldSpecifiedInsertable = this.specifiedInsertable;
		this.specifiedInsertable = newSpecifiedInsertable;
		firePropertyChanged(BaseColumn.SPECIFIED_INSERTABLE_PROPERTY, oldSpecifiedInsertable, newSpecifiedInsertable);
	}

	public boolean isUpdatable() {
		return (this.getSpecifiedUpdatable() == null) ? this.isDefaultUpdatable() : this.getSpecifiedUpdatable().booleanValue();
	}
	
	public boolean isDefaultUpdatable() {
		return BaseColumn.DEFAULT_UPDATABLE;
	}
	
	public Boolean getSpecifiedUpdatable() {
		return this.specifiedUpdatable;
	}
	
	public void setSpecifiedUpdatable(Boolean newSpecifiedUpdatable) {
		Boolean oldSpecifiedUpdatable = this.specifiedUpdatable;
		this.specifiedUpdatable = newSpecifiedUpdatable;
		if (this.valuesAreDifferent(oldSpecifiedUpdatable, newSpecifiedUpdatable)) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setUpdatable(newSpecifiedUpdatable);						
				this.removeResourceColumnIfFeaturesUnset();
			}
			else if (newSpecifiedUpdatable != null) {
				addResourceColumn();
				getResourceColumn().setUpdatable(newSpecifiedUpdatable);
			}
		}
		firePropertyChanged(BaseColumn.SPECIFIED_UPDATABLE_PROPERTY, oldSpecifiedUpdatable, newSpecifiedUpdatable);
	}
	
	protected void setSpecifiedUpdatable_(Boolean newSpecifiedUpdatable) {
		Boolean oldSpecifiedUpdatable = this.specifiedUpdatable;
		this.specifiedUpdatable = newSpecifiedUpdatable;
		firePropertyChanged(BaseColumn.SPECIFIED_UPDATABLE_PROPERTY, oldSpecifiedUpdatable, newSpecifiedUpdatable);
	}
	
	public void initialize(XmlOrderable_2_0 xmlOrderable) {
		this.xmlOrderable = xmlOrderable;
		this.initialize(this.getResourceColumn());
	}
	
	public void update(XmlOrderable_2_0 xmlOrderable) {
		this.xmlOrderable = xmlOrderable;
		this.update(this.getResourceColumn());
	}
	
	@Override
	protected void initialize(XmlOrderColumn column) {
		super.initialize(column);
		this.specifiedNullable = this.getResourceNullable(column);
		this.specifiedUpdatable = this.getResourceUpdatable(column);
		this.specifiedInsertable = this.getResourceInsertable(column);
	}
	
	@Override
	protected void update(XmlOrderColumn column) {
		super.update(column);
		setSpecifiedNullable_(this.getResourceNullable(column));
		setSpecifiedUpdatable_(this.getResourceUpdatable(column));
		setSpecifiedInsertable_(this.getResourceInsertable(column));
	}
		
	protected Boolean getResourceNullable(XmlOrderColumn column) {
		return column == null ? null : column.getNullable();
	}
	
	protected Boolean getResourceUpdatable(XmlOrderColumn column) {
		return column == null ? null : column.getUpdatable();
	}
	
	
	protected Boolean getResourceInsertable(XmlOrderColumn column) {
		return column == null ? null : column.getInsertable();
	}

	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = getResourceColumn().getValidationTextRange();
		return (textRange != null) ? textRange : this.getOwner().getValidationTextRange();	
	}

}
