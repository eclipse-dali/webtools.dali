/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmJoinColumn extends AbstractOrmBaseColumn<XmlJoinColumn> implements OrmJoinColumn
{

	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected XmlJoinColumn resourceJoinColumn;

	public GenericOrmJoinColumn(XmlContextNode parent, OrmJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn) {
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

	public Table getReferencedColumnDbTable() {
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
		this.specifiedReferencedColumnName = buildSpecifiedReferencedColumnName(xjc);
		this.defaultReferencedColumnName = buildDefaultReferencedColumnName();
	}
	
	@Override
	public void update(XmlJoinColumn xjc) {
		this.resourceJoinColumn = xjc;
		super.update(xjc);
		this.setSpecifiedReferencedColumnName_(buildSpecifiedReferencedColumnName(xjc));
		this.setDefaultReferencedColumnName(buildDefaultReferencedColumnName());
	}

	protected String buildSpecifiedReferencedColumnName(XmlJoinColumn xjc) {
		return (xjc == null) ? null : xjc.getReferencedColumnName();
	}

	@Override
	protected String buildDefaultName() {
		return MappingTools.buildJoinColumnDefaultName(this);
	}
	
	protected String buildDefaultReferencedColumnName() {
		return MappingTools.buildJoinColumnDefaultReferencedColumnName(this.getOwner());
	}
	
	@Override
	protected String buildDefaultTableName() {
		if (getOwner().getRelationshipMapping() == null) {
			return null;
		}
		return super.buildDefaultTableName();
	}
	
	
	//******************* validation ***********************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validateName(messages);
		validateReferencedColumnName(messages);
	}
	
	protected void validateName(List<IMessage> messages) {
		if ( ! this.isResolved() && getDbTable() != null) {
			if (getName() != null) {
				messages.add(this.buildUnresolvedMessage());
			}
			else if (getOwner().joinColumnsSize() > 1) {
			
			}
		}
	}
	
	protected void validateReferencedColumnName(List<IMessage> messages) {
		if ( ! this.isReferencedColumnResolved() && getReferencedColumnDbTable() != null) {
			if (getReferencedColumnName() != null) {
				messages.add(this.buildUnresolvedMessage());
			}
			else if (getOwner().joinColumnsSize() > 1) {
				
			}
		}
	}
	
	protected IMessage buildUnresolvedMessage() {
		OrmRelationshipMapping mapping = (OrmRelationshipMapping) this.getOwner().getRelationshipMapping();
		return mapping.getPersistentAttribute().isVirtual() ? this.buildVirtualUnresolvedNameMessage(mapping) : this.buildNonVirtualUnresolvedNameMessage();
	}

	protected IMessage buildVirtualUnresolvedNameMessage(OrmRelationshipMapping mapping) {
		return this.buildMessage(
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {mapping.getName(), this.getName()},
						this.getNameTextRange()
					);
	}

	protected IMessage buildNonVirtualUnresolvedNameMessage() {
		return this.buildMessage(
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {this.getName()},
						this.getNameTextRange()
					);
	}

	protected IMessage buildUnresolvedReferenceColumnNameMessage() {
		OrmRelationshipMapping mapping = (OrmRelationshipMapping) this.getOwner().getRelationshipMapping();
		return mapping.getPersistentAttribute().isVirtual() ? this.buildVirtualUnresolvedReferenceColumnNameMessage(mapping) : this.buildNonVirtualUnresolvedReferenceColumnNameMessage();
	}

	protected IMessage buildVirtualUnresolvedReferenceColumnNameMessage(OrmRelationshipMapping mapping) {
		return this.buildMessage(
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {mapping.getName(), this.getName()},
						this.getReferencedColumnNameTextRange()
					);
	}

	protected IMessage buildNonVirtualUnresolvedReferenceColumnNameMessage() {
		return this.buildMessage(
						JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {this.getName()},
						this.getReferencedColumnNameTextRange()
					);
	}

	protected IMessage buildMessage(String msgID, String[] parms, TextRange textRange) {
		return DefaultJpaValidationMessages.buildMessage(IMessage.HIGH_SEVERITY, msgID, parms, this, textRange);
	}

}
