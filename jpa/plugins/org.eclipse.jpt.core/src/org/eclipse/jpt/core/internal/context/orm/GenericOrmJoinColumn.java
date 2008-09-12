/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericOrmJoinColumn extends AbstractOrmBaseColumn<XmlJoinColumn> implements OrmJoinColumn
{

	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected XmlJoinColumn resourceJoinColumn;

	public GenericOrmJoinColumn(OrmJpaContextNode parent, OrmJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn) {
		super(parent, owner);
		this.initialize(resourceJoinColumn);
	}

	public void initializeFrom(JoinColumn oldColumn) {
		super.initializeFrom(oldColumn);
		setSpecifiedReferencedColumnName(oldColumn.getSpecifiedReferencedColumnName());
	}
	
	public String getReferencedColumnName() {
		return (this.getSpecifiedReferencedColumnName() == null) ? getDefaultReferencedColumnName() : this.getSpecifiedReferencedColumnName();
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		getResourceColumn().setReferencedColumnName(newSpecifiedReferencedColumnName);
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}
	
	protected void setSpecifiedReferencedColumnName_(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, oldDefaultReferencedColumnName, newDefaultReferencedColumnName);
	}

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}
	
	@Override
	public OrmJoinColumn.Owner getOwner() {
		return (OrmJoinColumn.Owner) this.owner;
	}

	protected Table getReferencedColumnDbTable() {
		return getOwner().getReferencedColumnDbTable();
	}

	public Column getReferencedDbColumn() {
		Table table = getReferencedColumnDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(getReferencedColumnName());
	}

	public boolean isReferencedColumnResolved() {
		return getReferencedDbColumn() != null;
	}

	public TextRange getReferencedColumnNameTextRange() {
		if (getResourceColumn() != null) {
			TextRange textRange = getResourceColumn().getReferencedColumnNameTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return getOwner().getValidationTextRange();
	}


	@Override
	protected XmlJoinColumn getResourceColumn() {
		return this.resourceJoinColumn;
	}

	@Override
	protected void addResourceColumn() {
		//joinColumns are part of a collection, the join-column element will be removed/added
		//when the XmlJoinColumn is removed/added to the XmlEntity collection
	}
	
	@Override
	protected void removeResourceColumn() {
		//joinColumns are part of a collection, the pk-join-column element will be removed/added
		//when the XmlJoinColumn is removed/added to the XmlEntity collection
	}
	
	
	@Override
	protected void initialize(XmlJoinColumn xjc) {
		this.resourceJoinColumn = xjc;
		super.initialize(xjc);
		this.specifiedReferencedColumnName = specifiedReferencedColumnName(xjc);
		this.defaultReferencedColumnName = defaultReferencedColumnName();
	}
	
	@Override
	public void update(XmlJoinColumn xjc) {
		this.resourceJoinColumn = xjc;
		super.update(xjc);
		this.setSpecifiedReferencedColumnName_(specifiedReferencedColumnName(xjc));
		this.setDefaultReferencedColumnName(defaultReferencedColumnName());
	}

	protected String specifiedReferencedColumnName(XmlJoinColumn xjc) {
		return xjc == null ? null : xjc.getReferencedColumnName();
	}

	@Override
	protected String buildDefaultName() {
		RelationshipMapping relationshipMapping = getOwner().getRelationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!getOwner().getRelationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return MappingTools.buildJoinColumnDefaultName(this);
	}
	
	protected String defaultReferencedColumnName() {
		RelationshipMapping relationshipMapping = getOwner().getRelationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!relationshipMapping.isRelationshipOwner()) {
			return null;
		}
		return MappingTools.buildJoinColumnDefaultReferencedColumnName(this);
	}
	
	@Override
	protected String defaultTable() {
		RelationshipMapping relationshipMapping = getOwner().getRelationshipMapping();
		if (relationshipMapping == null) {
			return null;
		}
		if (!relationshipMapping.isRelationshipOwner()) {
			return null;
		}
		return super.defaultTable();
	}
	
	
	//******************* validation ***********************

	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
	
		if ( ! this.isResolved()) {
			OrmRelationshipMapping mapping = (OrmRelationshipMapping) this.getOwner().getRelationshipMapping();
			if (mapping.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {mapping.getName(), this.getName()}, 
						this,
						this.getNameTextRange())
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {this.getName()}, 
						this,
						this.getNameTextRange())
				);
			}
		}
	}
}
